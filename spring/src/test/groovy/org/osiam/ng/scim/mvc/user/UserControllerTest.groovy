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
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import scim.schema.v2.Meta
import scim.schema.v2.Name
import scim.schema.v2.User
import spock.lang.Ignore
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.lang.reflect.Method

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

    def "should contain a method to GET a user"(){
        given:
           Method method = UserController.class.getDeclaredMethod("getUser", String)
        when:
        RequestMapping mapping = method.getAnnotation(RequestMapping)
        ResponseBody body = method.getAnnotation(ResponseBody)
        then:
        mapping.value() == ["/{id}"]
        mapping.method() == [RequestMethod.GET]
        body
        //@RequestMapping(value = "/{id}", method = RequestMethod.GET)

    }

    def "should contain a method to POST a user"(){
        given:
        Method method = UserController.class.getDeclaredMethod("createUser", User, HttpServletRequest, HttpServletResponse)
        when:
        RequestMapping mapping = method.getAnnotation(RequestMapping)
        ResponseBody body = method.getAnnotation(ResponseBody)
        ResponseStatus defaultStatus = method.getAnnotation(ResponseStatus)
        then:
        mapping.method() == [RequestMethod.POST]
        body
        defaultStatus.value() == HttpStatus.CREATED
    }

    def "should contain a method to PUT a user"(){
        given:
        //createUser(@RequestBody User user,HttpServletRequest request, HttpServletResponse response)
        Method method = UserController.class.getDeclaredMethod("replace",String, User, HttpServletRequest, HttpServletResponse)
        when:
        RequestMapping mapping = method.getAnnotation(RequestMapping)
        ResponseBody body = method.getAnnotation(ResponseBody)
        ResponseStatus defaultStatus = method.getAnnotation(ResponseStatus)
        then:
        mapping.method() == [RequestMethod.PUT]
        body
        defaultStatus.value() == HttpStatus.OK
    }

    def "should contain a method to PATCH a user"(){
        given:
        Method method = UserController.class.getDeclaredMethod("update", String, User, HttpServletRequest, HttpServletResponse)
        when:
        RequestMapping mapping = method.getAnnotation(RequestMapping)
        ResponseBody body = method.getAnnotation(ResponseBody)
        ResponseStatus defaultStatus = method.getAnnotation(ResponseStatus)
        then:
        mapping.method() == [RequestMethod.PATCH]
        body
        defaultStatus.value() == HttpStatus.OK
    }



    def validateUser(User result) {
        assert result != user
        assert user.password != null
        assert result.password == null
        assert result.active == user.active
        assert result.addresses == null
        assert result.any == user.any
        assert result.displayName == user.displayName
        assert result.emails == null
        assert result.entitlements == null
        assert result.groups == null
        assert result.ims == null
        assert result.locale == user.locale
        assert result.name == user.name
        assert result.nickName == user.nickName
        assert result.phoneNumbers == null
        assert result.photos == null
        assert result.preferredLanguage == user.preferredLanguage
        assert result.profileUrl == user.profileUrl
        assert result.roles == null
        assert result.timezone == user.timezone
        assert result.title == user.title
        assert result.userType == user.userType
        assert result.x509Certificates == null
        assert result.userName == user.userName
        assert result.id == user.id
        assert result.externalId == user.externalId
        assert result.meta == user.meta
        true
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

    def "should replace an user and set location header"() {
        given:
        def id = UUID.randomUUID().toString()
        when:
        def result = underTest.replace(id, user, httpServletRequest, httpServletResponse)
        then:
        1 * provisioning.replaceUser(id, user) >> user
        1 * httpServletRequest.getRequestURL() >> new StringBuffer("http://localhorst/horst/yo")
        1 * httpServletResponse.setHeader("Location", "http://localhorst/horst/id")
        validateUser(result)
    }

    def "should update an user and set location header"() {
        given:
        def id = UUID.randomUUID().toString()
        when:
        def result = underTest.update(id, user, httpServletRequest, httpServletResponse)
        then:
        1 * provisioning.updateUser(id, user) >> user
        1 * httpServletRequest.getRequestURL() >> new StringBuffer("http://localhorst/horst/yo")
        1 * httpServletResponse.setHeader("Location", "http://localhorst/horst/yo")
        validateUser(result)
    }

}