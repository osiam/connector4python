package org.osiam.ng.resourceserver.entities;

import javax.persistence.*;
import java.util.List;

/**
 * User Entity
 */
@Entity(name = "user")
@NamedQueries({@NamedQuery(name = "getUserById", query = "SELECT u FROM user u WHERE u.externalId = :externalId")})
public class UserEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String externalId;

    @Column(nullable = false, unique = true)
    private String userName;

    @OneToOne
    private NameEntity name;

    @Column
    private String displayName;

    @Column
    private String nickName;

    @Column
    private String profileUrl;

    @Column
    private String title;

    @Column
    private String userType;

    @Column
    private String preferredLanguage;

    @Column
    private String locale;

    @Column
    private String timezone;

    @Column
    private Boolean active;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    private List<EmailEntity> emails;

    @ManyToOne
    private List<PhoneNumberEntity> phoneNumbers;

    @ManyToOne
    private List<ImEntity> ims;

    @ManyToOne
    private List<PhotoEntity> photos;

    @ManyToOne
    private List<AddressEntity> addresses;

    @ManyToOne
    private List<GroupEntity> groups;

    @ManyToOne
    private List<EntitlementsEntity> entitlements;

    @ManyToOne
    private List<RolesEntity> roles;

    @ManyToOne
    private List<X509CertificateEntity> x509Certificates;

    @ManyToOne
    private List<Object> any;

    /**
     *
     * @param id
     *        the unique entity id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return
     *      the unique entity id
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @return
     *      the external id
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     *
     * @param externalId
     *          the external id
     */
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    /**
     *
     * @return
     *      the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     *          the user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return
     *      the name entity
     */
    public NameEntity getName() {
        return name;
    }

    /**
     *
     * @param name
     *          the name entity
     */
    public void setName(NameEntity name) {
        this.name = name;
    }

    /**
     *
     * @return
     *      the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     *
     * @param displayName
     *          the display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     *
     * @return
     *      the nick name
     */
    public String getNickName() {
        return nickName;
    }

    /**
     *
     * @param nickName
     *          the nick name
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     *
     * @return
     *      the profile url
     */
    public String getProfileUrl() {
        return profileUrl;
    }

    /**
     *
     * @param profileUrl
     *          the profile url
     */
    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    /**
     *
     * @return
     *      the title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     *          the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     *      the user type
     */
    public String getUserType() {
        return userType;
    }

    /**
     *
     * @param userType
     *          the user type
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     *
     * @return
     *      the preferred languages
     */
    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    /**
     *
     * @param preferredLanguage
     *              the preferred languages
     */
    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    /**
     *
     * @return
     *      the locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     *
     * @param locale
     *          the locale
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    /**
     *
     * @return
     *      the timezone
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     *
     * @param timezone
     *          the timezone
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     *
     * @return
     *      the active status
     */
    public Boolean getActive() {
        return active;
    }

    /**
     *
     * @param active
     *          the active status
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     *
     * @return
     *      the password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     *          the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     *      the emails entity
     */
    public List<EmailEntity> getEmails() {
        return emails;
    }

    /**
     *
     * @param emails
     *          the emails entity
     */
    public void setEmails(List<EmailEntity> emails) {
        this.emails = emails;
    }

    /**
     *
     * @return
     *      the phone numbers entity
     */
    public List<PhoneNumberEntity> getPhoneNumbers() {
        return phoneNumbers;
    }

    /**
     *
     * @param phoneNumbers
     *      the phone numbers entity
     */
    public void setPhoneNumbers(List<PhoneNumberEntity> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    /**
     *
     * @return
     *      the instant messaging entity
     */
    public List<ImEntity> getIms() {
        return ims;
    }

    /**
     *
     * @param ims
     *          the instant messaging entity
     */
    public void setIms(List<ImEntity> ims) {
        this.ims = ims;
    }

    /**
     *
     * @return
     *      the photos entity
     */
    public List<PhotoEntity> getPhotos() {
        return photos;
    }

    /**
     *
     * @param photos
     *          the photos entity
     */
    public void setPhotos(List<PhotoEntity> photos) {
        this.photos = photos;
    }

    /**
     *
     * @return
     *      the addresses entity
     */
    public List<AddressEntity> getAddresses() {
        return addresses;
    }

    /**
     *
     * @param addresses
     *          the addresses entity
     */
    public void setAddresses(List<AddressEntity> addresses) {
        this.addresses = addresses;
    }

    /**
     *
     * @return
     *      the groups entity
     */
    public List<GroupEntity> getGroups() {
        return groups;
    }

    /**
     *
     * @param groups
     *          the groups entity
     */
    public void setGroups(List<GroupEntity> groups) {
        this.groups = groups;
    }

    /**
     *
     * @return
     *      the entitlements
     */
    public List<EntitlementsEntity> getEntitlements() {
        return entitlements;
    }

    /**
     *
     * @param entitlements
     *          the entitlements
     */
    public void setEntitlements(List<EntitlementsEntity> entitlements) {
        this.entitlements = entitlements;
    }

    /**
     *
     * @return
     *      the roles
     */
    public List<RolesEntity> getRoles() {
        return roles;
    }

    /**
     *
     * @param roles
     *          the roles
     */
    public void setRoles(List<RolesEntity> roles) {
        this.roles = roles;
    }

    /**
     *
     * @return
     *      the X509 certs
     */
    public List<X509CertificateEntity> getX509Certificates() {
        return x509Certificates;
    }

    /**
     *
     * @param x509Certificates
     *          the X509 certs
     */
    public void setX509Certificates(List<X509CertificateEntity> x509Certificates) {
        this.x509Certificates = x509Certificates;
    }

    /**
     *
     * @return
     *      any
     */
    public List<Object> getAny() {
        return any;
    }

    /**
     *
     * @param any
     *      any
     */
    public void setAny(List<Object> any) {
        this.any = any;
    }
}