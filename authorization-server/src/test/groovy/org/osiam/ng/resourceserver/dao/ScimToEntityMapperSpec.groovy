package org.osiam.ng.resourceserver.dao

import scim.schema.v2.Address
import scim.schema.v2.MultiValuedAttribute
import scim.schema.v2.Name
import scim.schema.v2.User
import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 21.03.13
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 */
class ScimToEntityMapperSpec extends Specification {

    ScimToEntityMapper scimToEntityMapper = new ScimToEntityMapper()
    Name name
    User.Addresses addresses = new User.Addresses()
    User.Emails emails = new User.Emails()
    User.Entitlements entitlements = new User.Entitlements()
    User.Groups groups = new User.Groups()
    User.Ims ims = new User.Ims()
    User.PhoneNumbers phoneNumbers = new User.PhoneNumbers()
    User.Photos photos = new User.Photos()
    User.Roles roles = new User.Roles()
    User.X509Certificates certificates = new User.X509Certificates()

    def setup() {
        name = new Name.Builder().
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

    def "should be possible to map a scim user class to a user entity"() {
        given:
        User user = new User.Builder("username").
                setActive(true).
                setAddresses(addresses).
                setAny(["test","this", "stuff"]).
                setDisplayName("displayname").
                setEmails(emails).
                setEntitlements(entitlements).
                setGroups(groups).
                setIms(ims).
                setLocale("locale").
                setName(name).
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
                build()

        when:
        def userEntity = scimToEntityMapper.scimUserToEntity(user)

        then:
        userEntity != null
    }
}