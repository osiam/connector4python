package org.osiam.ng.resourceserver.dao;

import org.osiam.ng.resourceserver.entities.*;
import org.springframework.stereotype.Service;
import scim.schema.v2.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 19.03.13
 * Time: 11:06
 * To change this template use File | Settings | File Templates.
 */
@Service
public class EntityToScimMapper {


    public User entityUserToScim(UserEntity userEntity){

        return new User.Builder(userEntity.getUsername()).
                    setActive(userEntity.getActive()).
                    setAddresses(entityAddressToScim(userEntity.getAddresses())).
                    setAny(anyStringSetToObjectSet(userEntity.getAny())).
                    setDisplayName(userEntity.getDisplayName()).
                    setEmails(entityEmailToScim(userEntity.getEmails())).
                    setEntitlements(entityEntitlementsToScim(userEntity.getEntitlements())).
                    setGroups(entityGroupsToScim(userEntity.getGroups())).
                    setIms(entityImsToScim(userEntity.getIms())).
                    setLocale(userEntity.getLocale()).
                    setName(userEntity.getName() != null ? userEntity.getName().toScim() : null).
                    setNickName(userEntity.getNickName()).
                    setPassword(userEntity.getPassword()).
                    setPhoneNumbers(entityPhonenumbersToScim(userEntity.getPhoneNumbers())).
                    setPhotos(entityPhotosToScim(userEntity.getPhotos())).
                    setPreferredLanguage(userEntity.getPreferredLanguage()).
                    setProfileUrl(userEntity.getProfileUrl()).
                    setRoles(entityRolesToScim(userEntity.getRoles())).
                    setTimezone(userEntity.getTimezone()).
                    setTitle(userEntity.getTitle()).
                    setUserType(userEntity.getUserType()).
                    setX509Certificates(entityX509CertificatesToScim(userEntity.getX509Certificates())).
                    setExternalId(userEntity.getExternalId()).
                    build();
    }

    private User.X509Certificates entityX509CertificatesToScim(Set<X509CertificateEntity> x509CertificateEntities) {
        User.X509Certificates x509Certificates = new User.X509Certificates();
        for (X509CertificateEntity x509CertificateEntity : x509CertificateEntities) {
            x509Certificates.getX509Certificate().add(x509CertificateEntity.toScim());
        }
        return x509Certificates;
    }

    private User.Roles entityRolesToScim(Set<RolesEntity> rolesEntities) {
        User.Roles roles = new User.Roles();
        for (RolesEntity rolesEntity : rolesEntities) {
            roles.getRole().add(rolesEntity.toScim());
        }
        return roles;
    }

    private User.Photos entityPhotosToScim(Set<PhotoEntity> photoEntities) {
        User.Photos photos = new User.Photos();
        for (PhotoEntity photoEntity : photoEntities) {
            photos.getPhoto().add(photoEntity.toScim());
        }
        return photos;
    }

    private User.PhoneNumbers entityPhonenumbersToScim(Set<PhoneNumberEntity> phoneNumberEntities) {
        User.PhoneNumbers phoneNumbers = new User.PhoneNumbers();
        for (PhoneNumberEntity phoneNumberEntity : phoneNumberEntities) {
            phoneNumbers.getPhoneNumber().add(phoneNumberEntity.toScim());
        }
        return phoneNumbers;
    }

    private User.Ims entityImsToScim(Set<ImEntity> imEntities) {
        User.Ims ims = new User.Ims();
        for (ImEntity imEntity : imEntities) {
            ims.getIm().add(imEntity.toScim());
        }
        return ims;
    }

    private User.Groups entityGroupsToScim(Set<GroupEntity> groupEntities) {
        User.Groups groups = new User.Groups();
        for (GroupEntity groupEntity : groupEntities) {
            groups.getGroup().add(groupEntity.toScim());
        }
        return groups;
    }


    private User.Entitlements entityEntitlementsToScim(Set<EntitlementsEntity> entitlementsEntities) {
        User.Entitlements entitlements = new User.Entitlements();
        for (EntitlementsEntity entitlementsEntity : entitlementsEntities) {
            entitlements.getEntitlement().add(entitlementsEntity.toScim());
        }
        return entitlements;
    }

    private User.Emails entityEmailToScim(Set<EmailEntity> emailEntities) {
        User.Emails emails = new User.Emails();
        for (EmailEntity emailEntity : emailEntities) {
            emails.getEmail().add(emailEntity.toScim());
        }
        return emails;
    }

    private List<Object> anyStringSetToObjectSet(Set<String> anySet) {
        List<Object> objectSet = new ArrayList<>();
        for (String any: anySet) {
            objectSet.add(any);
        }
        return objectSet;
    }

    private User.Addresses entityAddressToScim(Set<AddressEntity> addressEntities) {
        User.Addresses addresses = new User.Addresses();
        for (AddressEntity addressEntity : addressEntities) {
            addresses.getAddress().add(addressEntity.toScim());
        }
        return addresses;
    }
}