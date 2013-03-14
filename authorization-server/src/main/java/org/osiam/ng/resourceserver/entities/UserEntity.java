package org.osiam.ng.resourceserver.entities;

import javax.persistence.*;
import java.util.List;

/**
 * User Entity
 */
@Entity
@NamedQueries({@NamedQuery(name = "getUserById", query = "SELECT u FROM UserEntity u WHERE u.id = :id")})
public class UserEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
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

    @Column
    private String password;

    @ManyToOne
    private EmailsEntity emails;

    @ManyToOne
    private PhoneNumbersEntity phoneNumbers;

    @ManyToOne
    private ImsEntity ims;

    @ManyToOne
    private PhotosEntity photos;

    @ManyToOne
    private AddressesEntity addresses;

    @ManyToMany
    private GroupsEntity groups;

    @ManyToOne
    private EntitlementsEntity entitlements;

    @ManyToOne
    private RolesEntity roles;

    @ManyToOne
    private X509CertificatesEntity x509Certificates;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EmailsEntity getEmails() {
        return emails;
    }

    public void setEmails(EmailsEntity emails) {
        this.emails = emails;
    }

    public PhoneNumbersEntity getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(PhoneNumbersEntity phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public ImsEntity getIms() {
        return ims;
    }

    public void setIms(ImsEntity ims) {
        this.ims = ims;
    }

    public PhotosEntity getPhotos() {
        return photos;
    }

    public void setPhotos(PhotosEntity photos) {
        this.photos = photos;
    }

    public AddressesEntity getAddresses() {
        return addresses;
    }

    public void setAddresses(AddressesEntity addresses) {
        this.addresses = addresses;
    }

    public GroupsEntity getGroups() {
        return groups;
    }

    public void setGroups(GroupsEntity groups) {
        this.groups = groups;
    }

    public EntitlementsEntity getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(EntitlementsEntity entitlements) {
        this.entitlements = entitlements;
    }

    public RolesEntity getRoles() {
        return roles;
    }

    public void setRoles(RolesEntity roles) {
        this.roles = roles;
    }

    public X509CertificatesEntity getX509Certificates() {
        return x509Certificates;
    }

    public void setX509Certificates(X509CertificatesEntity x509Certificates) {
        this.x509Certificates = x509Certificates;
    }

    public List<Object> getAny() {
        return any;
    }

    public void setAny(List<Object> any) {
        this.any = any;
    }
}
