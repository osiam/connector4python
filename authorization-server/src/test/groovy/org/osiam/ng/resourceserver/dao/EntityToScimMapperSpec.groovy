package org.osiam.ng.resourceserver.dao

import org.osiam.ng.resourceserver.entities.AddressEntity
import org.osiam.ng.resourceserver.entities.EmailEntity
import org.osiam.ng.resourceserver.entities.EntitlementsEntity
import org.osiam.ng.resourceserver.entities.GroupEntity
import org.osiam.ng.resourceserver.entities.ImEntity
import org.osiam.ng.resourceserver.entities.NameEntity
import org.osiam.ng.resourceserver.entities.PhoneNumberEntity
import org.osiam.ng.resourceserver.entities.PhotoEntity
import org.osiam.ng.resourceserver.entities.RolesEntity
import org.osiam.ng.resourceserver.entities.UserEntity
import org.osiam.ng.resourceserver.entities.X509CertificateEntity
import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 19.03.13
 * Time: 17:57
 * To change this template use File | Settings | File Templates.
 */
class EntityToScimMapperSpec extends Specification {

    EntityToScimMapper entityToScimMapper = new EntityToScimMapper()
    def userEntity = new UserEntity()
    def name = new NameEntity()

    def setup() {
        name.setFamilyName("familyName")
        name.setFormatted("formattedName")
        name.setGivenName("givenName")
        name.setHonorificPrefix("prefix")
        name.setHonorificSuffix("suffix")
        name.setId(1234)
        name.setMiddleName("middleName")
    }

    def "should be possible to map a user entity to a scim user class"() {
        given:
        userEntity.setActive(true)
        userEntity.setAddresses([Mock(AddressEntity)] as Set<AddressEntity>)
        userEntity.setAny(["stuff", "bro"] as Set<String>)
        userEntity.setDisplayName("displayName")
        userEntity.setEmails([Mock(EmailEntity)] as Set<EmailEntity>)
        userEntity.setEntitlements([Mock(EntitlementsEntity)] as Set<EntitlementsEntity>)
        userEntity.setExternalId("externalId")
        userEntity.setGroups([Mock(GroupEntity)] as Set<GroupEntity>)
        userEntity.setId(123456)
        userEntity.setIms([Mock(ImEntity)] as Set<ImEntity>)
        userEntity.setLocale("locale")
        userEntity.setName(name)
        userEntity.setNickName("nickName")
        userEntity.setPassword("topS3cr3t")
        userEntity.setPhoneNumbers([Mock(PhoneNumberEntity)] as Set<PhoneNumberEntity>)
        userEntity.setPhotos([Mock(PhotoEntity)] as Set<PhotoEntity>)
        userEntity.setPreferredLanguage("preferredLanguage")
        userEntity.setProfileUrl("profileURL")
        userEntity.setRoles([Mock(RolesEntity)] as Set<RolesEntity>)
        userEntity.setTimezone("timeZone")
        userEntity.setTitle("title")
        userEntity.setUsername("userName")
        userEntity.setUserType("userType")
        userEntity.setX509Certificates([Mock(X509CertificateEntity)] as Set<X509CertificateEntity>)
        userEntity.setExternalId("externalId")

        when:
        def user = entityToScimMapper.entityUserToScim(userEntity)

        then:
        user.isActive()
        user.addresses != null
        user.any == ["stuff", "bro"]
        user.displayName == "displayName"
        user.emails != null
        user.entitlements != null
        user.externalId == "externalId"
        user.groups != null
        user.id == null
        user.ims != null
        user.locale == "locale"
        user.name != null
        user.nickName == "nickName"
        user.password == "topS3cr3t"
        user.phoneNumbers != null
        user.photos != null
        user.preferredLanguage == "preferredLanguage"
        user.profileUrl == "profileURL"
        user.roles != null
        user.timezone == "timeZone"
        user.title == "title"
        user.userName == "userName"
        user.userType == "userType"
        user.x509Certificates != null
    }

    def "should be possible to map a user entity to a scim user class without setting the name"() {
        given:
        userEntity.setActive(true)
        userEntity.setAddresses([Mock(AddressEntity)] as Set<AddressEntity>)
        userEntity.setAny(["stuff", "bro"] as Set<String>)
        userEntity.setDisplayName("displayName")
        userEntity.setEmails([Mock(EmailEntity)] as Set<EmailEntity>)
        userEntity.setEntitlements([Mock(EntitlementsEntity)] as Set<EntitlementsEntity>)
        userEntity.setExternalId("externalId")
        userEntity.setGroups([Mock(GroupEntity)] as Set<GroupEntity>)
        userEntity.setId(123456)
        userEntity.setIms([Mock(ImEntity)] as Set<ImEntity>)
        userEntity.setLocale("locale")
        userEntity.setName(null)
        userEntity.setNickName("nickName")
        userEntity.setPassword("topS3cr3t")
        userEntity.setPhoneNumbers([Mock(PhoneNumberEntity)] as Set<PhoneNumberEntity>)
        userEntity.setPhotos([Mock(PhotoEntity)] as Set<PhotoEntity>)
        userEntity.setPreferredLanguage("preferredLanguage")
        userEntity.setProfileUrl("profileURL")
        userEntity.setRoles([Mock(RolesEntity)] as Set<RolesEntity>)
        userEntity.setTimezone("timeZone")
        userEntity.setTitle("title")
        userEntity.setUsername("userName")
        userEntity.setUserType("userType")
        userEntity.setX509Certificates([Mock(X509CertificateEntity)] as Set<X509CertificateEntity>)
        userEntity.setExternalId("externalId")

        when:
        def user = entityToScimMapper.entityUserToScim(userEntity)

        then:
        user.isActive()
        user.addresses != null
        user.any == ["stuff", "bro"]
        user.displayName == "displayName"
        user.emails != null
        user.entitlements != null
        user.externalId == "externalId"
        user.groups != null
        user.id == null
        user.ims != null
        user.locale == "locale"
        user.name == null
        user.nickName == "nickName"
        user.password == "topS3cr3t"
        user.phoneNumbers != null
        user.photos != null
        user.preferredLanguage == "preferredLanguage"
        user.profileUrl == "profileURL"
        user.roles != null
        user.timezone == "timeZone"
        user.title == "title"
        user.userName == "userName"
        user.userType == "userType"
        user.x509Certificates != null
    }
}