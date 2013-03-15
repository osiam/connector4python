package org.osiam.ng.resourceserver.entities

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
        userEntity.setUserName("bjensen@example.com")

        then:
        userEntity.getUserName() == "bjensen@example.com"
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
        userEntity.getActive() == true
    }

    def "setter and getter for the password should be present"() {
        when:
        userEntity.setPassword("changeMe")

        then:
        userEntity.getPassword() == "changeMe"
    }

    def "setter and getter for the emails should be present"() {
        given:
        def emails = new ArrayList<EmailEntity>()

        when:
        userEntity.setEmails(emails)

        then:
        userEntity.getEmails() == emails
    }

    def "setter and getter for the phoneNumbers should be present"() {
        given:
        def phoneNumbers = new ArrayList<PhoneNumberEntity>()

        when:
        userEntity.setPhoneNumbers(phoneNumbers)

        then:
        userEntity.getPhoneNumbers() == phoneNumbers
    }

    def "setter and getter for the ims should be present"() {
        given:
        def ims = new ArrayList<ImEntity>()

        when:
        userEntity.setIms(ims)

        then:
        userEntity.getIms() == ims
    }

    def "setter and getter for the photos should be present"() {
        given:
        def photos = new ArrayList<PhotoEntity>()

        when:
        userEntity.setPhotos(photos)

        then:
        userEntity.getPhotos() == photos
    }

    def "setter and getter for the addresses should be present"() {
        given:
        def addresses = new ArrayList<AddressEntity>()

        when:
        userEntity.setAddresses(addresses)

        then:
        userEntity.getAddresses() == addresses
    }

    def "setter and getter for the groups should be present"() {
        given:
        def groups = new ArrayList<GroupEntity>()

        when:
        userEntity.setGroups(groups)

        then:
        userEntity.getGroups() == groups
    }

    def "setter and getter for the entitlements should be present"() {
        given:
        def entitlements = new ArrayList<EntitlementsEntity>()

        when:
        userEntity.setEntitlements(entitlements)

        then:
        userEntity.getEntitlements() == entitlements
    }

    def "setter and getter for the roles should be present"() {
        given:
        def roles = new ArrayList<RolesEntity>()

        when:
        userEntity.setRoles(roles)

        then:
        userEntity.getRoles() == roles
    }

    def "setter and getter for the certificates should be present"() {
        given:
        def certs = new ArrayList<X509CertificateEntity>()

        when:
        userEntity.setX509Certificates(certs)

        then:
        userEntity.getX509Certificates() == certs
    }

    def "setter and getter for the any field should be present"() {
        given:
        def any = new ArrayList<Object>()

        when:
        userEntity.setAny(any)

        then:
        userEntity.getAny() == any
    }
}