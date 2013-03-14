package scim.schema.v2

import spock.lang.Specification

class UserTest extends Specification {
    def "userName is a required field so it should throw an exception when setting it null"() {
        when:
        new User.Builder(null)
        then:
        def e = thrown(IllegalArgumentException)
        e.message == "userName must not be null."
    }


    def "should generate a user based on builder"() {
        expect:
        User user = builder.build()
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



        where:
        builder << [new User.Builder("test").setActive(true),
                new User.Builder("test2").setAddresses(new User.Addresses()),
                new User.Builder("test").setAny(["ha"]),
                new User.Builder("test").setDisplayName("display"),
                new User.Builder("test").setEmails(new User.Emails()),
                new User.Builder("test").setEntitlements(new User.Entitlements()),
                new User.Builder("test").setGroups(new User.Groups()),
                new User.Builder("test").setIms(new User.Ims()),
                new User.Builder("test").setLocale("locale"),
                new User.Builder("test").setName(new Name()),
                new User.Builder("test").setNickName("nickname"),
                new User.Builder("test").setPassword("password"),
                new User.Builder("test").setPhoneNumbers(new User.PhoneNumbers()),
                new User.Builder("test").setPhotos(new User.Photos()),
                new User.Builder("test").setPreferredLanguage("prefereedLanguage"),
                new User.Builder("test").setProfileUrl("profileUrl"),
                new User.Builder("test").setRoles(new User.Roles()),
                new User.Builder("test").setTimezone("time"),
                new User.Builder("test").setTitle("title"),
                new User.Builder("test").setUserType("userType"),
                new User.Builder("test").setX509Certificates(new User.X509Certificates())
        ]
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
        def address = new Address()
        def generalAttribute = new MultiValuedAttribute()

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
