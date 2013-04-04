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

package scim.schema.v2;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.*;


/**
 * <p>Java class for User complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class User extends CoreResource {

    private String userName;
    private Name name;
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
    private User.Emails emails;
    private User.PhoneNumbers phoneNumbers;
    private User.Ims ims;
    private User.Photos photos;
    private User.Addresses addresses;
    private User.Groups groups;
    private User.Entitlements entitlements;
    private User.Roles roles;
    private User.X509Certificates x509Certificates;
    private Set<Object> any;

    public User() {

    }

    private User(Builder builder) {
        super(builder);
        this.userName = builder.userName;
        this.name = builder.name;
        this.displayName = builder.displayName;
        this.nickName = builder.nickName;
        this.profileUrl = builder.profileUrl;
        this.title = builder.title;
        this.userType = builder.userType;
        this.preferredLanguage = builder.preferredLanguage;
        this.locale = builder.locale;
        this.timezone = builder.timezone;
        this.active = builder.active;
        this.password = builder.password;

        this.emails = builder.emails;
        this.phoneNumbers = builder.phoneNumbers;
        this.ims = builder.ims;
        this.photos = builder.photos;
        this.addresses = builder.addresses;
        this.groups = builder.groups;
        this.entitlements = builder.entitlements;
        this.roles = builder.roles;
        this.x509Certificates = builder.x509Certificates;
        this.any = builder.any;
    }

    public static class Builder extends CoreResource.Builder {
        private final String userName;


        private Name name;
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

        private User.Emails emails;
        private User.PhoneNumbers phoneNumbers;
        private User.Ims ims;
        private User.Photos photos;
        private User.Addresses addresses;
        private User.Groups groups;
        private User.Entitlements entitlements;
        private User.Roles roles;
        private User.X509Certificates x509Certificates;


        private Set<Object> any;


        public Builder(String userName) {
            if (userName == null)
                throw new IllegalArgumentException("userName must not be null.");
            this.userName = userName;
        }

        /**
         * This class is for generating the output of an User. It does not copy the password and it checks for empty
         * lists; if a list is empty it will be nulled so that json-mapping will ignore it.
         * @param user
         * @return
         */
        public static User generateForOuput(User user){
           Builder builder = new Builder(user.userName) ;
            builder.id = user.id;
            builder.meta = user.meta;
            builder.externalId = user.externalId;
            builder.name = user.name;
            builder.displayName = user.displayName;
            builder.nickName = user.nickName;
            builder.profileUrl = user.profileUrl;
            builder.title = user.title;
            builder.userType = user.userType;
            builder.preferredLanguage = user.preferredLanguage;
            builder.locale = user.locale;
            builder.timezone = user.timezone;
            builder.active = user.active;
            // null lists when empty
            builder.emails = user.emails == null ? null : (user.emails.getEmail().isEmpty() ? null : user.emails);
            builder.phoneNumbers = user.phoneNumbers == null ? null : (user.phoneNumbers.getPhoneNumber().isEmpty() ? null : user.phoneNumbers);;
            builder.ims = user.ims == null ? null : (user.ims.getIm().isEmpty() ? null : user.ims);;
            builder.photos = user.photos == null ? null : (user.photos.getPhoto().isEmpty() ? null : user.photos);;
            builder.addresses = user.addresses == null ? null : (user.addresses.getAddress().isEmpty() ? null : user.addresses);;
            builder.groups = user.groups == null ? null : (user.groups.getGroup().isEmpty() ? null : user.groups);;
            builder.entitlements = user.entitlements == null ? null : (user.entitlements.getEntitlement().isEmpty() ? null : user.entitlements);;
            builder.roles = user.roles == null ? null : (user.roles.getRole().isEmpty() ? null : user.roles);;
            builder.x509Certificates = user.x509Certificates == null ? null : (user.x509Certificates.getX509Certificate().isEmpty() ? null : user.x509Certificates);;
            builder.any = user.any;
            builder.schemas = user.schemas;
            return builder.build();
        }

        public Builder setName(Name name) {
            this.name = name;
            return this;
        }

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setNickName(String nickName) {
            this.nickName = nickName;
            return this;
        }

        public Builder setProfileUrl(String profileUrl) {
            this.profileUrl = profileUrl;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setUserType(String userType) {
            this.userType = userType;
            return this;
        }

        public Builder setPreferredLanguage(String preferredLanguage) {
            this.preferredLanguage = preferredLanguage;
            return this;
        }

        public Builder setLocale(String locale) {
            this.locale = locale;
            return this;
        }

        public Builder setTimezone(String timezone) {
            this.timezone = timezone;
            return this;
        }

        public Builder setActive(Boolean active) {
            this.active = active;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setEmails(Emails emails) {
            this.emails = emails;
            return this;
        }

        public Builder setPhoneNumbers(PhoneNumbers phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
            return this;
        }

        public Builder setIms(Ims ims) {
            this.ims = ims;
            return this;
        }

        public Builder setPhotos(Photos photos) {
            this.photos = photos;
            return this;
        }

        public Builder setAddresses(Addresses addresses) {
            this.addresses = addresses;
            return this;
        }

        public Builder setGroups(Groups groups) {
            this.groups = groups;
            return this;
        }

        public Builder setEntitlements(Entitlements entitlements) {
            this.entitlements = entitlements;
            return this;
        }

        public Builder setRoles(Roles roles) {
            this.roles = roles;
            return this;
        }

        public Builder setX509Certificates(X509Certificates x509Certificates) {
            this.x509Certificates = x509Certificates;
            return this;
        }

        public Builder setAny(Set<Object> any) {
            this.any = any;
            return this;
        }

        @Override
        public User build() {
            return new User(this);
        }
    }


    /**
     * Gets the value of the userName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     *         {@link Name }
     */
    public Name getName() {
        return name;
    }

    /**
     * Gets the value of the displayName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the value of the nickName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Gets the value of the profileUrl property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getProfileUrl() {
        return profileUrl;
    }

    /**
     * Gets the value of the title property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the value of the userType property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Gets the value of the preferredLanguage property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    /**
     * Gets the value of the locale property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Gets the value of the timezone property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * Gets the value of the active property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isActive() {
        return active;
    }

    /**
     * Gets the value of the password property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the value of the emails property.
     *
     * @return possible object is
     *         {@link User.Emails }
     */
    public User.Emails getEmails() {
        return emails;
    }

    /**
     * Gets the value of the phoneNumbers property.
     *
     * @return possible object is
     *         {@link User.PhoneNumbers }
     */
    public User.PhoneNumbers getPhoneNumbers() {
        return phoneNumbers;
    }

    /**
     * Gets the value of the ims property.
     *
     * @return possible object is
     *         {@link User.Ims }
     */
    public User.Ims getIms() {
        return ims;
    }

    /**
     * Gets the value of the photos property.
     *
     * @return possible object is
     *         {@link User.Photos }
     */
    public User.Photos getPhotos() {
        return photos;
    }

    /**
     * Gets the value of the addresses property.
     *
     * @return possible object is
     *         {@link User.Addresses }
     */
    public User.Addresses getAddresses() {
        return addresses;
    }

    /**
     * Gets the value of the groups property.
     *
     * @return possible object is
     *         {@link User.Groups }
     */
    public User.Groups getGroups() {
        return groups;
    }

    /**
     * Gets the value of the entitlements property.
     *
     * @return possible object is
     *         {@link User.Entitlements }
     */
    public User.Entitlements getEntitlements() {
        return entitlements;
    }

    /**
     * Gets the value of the roles property.
     *
     * @return possible object is
     *         {@link User.Roles }
     */
    public User.Roles getRoles() {
        return roles;
    }

    /**
     * Gets the value of the x509Certificates property.
     *
     * @return possible object is
     *         {@link User.X509Certificates }
     */
    public User.X509Certificates getX509Certificates() {
        return x509Certificates;
    }

    /**
     * Gets the value of the any property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the any property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     */
    public Set<Object> getAny() {
        return this.any;
    }




    /**
     * Java class for anonymous complex type.
     */
    public static class Addresses implements UserFields{

        private List<Address> address = new ArrayList<>();

        /**
         * Gets the value of the address property.
         * <p/>
         * <p/>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the address property.
         * <p/>
         * <p/>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAddress().add(newItem);
         * </pre>
         * <p/>
         * <p/>
         * <p/>
         * Objects of the following type(s) are allowed in the list
         * {@link Address }
         */
        public List<Address> getAddress() {
            return this.address;
        }

    }


    public static interface UserFields{}
    /**
     * Java class for anonymous complex type.
     */
    public static class Emails implements UserFields{

        private List<MultiValuedAttribute> email = new ArrayList<>();

        /**
         * Gets the value of the email property.
         * <p/>
         * <p/>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the email property.
         * <p/>
         * <p/>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEmail().add(newItem);
         * </pre>
         * <p/>
         * <p/>
         * <p/>
         * Objects of the following type(s) are allowed in the list
         * {@link MultiValuedAttribute }
         */
        public List<MultiValuedAttribute> getEmail() {
            return this.email;
        }

    }


    /**
     * Java class for anonymous complex type.
     */
    public static class Entitlements implements UserFields{

        private List<MultiValuedAttribute> entitlement = new ArrayList<>();

        /**
         * Gets the value of the entitlement property.
         * <p/>
         * <p/>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entitlement property.
         * <p/>
         * <p/>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntitlement().add(newItem);
         * </pre>
         * <p/>
         * <p/>
         * <p/>
         * Objects of the following type(s) are allowed in the list
         * {@link MultiValuedAttribute }
         */
        public List<MultiValuedAttribute> getEntitlement() {
            return this.entitlement;
        }

    }


    /**
     * Java class for anonymous complex type.
     */
    public static class Groups implements UserFields{

        private List<MultiValuedAttribute> group = new ArrayList<>();

        /**
         * Gets the value of the group property.
         * <p/>
         * <p/>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the group property.
         * <p/>
         * <p/>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGroup().add(newItem);
         * </pre>
         * <p/>
         * <p/>
         * <p/>
         * Objects of the following type(s) are allowed in the list
         * {@link MultiValuedAttribute }
         */
        public List<MultiValuedAttribute> getGroup() {
            return this.group;
        }

    }


    /**
     * Java class for anonymous complex type.
     */
    public static class Ims implements UserFields{

        private List<MultiValuedAttribute> im = new ArrayList<>();

        /**
         * Gets the value of the im property.
         * <p/>
         * <p/>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the im property.
         * <p/>
         * <p/>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIm().add(newItem);
         * </pre>
         * <p/>
         * <p/>
         * <p/>
         * Objects of the following type(s) are allowed in the list
         * {@link MultiValuedAttribute }
         */
        public List<MultiValuedAttribute> getIm() {
            return this.im;
        }

    }


    /**
     * Java class for anonymous complex type.
     */
    public static class PhoneNumbers implements UserFields{

        private List<MultiValuedAttribute> phoneNumber = new ArrayList<>();

        /**
         * Gets the value of the phoneNumber property.
         * <p/>
         * <p/>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the phoneNumber property.
         * <p/>
         * <p/>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPhoneNumber().add(newItem);
         * </pre>
         * <p/>
         * <p/>
         * <p/>
         * Objects of the following type(s) are allowed in the list
         * {@link MultiValuedAttribute }
         */
        public List<MultiValuedAttribute> getPhoneNumber() {
            return this.phoneNumber;
        }

    }


    /**
     * Java class for anonymous complex type.
     */
    public static class Photos implements UserFields{

        private List<MultiValuedAttribute> photo = new ArrayList<>();

        /**
         * Gets the value of the photo property.
         * <p/>
         * <p/>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the photo property.
         * <p/>
         * <p/>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPhoto().add(newItem);
         * </pre>
         * <p/>
         * <p/>
         * <p/>
         * Objects of the following type(s) are allowed in the list
         * {@link MultiValuedAttribute }
         */
        public List<MultiValuedAttribute> getPhoto() {
            return this.photo;
        }

    }


    /**
     * Java class for anonymous complex type.
     */
    public static class Roles implements UserFields{

        private List<MultiValuedAttribute> role = new ArrayList<>();

        /**
         * Gets the value of the role property.
         * <p/>
         * <p/>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the role property.
         * <p/>
         * <p/>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRole().add(newItem);
         * </pre>
         * <p/>
         * <p/>
         * <p/>
         * Objects of the following type(s) are allowed in the list
         * {@link MultiValuedAttribute }
         */
        public List<MultiValuedAttribute> getRole() {
            return this.role;
        }

    }


    /**
     * Java class for anonymous complex type.
     */
    public static class X509Certificates implements UserFields{

        private List<MultiValuedAttribute> x509Certificate = new ArrayList<>();

        /**
         * Gets the value of the x509Certificate property.
         * <p/>
         * <p/>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the x509Certificate property.
         * <p/>
         * <p/>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getX509Certificate().add(newItem);
         * </pre>
         * <p/>
         * <p/>
         * <p/>
         * Objects of the following type(s) are allowed in the list
         * {@link MultiValuedAttribute }
         */
        public List<MultiValuedAttribute> getX509Certificate() {
            return this.x509Certificate;
        }

    }

}



