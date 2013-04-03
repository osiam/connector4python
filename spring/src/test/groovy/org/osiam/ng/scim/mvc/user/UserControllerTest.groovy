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

package org.osiam.ng.scim.mvc.user

import org.osiam.ng.scim.dao.SCIMUserProvisioning
import scim.schema.v2.Address
import scim.schema.v2.Meta
import scim.schema.v2.Name
import scim.schema.v2.User
import spock.lang.Specification
import sun.security.ssl.KeyManagerFactoryImpl

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserControllerTest extends Specification {

    def underTest = new UserController()
    def provisioning = Mock(SCIMUserProvisioning)
    def httpServletRequest = Mock(HttpServletRequest)
    def httpServletResponse = Mock(HttpServletResponse)
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


    def setup() {
        underTest.setScimUserProvisioning(provisioning)
    }

    def "should return a cloned user based on a user found by provisioning on getUser"() {
        when:
        def result = underTest.getUser("one")
        then:
        1 * provisioning.getById("one") >> user
        validateUser(result)


    }

    private boolean validateUser(User result) {
        return result != user &&
                user.password != null &&
                result.password == null &&
                result.active == user.active &&
                result.addresses == user.addresses &&
                result.any == user.any &&
                result.displayName == user.displayName &&
                result.emails == user.emails &&
                result.entitlements == user.entitlements &&
                result.groups == user.groups &&
                result.ims == user.ims &&
                result.locale == user.locale &&
                result.name == user.name &&
                result.nickName == user.nickName &&
                result.phoneNumbers == user.phoneNumbers &&
                result.photos == user.photos &&
                result.preferredLanguage == user.preferredLanguage &&
                result.profileUrl == user.profileUrl &&
                result.roles == user.roles &&
                result.timezone == user.timezone &&
                result.title == user.title &&
                result.userType == user.userType &&
                result.x509Certificates == user.x509Certificates &&
                result.userName == user.userName &&
                result.id == user.id &&
                result.externalId == user.externalId &&
                result.meta == user.meta
    }

    def "should create the user and add the location header"() {
        given:
        httpServletRequest.getRequestURL() >> new StringBuffer("http://host:port/deployment/User/")
        def uri = new URI("http://host:port/deployment/User/id")

        when:
        def result = underTest.createUser(user, httpServletRequest, httpServletResponse)

        then:
        1 * provisioning.createUser(user) >> user
        1 * httpServletResponse.setHeader("Location", uri.toASCIIString())
        validateUser(result)
    }

    def "should update an user and set location header"() {
        given:
        def id = UUID.randomUUID().toString()
        when:
        def result = underTest.updateUser(id, user, httpServletRequest, httpServletResponse)
        then:
        1 * provisioning.replaceUser(id, user) >> user
        1 * httpServletRequest.getRequestURL() >> new StringBuffer("http://localhorst/horst/yo")
        1 * httpServletResponse.setHeader("Location", "http://localhorst/horst/id")
        validateUser(result)
    }
}