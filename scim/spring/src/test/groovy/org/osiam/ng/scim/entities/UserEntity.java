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

package org.osiam.ng.scim.entities;

import scim.schema.v2.Address;
import scim.schema.v2.MultiValuedAttribute;
import scim.schema.v2.Name;
import scim.schema.v2.User;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * User Entity
 */


        
public class UserEntity {
    
    private long id;
    
    
    private UUID internalId;
    
    private String externalId;
    
    private String userName;
    
    private NameEntity name;
    
    private String displayName;
    
    private String nickName;
    
    private String profileUrl;
    
    private String title;
    
    private String userType;
    
    private String preferredLanguage;
    
    private String locale;
    
    private String timezone;
    
    private Boolean active;
    
    private String password;
    
    private Set<EmailEntity> emails;
    
    private Set<PhoneNumberEntity> phoneNumbers;
    
    private Set<ImEntity> ims;
    
    private Set<PhotoEntity> photos;
    
    private Set<AddressEntity> addresses;
    
    private Set<GroupEntity> groups;
    
    private Set<EntitlementsEntity> entitlements;
    //needs to be eager fetched due to authorization decisions
    
    private Set<RolesEntity> roles;
    
    private Set<X509CertificateEntity> x509Certificates;
    
    
    
    private Set<String> any;


    /**
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(long id) {
        this.id = id;
    }

    public UUID getInternalId() {
        return internalId;
    }

    public void setInternalId(UUID internalId) {
        this.internalId = internalId;
    }

    /**
     * 
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * 
     */
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    /**
     * 
     */
    public NameEntity getName() {
        return name;
    }

    /**
     * 
     */
    public void setName(NameEntity name) {
        this.name = name;
    }

    /**
     * 
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * 
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 
     */
    public String getProfileUrl() {
        return profileUrl;
    }

    /**
     * 
     */
    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    /**
     * 
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     */
    public String getUserType() {
        return userType;
    }

    /**
     * 
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * 
     */
    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    /**
     * 
     */
    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    /**
     * 
     */
    public String getLocale() {
        return locale;
    }

    /**
     * 
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    /**
     * 
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * 
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     * 
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * 
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    
    /**
     * 
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     */
    public void setPassword(String password) {
        this.password = password;
    }

    
    public String getUsername() {
        return userName;
    }

    /**
     * 
     */
    public void setUsername(String userName) {
        this.userName = userName;
    }

    
    public boolean isAccountNonExpired() {
        return true;
    }

    
    public boolean isAccountNonLocked() {
        return true;
    }

    
    public boolean isCredentialsNonExpired() {
        return true;
    }

    
    public boolean isEnabled() {
        return true;
    }

    /**
     * 
     */
    public Set<EmailEntity> getEmails() {
        if (emails == null) {
            emails = new HashSet<>();
        }
        return emails;
    }

    /**
     * 
     */
    public void setEmails(Set<EmailEntity> emails) {
        this.emails = emails;
    }

    /**
     * 
     */
    public Set<PhoneNumberEntity> getPhoneNumbers() {
        if (phoneNumbers == null) {
            phoneNumbers = new HashSet<>();
        }
        return phoneNumbers;
    }

    /**
     * 
     */
    public void setPhoneNumbers(Set<PhoneNumberEntity> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    /**
     * 
     */
    public Set<ImEntity> getIms() {
        if (ims == null) {
            ims = new HashSet<>();
        }
        return ims;
    }

    /**
     * 
     */
    public void setIms(Set<ImEntity> ims) {
        this.ims = ims;
    }

    /**
     * 
     */
    public Set<PhotoEntity> getPhotos() {
        if (photos == null) {
            photos = new HashSet<>();
        }
        return photos;
    }

    /**
     * 
     */
    public void setPhotos(Set<PhotoEntity> photos) {
        this.photos = photos;
    }

    /**
     * 
     */
    public Set<AddressEntity> getAddresses() {
        if (addresses == null) {
            addresses = new HashSet<>();
        }
        return addresses;
    }

    /**
     * 
     */
    public void setAddresses(Set<AddressEntity> addresses) {
        this.addresses = addresses;
    }

    /**
     * 
     */
    public Set<GroupEntity> getGroups() {
        if (groups == null) {
            groups = new HashSet<>();
        }
        return groups;
    }

    /**
     * 
     */
    public void setGroups(Set<GroupEntity> groups) {
        this.groups = groups;
    }

    /**
     * 
     */
    public Set<EntitlementsEntity> getEntitlements() {
        if (entitlements == null) {
            entitlements = new HashSet<>();
        }
        return entitlements;
    }

    /**
     * 
     */
    public void setEntitlements(Set<EntitlementsEntity> entitlements) {
        this.entitlements = entitlements;
    }

    /**
     * 
     */
    public Set<RolesEntity> getRoles() {
        if (roles == null) {
            roles = new HashSet<>();
        }
        return roles;
    }

    /**
     * 
     */
    public void setRoles(Set<RolesEntity> roles) {
        this.roles = roles;
    }

    /**
     * 
     */
    public Set<X509CertificateEntity> getX509Certificates() {
        if (x509Certificates == null) {
            x509Certificates = new HashSet<>();
        }
        return x509Certificates;
    }

    /**
     * 
     */
    public void setX509Certificates(Set<X509CertificateEntity> x509Certificates) {
        this.x509Certificates = x509Certificates;
    }

    /**
     * 
     */
    public Set<String> getAny() {
        return any;
    }

    /**
     * 
     */
    public void setAny(Set<String> any) {
        this.any = any;
    }
}
