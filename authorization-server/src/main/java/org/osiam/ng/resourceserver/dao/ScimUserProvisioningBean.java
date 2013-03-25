/*
 * Copyright (C) 2013 tarent AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.osiam.ng.resourceserver.dao;

import org.osiam.ng.resourceserver.entities.*;
import org.osiam.ng.scim.dao.SCIMUserProvisioning;
import org.osiam.ng.scim.exceptions.ResourceExistsException;
import org.springframework.stereotype.Service;
import scim.schema.v2.Address;
import scim.schema.v2.MultiValuedAttribute;
import scim.schema.v2.Name;
import scim.schema.v2.User;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ScimUserProvisioningBean implements SCIMUserProvisioning {

    private static final String[] READ_ONLY_FIELDS = {"id", "meta", "groups"};
    private static final Set<String> READ_ONLY_FIELD_SET = new HashSet<>(Arrays.asList(READ_ONLY_FIELDS));

    private enum UserLists {
        EMAILS(User.Emails.class),
        IMS(User.Ims.class),
        PHONENUMBER(User.PhoneNumbers.class),
        PHOTOS(User.Photos.class),

        ENTITLEMENTS(User.Entitlements.class),
        ROLES(User.Roles.class),
        X509(User.X509Certificates.class),
        ADDRESSES(User.Addresses.class);

        private final Class<?> isUserClass;

        UserLists(Class<?> isUserClass) {
            this.isUserClass = isUserClass;
        }

        private static Map<Class<?>, UserLists> fromClazz = new HashMap<>();

        static {
            for (UserLists d : values())
                fromClazz.put(d.isUserClass, d);
        }
    }

    @Inject
    private UserDAO userDao;


    @Override
    public User getById(String id) {
        UserEntity userEntity = userDao.getById(id);
        return userEntity.toScim();
    }

    @Override
    public User createUser(User user) {
        UserEntity userEntity = UserEntity.fromScim(user);
        try {
            userDao.createUser(userEntity);
        } catch (Exception e) {
            throw new ResourceExistsException("The user with name " + user.getUserName() + " already exists.");
        }
        return user;
    }


    @Override
    public User updateUser(String id, User user) {
        UserEntity entity = userDao.getById(id);
        setFields(user, entity);
        userDao.update(entity);
        return entity.toScim();
    }

    private void setFields(User user, UserEntity entity) {
        Map<String, Field> userFields = getFieldsAsNormalizedMap(user.getClass());
        Map<String, Field> entityFields = getFieldsAsNormalizedMap(entity.getClass());
        try {
            for (String key : userFields.keySet()) {
                Field field = userFields.get(key);
                field.setAccessible(true);
                if (!READ_ONLY_FIELD_SET.contains(key)) {
                    Object userValue = field.get(user);
                    if (isNotEmptyAndNotAList(userValue)) {
                        updateSingleField(user, entity, entityFields.get(key), userValue, key);
                    } else if (userValue != null) {
                        UserLists attributes = UserLists.fromClazz.get(userValue.getClass());
                        switch (attributes) {
                            case EMAILS:
                                Set<EmailEntity> emails = new HashSet<>();
                                for (MultiValuedAttribute m : User.Emails.class.cast(userValue).getEmail()) {
                                    EmailEntity email = EmailEntity.fromScim(m);
                                    email.setUser(entity);
                                    emails.add(email);
                                }
                                entity.setEmails(emails);
                                break;
                            case ENTITLEMENTS:
                                Set<EntitlementsEntity> entitlements = new HashSet<>();
                                for (MultiValuedAttribute m : User.Entitlements.class.cast(userValue).getEntitlement()) {
                                    EntitlementsEntity entitlement = EntitlementsEntity.fromScim(m);
                                    entitlements.add(entitlement);
                                }
                                entity.setEntitlements(entitlements);
                                break;
                            case ADDRESSES:
                                Set<AddressEntity> addresses = new HashSet<>();
                                for (Address m : User.Addresses.class.cast(userValue).getAddress()) {
                                    AddressEntity fromScim = AddressEntity.fromScim(m);
                                    fromScim.setUser(entity);
                                    addresses.add(fromScim);
                                }
                                entity.setAddresses(addresses);
                                break;
                            case IMS:
                                Set<ImEntity> ims = new HashSet<>();
                                for (MultiValuedAttribute m : User.Ims.class.cast(userValue).getIm()) {
                                    ImEntity fromScim = ImEntity.fromScim(m);
                                    fromScim.setUser(entity);
                                    ims.add(fromScim);
                                }
                                entity.setIms(ims);
                                break;
                            case PHONENUMBER:
                                Set<PhoneNumberEntity> phonenUmbers = new HashSet<>();
                                for (MultiValuedAttribute m : User.PhoneNumbers.class.cast(userValue).getPhoneNumber()) {
                                    PhoneNumberEntity fromScim = PhoneNumberEntity.fromScim(m);
                                    fromScim.setUser(entity);
                                    phonenUmbers.add(fromScim);
                                }
                                entity.setPhoneNumbers(phonenUmbers);
                                break;
                            case PHOTOS:
                                Set<PhotoEntity> photos = new HashSet<>();
                                for (MultiValuedAttribute m : User.Photos.class.cast(userValue).getPhoto()) {
                                    PhotoEntity fromScim = PhotoEntity.fromScim(m);
                                    fromScim.setUser(entity);
                                    photos.add(fromScim);
                                }
                                entity.setPhotos(photos);
                                break;
                            case ROLES:
                                Set<RolesEntity> roles = new HashSet<>();
                                for (MultiValuedAttribute m : User.Roles.class.cast(userValue).getRole()) {
                                    RolesEntity fromScim = RolesEntity.fromScim(m);
                                    roles.add(fromScim);
                                }
                                entity.setRoles(roles);
                                break;
                            case X509:
                                Set<X509CertificateEntity> x509 = new HashSet<>();
                                for (MultiValuedAttribute m : User.X509Certificates.class.cast(userValue).getX509Certificate()) {
                                    X509CertificateEntity fromScim = X509CertificateEntity.fromScim(m);
                                    fromScim.setUser(entity);
                                    x509.add(fromScim);
                                }
                                entity.setX509Certificates(x509);
                                break;
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("This should not happen!", e);
        }
    }

    private boolean isNotEmptyAndNotAList(Object userValue) {
        return userValue != null && !(userValue instanceof User.UserFields);
    }

    private void updateSingleField(User user, UserEntity entity, Field entityField, Object userValue, String key) throws IllegalAccessException {
        if (userValue instanceof Name) {
            //TODO set to orphan delete?
            entity.setName(NameEntity.fromScim(user.getName()));
        } else {
            if (!(key == "password" && String.valueOf(userValue).isEmpty()))
                updateSimpleField(entity, entityField, userValue);
        }
    }

    private void updateSimpleField(Object entity, Field entityField, Object userValue) throws IllegalAccessException {
        entityField.setAccessible(true);
        entityField.set(entity, userValue);
    }

    private Map<String, Field> getFieldsAsNormalizedMap(Class<?> clazz) {
        Map<String, Field> fields = new HashMap<>();
        if (clazz != null) {
            for (Field f : clazz.getDeclaredFields()) {
                fields.put(f.getName().toLowerCase(), f);
            }
            fields.putAll(getFieldsAsNormalizedMap(clazz.getSuperclass()));
        }

        return fields;
    }
}
