package org.osiam.ng.resourceserver.dao

import org.osiam.ng.resourceserver.entities.UserEntity
import org.osiam.ng.scim.exceptions.ResourceExistsException
import scim.schema.v2.Meta
import scim.schema.v2.Name
import scim.schema.v2.User
import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 19.03.13
 * Time: 17:51
 * To change this template use File | Settings | File Templates.
 */
class ScimUserProvisioningBeanSpec extends Specification {


    def userDao = Mock(UserDAO)
    def userEntity = Mock(UserEntity)
    def scimUser = Mock(User)
    ScimUserProvisioningBean scimUserProvisioningBean =
        new ScimUserProvisioningBean(userDao: userDao)


    def "should be possible to get an user by his id"() {
        given:
        userDao.getById("1234") >> userEntity
        userEntity.toScim() >> scimUser

        when:
        def user = scimUserProvisioningBean.getById("1234")

        then:
        user == scimUser
    }

    def "should be possible to create a user"() {
        given:
        userEntity.toScim() >> scimUser

        when:
        def user = scimUserProvisioningBean.createUser(scimUser)

        then:
        user == scimUser
    }

    def "should throw exception if user already exists"() {
        given:
        userDao.createUser(_) >> {throw new Exception()}
        scimUser.getUserName() >> "Bäm"

        when:
        scimUserProvisioningBean.createUser(scimUser)

        then:
        def e = thrown(ResourceExistsException)
        e.getMessage() == "The user with name Bäm already exists."
    }




    def "should get an user before update, set the expected fields, merge the result"() {
        given:
        def any = new HashSet()
        any.add("ha")
        def scimUser = new User.Builder("test").setActive(true)
                .setAddresses(new User.Addresses())
                .setAny(any)
                .setDisplayName("display")
                .setEmails(new User.Emails())
                .setEntitlements(new User.Entitlements())
                .setGroups(new User.Groups())
                .setIms(new User.Ims())
                .setLocale("locale")
                .setName(new Name.Builder().build())
                .setNickName("nickname")
                .setPassword("password")
                .setPhoneNumbers(new User.PhoneNumbers())
                .setPhotos(new User.Photos())
                .setPreferredLanguage("prefereedLanguage")
                .setProfileUrl("profileUrl")
                .setRoles(new User.Roles())
                .setTimezone("time")
                .setTitle("title")
                .setUserType("userType")
                .setX509Certificates(new User.X509Certificates())
                .setExternalId("externalid")
                .setId("id")
                .setMeta(new Meta.Builder().build())
                .build()

        def entity = new UserEntity()
        when:
        scimUserProvisioningBean.replaceUser("1234", scimUser)


        then:
        1 * userDao.getById("1234") >> entity
        1 * userDao.update(entity)

        scimUser.active == entity.active
        scimUser.any == entity.any
        scimUser.displayName == entity.displayName
        scimUser.locale == entity.locale
        scimUser.name.familyName == entity.name.familyName
        scimUser.nickName == entity.nickName
        scimUser.password == entity.password
        scimUser.preferredLanguage == entity.preferredLanguage
        scimUser.profileUrl == entity.profileUrl
        scimUser.timezone == entity.timezone
        scimUser.title == entity.title
        scimUser.userType == entity.userType
        scimUser.userName == entity.username
        scimUser.externalId == entity.externalId

        scimUser.x509Certificates.x509Certificate.size() == entity.x509Certificates.size()
        scimUser.roles.role.size() == entity.roles.size()
        scimUser.entitlements.entitlement.size() == entity.entitlements.size()
        !entity.groups
        scimUser.ims.im.size() == entity.ims.size()
        scimUser.emails.email.size() == entity.emails.size()
        scimUser.addresses.address.size() == entity.addresses.size()
        scimUser.phoneNumbers.phoneNumber.size() == entity.phoneNumbers.size()
        scimUser.photos.photo.size() == entity.photos.size()
    }
}