package scim.schema.v2;

import java.util.ArrayList;
import java.util.List;


/**
 * Java class for User complex type.
 */
public class User
    extends CoreResource
{

    protected String userName;
    protected Name name;
    protected String displayName;
    protected String nickName;
    protected String profileUrl;
    protected String title;
    protected String userType;
    protected String preferredLanguage;
    protected String locale;
    protected String timezone;
    protected Boolean active;
    protected String password;
    protected User.Emails emails;
    protected User.PhoneNumbers phoneNumbers;
    protected User.Ims ims;
    protected User.Photos photos;
    protected User.Addresses addresses;
    protected User.Groups groups;
    protected User.Entitlements entitlements;
    protected User.Roles roles;
    protected User.X509Certificates x509Certificates;
    protected List<Object> any;

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link Name }
     *     
     */
    public Name getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link Name }
     *     
     */
    public void setName(Name value) {
        this.name = value;
    }

    /**
     * Gets the value of the displayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

    /**
     * Gets the value of the nickName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Sets the value of the nickName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNickName(String value) {
        this.nickName = value;
    }

    /**
     * Gets the value of the profileUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfileUrl() {
        return profileUrl;
    }

    /**
     * Sets the value of the profileUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfileUrl(String value) {
        this.profileUrl = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the userType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Sets the value of the userType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserType(String value) {
        this.userType = value;
    }

    /**
     * Gets the value of the preferredLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    /**
     * Sets the value of the preferredLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferredLanguage(String value) {
        this.preferredLanguage = value;
    }

    /**
     * Gets the value of the locale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets the value of the locale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocale(String value) {
        this.locale = value;
    }

    /**
     * Gets the value of the timezone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * Sets the value of the timezone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimezone(String value) {
        this.timezone = value;
    }

    /**
     * Gets the value of the active property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isActive() {
        return active;
    }

    /**
     * Sets the value of the active property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setActive(Boolean value) {
        this.active = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the emails property.
     * 
     * @return
     *     possible object is
     *     {@link User.Emails }
     *     
     */
    public User.Emails getEmails() {
        return emails;
    }

    /**
     * Sets the value of the emails property.
     * 
     * @param value
     *     allowed object is
     *     {@link User.Emails }
     *     
     */
    public void setEmails(User.Emails value) {
        this.emails = value;
    }

    /**
     * Gets the value of the phoneNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link User.PhoneNumbers }
     *     
     */
    public User.PhoneNumbers getPhoneNumbers() {
        return phoneNumbers;
    }

    /**
     * Sets the value of the phoneNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link User.PhoneNumbers }
     *     
     */
    public void setPhoneNumbers(User.PhoneNumbers value) {
        this.phoneNumbers = value;
    }

    /**
     * Gets the value of the ims property.
     * 
     * @return
     *     possible object is
     *     {@link User.Ims }
     *     
     */
    public User.Ims getIms() {
        return ims;
    }

    /**
     * Sets the value of the ims property.
     * 
     * @param value
     *     allowed object is
     *     {@link User.Ims }
     *     
     */
    public void setIms(User.Ims value) {
        this.ims = value;
    }

    /**
     * Gets the value of the photos property.
     * 
     * @return
     *     possible object is
     *     {@link User.Photos }
     *     
     */
    public User.Photos getPhotos() {
        return photos;
    }

    /**
     * Sets the value of the photos property.
     * 
     * @param value
     *     allowed object is
     *     {@link User.Photos }
     *     
     */
    public void setPhotos(User.Photos value) {
        this.photos = value;
    }

    /**
     * Gets the value of the addresses property.
     * 
     * @return
     *     possible object is
     *     {@link User.Addresses }
     *     
     */
    public User.Addresses getAddresses() {
        return addresses;
    }

    /**
     * Sets the value of the addresses property.
     * 
     * @param value
     *     allowed object is
     *     {@link User.Addresses }
     *     
     */
    public void setAddresses(User.Addresses value) {
        this.addresses = value;
    }

    /**
     * Gets the value of the groups property.
     * 
     * @return
     *     possible object is
     *     {@link User.Groups }
     *     
     */
    public User.Groups getGroups() {
        return groups;
    }

    /**
     * Sets the value of the groups property.
     * 
     * @param value
     *     allowed object is
     *     {@link User.Groups }
     *     
     */
    public void setGroups(User.Groups value) {
        this.groups = value;
    }

    /**
     * Gets the value of the entitlements property.
     * 
     * @return
     *     possible object is
     *     {@link User.Entitlements }
     *     
     */
    public User.Entitlements getEntitlements() {
        return entitlements;
    }

    /**
     * Sets the value of the entitlements property.
     * 
     * @param value
     *     allowed object is
     *     {@link User.Entitlements }
     *     
     */
    public void setEntitlements(User.Entitlements value) {
        this.entitlements = value;
    }

    /**
     * Gets the value of the roles property.
     * 
     * @return
     *     possible object is
     *     {@link User.Roles }
     *     
     */
    public User.Roles getRoles() {
        return roles;
    }

    /**
     * Sets the value of the roles property.
     * 
     * @param value
     *     allowed object is
     *     {@link User.Roles }
     *     
     */
    public void setRoles(User.Roles value) {
        this.roles = value;
    }

    /**
     * Gets the value of the x509Certificates property.
     * 
     * @return
     *     possible object is
     *     {@link User.X509Certificates }
     *     
     */
    public User.X509Certificates getX509Certificates() {
        return x509Certificates;
    }

    /**
     * Sets the value of the x509Certificates property.
     * 
     * @param value
     *     allowed object is
     *     {@link User.X509Certificates }
     *     
     */
    public void setX509Certificates(User.X509Certificates value) {
        this.x509Certificates = value;
    }

    /**
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the any property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<>();
        }
        return this.any;
    }


    /**
     * Java class for anonymous complex type.
     */
    public static class Addresses {

        protected List<Address> address;

        /**
         * Gets the value of the address property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the address property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAddress().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Address }
         * 
         * 
         */
        public List<Address> getAddress() {
            if (address == null) {
                address = new ArrayList<>();
            }
            return this.address;
        }

    }


    /**
     * Java class for anonymous complex type.
     */
    public static class Emails {

        protected List<MultiValuedAttribute> email;

        /**
         * Gets the value of the email property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the email property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEmail().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link MultiValuedAttribute }
         * 
         * 
         */
        public List<MultiValuedAttribute> getEmail() {
            if (email == null) {
                email = new ArrayList<>();
            }
            return this.email;
        }

    }


    /**
     * Java class for anonymous complex type.
     */
    public static class Entitlements {

        protected List<MultiValuedAttribute> entitlement;

        /**
         * Gets the value of the entitlement property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entitlement property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntitlement().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link MultiValuedAttribute }
         * 
         * 
         */
        public List<MultiValuedAttribute> getEntitlement() {
            if (entitlement == null) {
                entitlement = new ArrayList<>();
            }
            return this.entitlement;
        }

    }


    /**
     * Java class for anonymous complex type.
     */
    public static class Groups {

        protected List<MultiValuedAttribute> group;

        /**
         * Gets the value of the group property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the group property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGroup().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link MultiValuedAttribute }
         * 
         * 
         */
        public List<MultiValuedAttribute> getGroup() {
            if (group == null) {
                group = new ArrayList<>();
            }
            return this.group;
        }

    }


    /**
     * Java class for anonymous complex type.
     */
    public static class Ims {

        protected List<MultiValuedAttribute> im;

        /**
         * Gets the value of the im property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the im property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIm().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link MultiValuedAttribute }
         * 
         * 
         */
        public List<MultiValuedAttribute> getIm() {
            if (im == null) {
                im = new ArrayList<>();
            }
            return this.im;
        }

    }


    /**
     * Java class for anonymous complex type.
     */
    public static class PhoneNumbers {

        protected List<MultiValuedAttribute> phoneNumber;

        /**
         * Gets the value of the phoneNumber property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the phoneNumber property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPhoneNumber().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link MultiValuedAttribute }
         * 
         * 
         */
        public List<MultiValuedAttribute> getPhoneNumber() {
            if (phoneNumber == null) {
                phoneNumber = new ArrayList<>();
            }
            return this.phoneNumber;
        }

    }


    /**
     * Java class for anonymous complex type.
     */
    public static class Photos {

        protected List<MultiValuedAttribute> photo;

        /**
         * Gets the value of the photo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the photo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPhoto().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link MultiValuedAttribute }
         * 
         * 
         */
        public List<MultiValuedAttribute> getPhoto() {
            if (photo == null) {
                photo = new ArrayList<>();
            }
            return this.photo;
        }

    }


    /**
     * Java class for anonymous complex type.
     */
    public static class Roles {

        protected List<MultiValuedAttribute> role;

        /**
         * Gets the value of the role property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the role property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRole().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link MultiValuedAttribute }
         * 
         * 
         */
        public List<MultiValuedAttribute> getRole() {
            if (role == null) {
                role = new ArrayList<>();
            }
            return this.role;
        }

    }


    /**
     * Java class for anonymous complex type.
     */
    public static class X509Certificates {

        protected List<MultiValuedAttribute> x509Certificate;

        /**
         * Gets the value of the x509Certificate property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the x509Certificate property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getX509Certificate().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link MultiValuedAttribute }
         * 
         * 
         */
        public List<MultiValuedAttribute> getX509Certificate() {
            if (x509Certificate == null) {
                x509Certificate = new ArrayList<>();
            }
            return this.x509Certificate;
        }

    }

}
