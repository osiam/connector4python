package org.osiam.ng.resourceserver.dao;

import org.osiam.ng.resourceserver.entities.*;
import org.springframework.stereotype.Service;
import scim.schema.v2.Address;
import scim.schema.v2.MultiValuedAttribute;
import scim.schema.v2.Name;
import scim.schema.v2.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 21.03.13
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ScimToEntityMapper {

    public UserEntity scimUserToEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setActive(user.isActive());
        userEntity.setAddresses(scimUserAddressesToEntity(user.getAddresses()));
        userEntity.setAny(scimAnyToStringSet(user.getAny()));
        userEntity.setDisplayName(user.getDisplayName());
        userEntity.setEmails(scimEmailsToEntity(user.getEmails()));
        userEntity.setEntitlements(scimEntitlementsToEntity(user.getEntitlements()));
        userEntity.setExternalId(user.getExternalId());
        userEntity.setGroups(scimUserGroupsToEntity(user.getGroups()));
        userEntity.setIms(scimImsToEntity(user.getIms()));
        userEntity.setLocale(user.getLocale());
        userEntity.setName(scimNameToEntity(user.getName()));
        userEntity.setNickName(user.getNickName());
        userEntity.setPassword(user.getPassword());
        userEntity.setPhoneNumbers(scimPhonenumbersToEntity(user.getPhoneNumbers()));
        userEntity.setPhotos(scimPhotosToEntity(user.getPhotos()));
        userEntity.setPreferredLanguage(user.getPreferredLanguage());
        userEntity.setProfileUrl(user.getProfileUrl());
        userEntity.setRoles(scimUserRolesToEntity(user.getRoles()));
        userEntity.setTimezone(user.getTimezone());
        userEntity.setTitle(user.getTitle());
        userEntity.setUsername(user.getUserName());
        userEntity.setUserType(user.getUserType());
        userEntity.setX509Certificates(scimCertificatesToEntity(user.getX509Certificates()));

        return userEntity;
    }

    private Set<X509CertificateEntity> scimCertificatesToEntity(User.X509Certificates x509Certificates) {
        Set<X509CertificateEntity> x509CertificateEntities = new HashSet<>();
        for (MultiValuedAttribute multiValuedAttribute : x509Certificates.getX509Certificate()) {
            x509CertificateEntities.add(X509CertificateEntity.fromScim(multiValuedAttribute));
        }
        return x509CertificateEntities;
    }

    private Set<RolesEntity> scimUserRolesToEntity(User.Roles roles) {
        Set<RolesEntity> rolesEntities = new HashSet<>();
        for (MultiValuedAttribute multiValuedAttribute : roles.getRole()) {
            rolesEntities.add(RolesEntity.fromScim(multiValuedAttribute));
        }
        return rolesEntities;
    }

    private Set<PhotoEntity> scimPhotosToEntity(User.Photos photos) {
        Set<PhotoEntity> photoEntities = new HashSet<>();
        for (MultiValuedAttribute multiValuedAttribute : photos.getPhoto()) {
            photoEntities.add(PhotoEntity.fromScim(multiValuedAttribute));
        }
        return photoEntities;
    }

    private Set<PhoneNumberEntity> scimPhonenumbersToEntity(User.PhoneNumbers phoneNumbers) {
        Set<PhoneNumberEntity> phoneNumberEntities = new HashSet<>();
        for (MultiValuedAttribute multiValuedAttribute : phoneNumbers.getPhoneNumber()) {
            phoneNumberEntities.add(PhoneNumberEntity.fromScim(multiValuedAttribute));
        }
        return phoneNumberEntities;
    }

    private NameEntity scimNameToEntity(Name name) {
        return NameEntity.fromScim(name);
    }

    private Set<ImEntity> scimImsToEntity(User.Ims ims) {
        Set<ImEntity> imEntities = new HashSet<>();
        for (MultiValuedAttribute multiValuedAttribute : ims.getIm()) {
            imEntities.add(ImEntity.fromScim(multiValuedAttribute));
        }
        return imEntities;
    }

    private Set<GroupEntity> scimUserGroupsToEntity(User.Groups groups) {
        Set<GroupEntity> groupEntities = new HashSet<>();
        for (MultiValuedAttribute multiValuedAttribute : groups.getGroup()) {
            groupEntities.add(GroupEntity.fromScim(multiValuedAttribute));
        }
        return groupEntities;
    }

    private Set<EntitlementsEntity> scimEntitlementsToEntity(User.Entitlements entitlements) {
        Set<EntitlementsEntity> entitlementsEntities = new HashSet<>();
        for (MultiValuedAttribute multiValuedAttribute : entitlements.getEntitlement()) {
            entitlementsEntities.add(EntitlementsEntity.fromScim(multiValuedAttribute));
        }
        return entitlementsEntities;
    }

    private Set<AddressEntity> scimUserAddressesToEntity(User.Addresses addresses) {
        Set<AddressEntity> addressEntities = new HashSet<>();
        for (Address address : addresses.getAddress()) {
            addressEntities.add(AddressEntity.fromScim(address));
        }
        return addressEntities;
    }

    private Set<String> scimAnyToStringSet(List<Object> any) {
        Set<String> anyStrings = new HashSet<>();
        for (Object object : any) {
            anyStrings.add(object.toString());
        }
        return anyStrings;
    }

    private Set<EmailEntity> scimEmailsToEntity(User.Emails emails) {
        Set<EmailEntity> emailEntities = new HashSet<>();
        for (MultiValuedAttribute multiValuedAttribute : emails.getEmail()) {
            emailEntities.add(EmailEntity.fromScim(multiValuedAttribute));
        }
        return emailEntities;
    }
}