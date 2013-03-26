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
import scim.schema.v2.Address;
import scim.schema.v2.MultiValuedAttribute;
import scim.schema.v2.User;

import java.util.HashSet;
import java.util.Set;

public class SetUserListFields {


    private UserEntity entity;

    public SetUserListFields(UserEntity entity) {
        this.entity = entity;
    }

    public void updateListFields(Object userValue, SetUserFields.UserLists attributes) {
        switch (attributes) {
            case EMAILS:
                setEmails(userValue);
                break;
            case ENTITLEMENTS:
                setEntitlements(userValue);
                break;
            case ADDRESSES:
                setAddresses(userValue);
                break;
            case IMS:
                setIMS(userValue);
                break;
            case PHONENUMBERS:
                setPhoneNumbers(userValue);
                break;
            case PHOTOS:
                setPhotos(userValue);
                break;
            case ROLES:
                setRoles(userValue);
                break;
            case X509:
                setX509(userValue);
                break;
        }
    }

    void setX509(Object userValue) {
        entity.getX509Certificates().clear();
        if (userValue != null) {
            for (MultiValuedAttribute m : User.X509Certificates.class.cast(userValue).getX509Certificate()) {
                X509CertificateEntity fromScim = X509CertificateEntity.fromScim(m);
                fromScim.setUser(entity);
                entity.getX509Certificates().add(fromScim);
            }
        }
    }

    void setRoles(Object userValue) {
        entity.getRoles().clear();
        if (userValue != null) {
            for (MultiValuedAttribute m : User.Roles.class.cast(userValue).getRole()) {
                RolesEntity fromScim = RolesEntity.fromScim(m);
                entity.getRoles().add(fromScim);
            }
        }
    }

    void setPhotos(Object userValue) {
        entity.getPhotos().clear();
        if (userValue != null) {
            for (MultiValuedAttribute m : User.Photos.class.cast(userValue).getPhoto()) {
                PhotoEntity fromScim = PhotoEntity.fromScim(m);
                fromScim.setUser(entity);
                entity.getPhotos().add(fromScim);
            }
        }
    }

    void setPhoneNumbers(Object userValue) {
        entity.getPhoneNumbers().clear();
        if (userValue != null) {

            for (MultiValuedAttribute m : User.PhoneNumbers.class.cast(userValue).getPhoneNumber()) {
                PhoneNumberEntity fromScim = PhoneNumberEntity.fromScim(m);
                fromScim.setUser(entity);
                entity.getPhoneNumbers().add(fromScim);
            }
        }
    }

    void setIMS(Object userValue) {
        entity.getIms().clear();
        if (userValue != null) {
            for (MultiValuedAttribute m : User.Ims.class.cast(userValue).getIm()) {
                ImEntity fromScim = ImEntity.fromScim(m);
                fromScim.setUser(entity);
                entity.getIms().add(fromScim);
            }

        }
    }

    void setAddresses(Object userValue) {
        entity.getAddresses().clear();
        if (userValue != null) {
            for (Address m : User.Addresses.class.cast(userValue).getAddress()) {
                AddressEntity fromScim = AddressEntity.fromScim(m);
                fromScim.setUser(entity);
                entity.getAddresses().add(fromScim);
            }
        }
    }

    void setEntitlements(Object userValue) {
        entity.getEntitlements().clear();
        if (userValue != null) {
            for (MultiValuedAttribute m : User.Entitlements.class.cast(userValue).getEntitlement()) {
                EntitlementsEntity entitlement = EntitlementsEntity.fromScim(m);
                entity.getEntitlements().add(entitlement);
            }
        }
    }

    void setEmails(Object userValue) {
        entity.getEmails().clear();
        if (userValue != null) {

            for (MultiValuedAttribute m : User.Emails.class.cast(userValue).getEmail()) {
                EmailEntity email = EmailEntity.fromScim(m);
                email.setUser(entity);
                entity.getEmails().add(email);
            }
        }
    }
}