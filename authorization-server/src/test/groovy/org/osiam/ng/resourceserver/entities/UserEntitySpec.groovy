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
        def roles = [new RolesEntity()]
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