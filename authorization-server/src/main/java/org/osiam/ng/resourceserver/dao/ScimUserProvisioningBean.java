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



    public enum UserLists {
        EMAILS("emails"),
        IMS("ims"),
        PHONENUMBERS("phonenumbers"),
        PHOTOS("photos"),

        ENTITLEMENTS("entitlements"),
        ROLES("roles"),
        X509("x509certificates"),
        ADDRESSES("addresses");

        private final String isUserClass;

        UserLists(String isUserClass) {
            this.isUserClass = isUserClass;
        }

        private static Map<String, UserLists> fromString = new HashMap<>();

        static {
            for (UserLists d : values())
                fromString.put(d.isUserClass, d);
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
    public User replaceUser(String id, User user) {
        UserEntity entity = userDao.getById(id);
        setFields(user, entity);
        userDao.update(entity);
        return entity.toScim();
    }

    private void setFields(User user, UserEntity entity) {
        Map<String, Field> userFields = getFieldsAsNormalizedMap(user.getClass());
        Map<String, Field> entityFields = getFieldsAsNormalizedMap(entity.getClass());
        SetUserListFields setUserListFields = new SetUserListFields(entity);
        SetUserSingleFields setUserSingleFields = new SetUserSingleFields(entity);
        try {
            for (String key : userFields.keySet()) {
                Field field = userFields.get(key);
                field.setAccessible(true);
                if (!READ_ONLY_FIELD_SET.contains(key)) {
                    Object userValue = field.get(user);
                    UserLists attributes = UserLists.fromString.get(key);
                    if (attributes == null) {
                        setUserSingleFields.updateSingleField(user, entityFields.get(key), userValue, key);
                    } else {
                        setUserListFields.updateListFields(userValue, attributes);
                    }
                }
            }
        } catch (IllegalAccessException e1) {
            throw new IllegalStateException("This should not happen.");
        }
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
