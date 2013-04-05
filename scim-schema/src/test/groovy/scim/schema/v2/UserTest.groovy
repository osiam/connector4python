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

package scim.schema.v2

import spock.lang.Specification

class UserTest extends Specification {

    def "default constructor should be present due to json mappings"() {
        when:
        def user = new User()

        then:
        user != null
    }

    def "user should contain core schemas as default"() {
        when:
        def user = new User.Builder("username").build()
        then:
        user.schemas == Constants.CORE_SCHEMAS;
    }

    def "user should be able to create an user without name for PATCH"() {
        when:
        def user = new User.Builder().build()
        then:
        user.schemas == Constants.CORE_SCHEMAS;
        user.name == null
    }


    def "user should be able to contain schemas"(){
        def schemas = ["urn:wtf", "urn:hajo"] as Set
        when:
        User user = new User.Builder("username").setSchemas(schemas).build()
        then:
        user.schemas == schemas

    }

    def "user should clone schemas"(){
        def schemas = ["urn:wtf", "urn:hajo"] as Set
        User oldUser = new User.Builder("username").setSchemas(schemas).build()
        when:
        User user = User.Builder.generateForOuput(oldUser);
        then:
        user.schemas == oldUser.schemas

    }

    def "userName is a required field so it should throw an exception when setting it null"() {
        when:
        new User.Builder(null)
        then:
        def e = thrown(IllegalArgumentException)
        e.message == "userName must not be null."
    }


    def "should generate a user based on builder"() {
        given:
        def builder = new User.Builder("test").setActive(true)
                .setAddresses(new User.Addresses())
                .setAny(["ha"] as Set)
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
                .setExternalId("externalid").setId("id").setMeta(new Meta.Builder().build())
        when:
        User user = builder.build()
        then:
        user.active == builder.active
        user.addresses == builder.addresses
        user.any == builder.any
        user.displayName == builder.displayName
        user.emails == builder.emails
        user.entitlements == builder.entitlements
        user.groups == builder.groups
        user.ims == builder.ims
        user.locale == builder.locale
        user.name == builder.name
        user.nickName == builder.nickName
        user.password == builder.password
        user.phoneNumbers == builder.phoneNumbers
        user.photos == builder.photos
        user.preferredLanguage == builder.preferredLanguage
        user.profileUrl == builder.profileUrl
        user.roles == builder.roles
        user.timezone == builder.timezone
        user.title == builder.title
        user.userType == builder.userType
        user.x509Certificates == builder.x509Certificates
        user.userName == builder.userName
        user.id == builder.id
        user.externalId == builder.externalId
        user.meta == builder.meta
    }

    def "should ignore password on output"() {
        given:
        User user = new User.Builder("test").setActive(true)
                .setAddresses(new User.Addresses())
                .setAny(["ha"] as Set)
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
                .setExternalId("externalid").setId("id").setMeta(new Meta.Builder().build())
                .build()
        when:
        User clonedUser = User.Builder.generateForOuput(user)
        then:
        clonedUser.password == null

        clonedUser.timezone == user.timezone
        clonedUser.title == user.title
        clonedUser.userType == user.userType
        clonedUser.userName == user.userName
        clonedUser.id == user.id
        clonedUser.externalId == user.externalId
        clonedUser.meta == user.meta
        clonedUser.active == user.active
        clonedUser.any == user.any
        clonedUser.displayName == user.displayName
        clonedUser.locale == user.locale
        clonedUser.name == user.name
        clonedUser.nickName == user.nickName
        clonedUser.preferredLanguage == user.preferredLanguage
        clonedUser.profileUrl == user.profileUrl
    }

    def "should null empty lists for pretty output"() {
        given:
        User user = new User.Builder("test").setActive(true)
                .setAddresses(new User.Addresses())
                .setAny(["ha"] as Set)
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
                .setExternalId("externalid").setId("id").setMeta(new Meta.Builder().build())
                .build()
        when:
        User clonedUser = User.Builder.generateForOuput(user)
        then:
        clonedUser.addresses == null
        clonedUser.emails == null
        clonedUser.entitlements == null
        clonedUser.groups == null
        clonedUser.ims == null
        clonedUser.phoneNumbers == null
        clonedUser.photos == null
        clonedUser.roles == null
        clonedUser.x509Certificates == null
    }

    def "should copy lists from origin user"() {
        given:
        def address = new Address.Builder().build()
        def generalAttribute = new MultiValuedAttribute.Builder().build()

        User user = new User.Builder("test").setActive(true)
                .setAddresses(new User.Addresses())
                .setAny(["ha"] as Set)
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
                .setExternalId("externalid").setId("id").setMeta(new Meta.Builder().build())
                .build()

        user.addresses.address.add(address)
        user.emails.email.add(generalAttribute)
        user.entitlements.entitlement.add(generalAttribute)
        user.groups.group.add(generalAttribute)
        user.ims.im.add(generalAttribute)
        user.phoneNumbers.phoneNumber.add(generalAttribute)
        user.photos.photo.add(generalAttribute)
        user.roles.role.add(generalAttribute)
        user.x509Certificates.x509Certificate.add(generalAttribute)

        when:
        User clonedUser = User.Builder.generateForOuput(user)
        then:
        clonedUser.addresses.address.get(0) == address
        clonedUser.emails.email.get(0) == generalAttribute
        clonedUser.entitlements.entitlement.get(0) == generalAttribute
        clonedUser.groups.group.get(0) == generalAttribute
        clonedUser.ims.im.get(0) == generalAttribute
        clonedUser.phoneNumbers.phoneNumber.get(0) == generalAttribute
        clonedUser.photos.photo.get(0) == generalAttribute
        clonedUser.roles.role.get(0) == generalAttribute
        clonedUser.x509Certificates.x509Certificate.get(0) == generalAttribute

    }


    def "should be able to enrich addresses, emails, entitlements, groups, phone-numbers, photos, roles and certificates"() {
        given:
        def user = new User.Builder("test2").setAddresses(new User.Addresses())
                .setEmails(new User.Emails())
                .setEntitlements(new User.Entitlements())
                .setGroups(new User.Groups())
                .setIms(new User.Ims())
                .setPhoneNumbers(new User.PhoneNumbers())
                .setPhotos(new User.Photos())
                .setRoles(new User.Roles())
                .setX509Certificates(new User.X509Certificates()).build()
        def address = new Address.Builder().build()
        def generalAttribute = new MultiValuedAttribute.Builder().build()

        when:
        user.addresses.address.add(address)
        user.emails.email.add(generalAttribute)
        user.entitlements.entitlement.add(generalAttribute)
        user.groups.group.add(generalAttribute)
        user.ims.im.add(generalAttribute)
        user.phoneNumbers.phoneNumber.add(generalAttribute)
        user.photos.photo.add(generalAttribute)
        user.roles.role.add(generalAttribute)
        user.x509Certificates.x509Certificate.add(generalAttribute)

        and:
        user.addresses.address.get(0) == address
        and:
        user.emails.email.get(0) == generalAttribute
        and:
        user.entitlements.entitlement.get(0) == generalAttribute
        and:
        user.groups.group.get(0) == generalAttribute
        and:
        user.ims.im.get(0) == generalAttribute
        and:
        user.phoneNumbers.phoneNumber.get(0) == generalAttribute
        and:
        user.photos.photo.get(0) == generalAttribute
        and:
        user.roles.role.get(0) == generalAttribute
        then:
        user.x509Certificates.x509Certificate.get(0) == generalAttribute


    }


}
