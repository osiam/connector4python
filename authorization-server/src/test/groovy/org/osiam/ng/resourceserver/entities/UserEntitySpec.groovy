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

package org.osiam.ng.resourceserver.entities

import org.springframework.security.core.userdetails.UserDetails
import scim.schema.v2.Address
import scim.schema.v2.MultiValuedAttribute
import scim.schema.v2.Name
import scim.schema.v2.User
import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 16:18
 * To change this template use File | Settings | File Templates.
 */
class UserEntitySpec extends Specification {

    UserEntity userEntity = new UserEntity()

    def "should inherit UserDetails"(){
        given:
        def roles = [new RolesEntity()] as Set<RolesEntity>
        when:
        userEntity.setUsername("username")
        userEntity.setRoles(roles)
        then:
        userEntity instanceof UserDetails
        userEntity.getAuthorities() == roles
        //not correctly set yet
        userEntity.isAccountNonExpired()
        userEntity.isAccountNonLocked()
        userEntity.isCredentialsNonExpired()
        userEntity.isEnabled()

    }

    def "setter and getter for the Id should be present"() {
        when:
        userEntity.setId(123456)

        then:
        userEntity.getId() == 123456
    }

    def "setter and getter for the external Id should be present"() {
        when:
        userEntity.setExternalId("123")

        then:
        userEntity.getExternalId() == "123"
    }

    def "setter and getter for the username should be present"() {
        when:
        userEntity.setUsername("bjensen@example.com")

        then:
        userEntity.getUsername() == "bjensen@example.com"
    }

    def "setter and getter for the name should be present"() {
        given:
        def name = Mock(NameEntity)

        when:
        userEntity.setName(name)

        then:
        userEntity.getName() == name
    }

    def "setter and getter for the display name should be present"() {
        when:
        userEntity.setDisplayName("Babs Jensen")

        then:
        userEntity.getDisplayName() == "Babs Jensen"
    }

    def "setter and getter for the nick name should be present"() {
        when:
        userEntity.setNickName("Babs")

        then:
        userEntity.getNickName() == "Babs"
    }

    def "setter and getter for the profile URL should be present"() {
        when:
        userEntity.setProfileUrl("https://login.example.com/bjensen")

        then:
        userEntity.getProfileUrl() == "https://login.example.com/bjensen"
    }

    def "setter and getter for the title should be present"() {
        when:
        userEntity.setTitle("Tour Guide")

        then:
        userEntity.getTitle() == "Tour Guide"
    }

    def "setter and getter for the user type should be present"() {
        when:
        userEntity.setUserType("Employee")

        then:
        userEntity.getUserType() == "Employee"
    }

    def "setter and getter for the preferred language should be present"() {
        when:
        userEntity.setPreferredLanguage("en_US")

        then:
        userEntity.getPreferredLanguage() == "en_US"
    }

    def "setter and getter for the locale should be present"() {
        when:
        userEntity.setLocale("en_US")

        then:
        userEntity.getLocale() == "en_US"
    }

    def "setter and getter for the timezone should be present"() {
        when:
        userEntity.setTimezone("America/Los_Angeles")

        then:
        userEntity.getTimezone() == "America/Los_Angeles"
    }

    def "setter and getter for the active should be present"() {
        when:
        userEntity.setActive(true)

        then:
        userEntity.getActive()
    }

    def "setter and getter for the password should be present"() {
        when:
        userEntity.setPassword("changeMe")

        then:
        userEntity.getPassword() == "changeMe"
    }

    def "setter and getter for the emails should be present"() {
        given:
        def emails = [EmailEntity] as Set<EmailEntity>

        when:
        userEntity.setEmails(emails)

        then:
        userEntity.getEmails() == emails
    }

    def "setter and getter for the phoneNumbers should be present"() {
        given:
        def phoneNumbers = [PhoneNumberEntity] as Set<PhoneNumberEntity>

        when:
        userEntity.setPhoneNumbers(phoneNumbers)

        then:
        userEntity.getPhoneNumbers() == phoneNumbers
    }

    def "setter and getter for the ims should be present"() {
        given:
        def ims = [ImEntity] as Set<ImEntity>

        when:
        userEntity.setIms(ims)

        then:
        userEntity.getIms() == ims
    }

    def "setter and getter for the photos should be present"() {
        given:
        def photos = [PhotoEntity] as Set<PhotoEntity>

        when:
        userEntity.setPhotos(photos)

        then:
        userEntity.getPhotos() == photos
    }

    def "setter and getter for the addresses should be present"() {
        given:
        def addresses = [AddressEntity] as Set<AddressEntity>

        when:
        userEntity.setAddresses(addresses)

        then:
        userEntity.getAddresses() == addresses
    }

    def "setter and getter for the groups should be present"() {
        given:
        def groups = [GroupEntity] as Set<GroupEntity>

        when:
        userEntity.setGroups(groups)

        then:
        userEntity.getGroups() == groups
    }

    def "setter and getter for the entitlements should be present"() {
        given:
        def entitlements = [EntitlementsEntity] as Set<EntitlementsEntity>

        when:
        userEntity.setEntitlements(entitlements)

        then:
        userEntity.getEntitlements() == entitlements
    }

    def "setter and getter for the roles should be present"() {
        given:
        def roles = [RolesEntity] as Set<RolesEntity>

        when:
        userEntity.setRoles(roles)

        then:
        userEntity.getRoles() == roles
    }

    def "setter and getter for the certificates should be present"() {
        given:
        def certs = [X509CertificateEntity] as Set<X509CertificateEntity>

        when:
        userEntity.setX509Certificates(certs)

        then:
        userEntity.getX509Certificates() == certs
    }

    def "setter and getter for the any field should be present"() {
        given:
        def any = ["any"] as Set<String>

        when:
        userEntity.setAny(any)

        then:
        userEntity.getAny() == any
    }

    def "setter and getter for internalID should be present and it must be a UUID"() {
        given:
        def internalId = UUID.randomUUID()

        when:
        userEntity.setInternalId(internalId)

        then:
        internalId == userEntity.getInternalId()
    }

    def name = new NameEntity()
    def scimName

    def setup() {
        name.setFamilyName("familyName")
        name.setFormatted("formattedName")
        name.setGivenName("givenName")
        name.setHonorificPrefix("prefix")
        name.setHonorificSuffix("suffix")
        name.setId(1234)
        name.setMiddleName("middleName")


        scimName = new Name.Builder().
                setFamilyName("familyName").
                setFormatted("formattedName").
                setGivenName("givenName").
                setHonorificPrefix("prefix").
                setHonorificSuffix("suffix").
                setMiddleName("middleName").
                build()

        addresses.getAddress().add(new Address.Builder().
                setCountry("country").
                setFormatted("formatted").
                setLocality("locality").
                setPostalCode("123456").
                setRegion("region").
                setStreetAddress("streetAddress").setPrimary(true).
                build())

        emails.getEmail().add(new MultiValuedAttribute.Builder().
                setPrimary(true).
                setType("type").
                setValue("value").
                build())

        entitlements.getEntitlement().add(new MultiValuedAttribute.Builder().
                setValue("value").
                build())

        groups.getGroup().add(new MultiValuedAttribute.Builder().
                setValue(UUID.randomUUID().toString()).
                setDisplay("display").
                build())

        ims.getIm().add(new MultiValuedAttribute.Builder().
                setValue("blaaaa").
                setType("type").
                build())

        phoneNumbers.getPhoneNumber().add(new MultiValuedAttribute.Builder().
                setValue("blaaaa").
                setType("type").
                build())

        photos.getPhoto().add(new MultiValuedAttribute.Builder().
                setValue("blaaaa").
                setType("type").
                build())

        roles.getRole().add(new MultiValuedAttribute.Builder().
                setValue("blaaaa").
                build())

        certificates.getX509Certificate().add(new MultiValuedAttribute.Builder().
                setValue("blaaaa").
                build())
    }

    def "should be possible to map a user entity to a scim user class"() {
        given:
        def internalId = UUID.randomUUID()

        userEntity.setInternalId(internalId)
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
        def user = userEntity.toScim()

        then:
        user.id == internalId.toString()
        user.isActive()
        user.addresses != null
        user.any.size() == 2
        user.displayName == "displayName"
        user.emails != null
        user.entitlements != null
        user.externalId == "externalId"
        user.groups != null
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
        def internalId = UUID.randomUUID()

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
        userEntity.setInternalId(internalId)

        when:
        def user = userEntity.toScim()

        then:
        user.id == internalId.toString()
        user.isActive()
        user.addresses != null
        user.any.size() == 2
        user.displayName == "displayName"
        user.emails != null
        user.entitlements != null
        user.externalId == "externalId"
        user.groups != null
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

    User.Addresses addresses = new User.Addresses()
    User.Emails emails = new User.Emails()
    User.Entitlements entitlements = new User.Entitlements()
    User.Groups groups = new User.Groups()
    User.Ims ims = new User.Ims()
    User.PhoneNumbers phoneNumbers = new User.PhoneNumbers()
    User.Photos photos = new User.Photos()
    User.Roles roles = new User.Roles()
    User.X509Certificates certificates = new User.X509Certificates()

    def "should be possible to map a scim user class to a user entity"() {
        given:
        def internalId = UUID.randomUUID()

        User user = new User.Builder("username").
                setActive(true).
                setAddresses(addresses).
                setAny(["test","this", "stuff"] as Set).
                setDisplayName("displayname").
                setEmails(emails).
                setEntitlements(entitlements).
                setGroups(groups).
                setIms(ims).
                setLocale("locale").
                setName(scimName).
                setNickName("nickname").
                setPassword("password").
                setPhoneNumbers(phoneNumbers).
                setPhotos(photos).
                setPreferredLanguage("preferredLanguage").
                setProfileUrl("profileUrl").
                setRoles(roles).
                setTimezone("timeZone").
                setTitle("title").
                setUserType("userType").
                setX509Certificates(certificates).
                setExternalId("externalId").
                setId(internalId.toString()).
                build()

        when:
        def userEntity = UserEntity.fromScim(user)

        then:
        userEntity.getUsername() == "username"
        userEntity.getAny() == ["test","this", "stuff"] as Set
        userEntity.getDisplayName() == "displayname"
        userEntity.getLocale() == "locale"
        userEntity.getNickName() == "nickname"
        userEntity.getPassword() == "password"
        userEntity.getPreferredLanguage() == "preferredLanguage"
        userEntity.getProfileUrl() == "profileUrl"
        userEntity.getTimezone() == "timeZone"
        userEntity.getTitle() == "title"
        userEntity.getUserType() == "userType"
        userEntity.getExternalId() == "externalId"
    }
}