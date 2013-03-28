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
import scim.schema.v2.User
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserControllerTest extends Specification {

    def underTest = new UserController()
    def provisioning = Mock(SCIMUserProvisioning)
    def httpServletRequest = Mock(HttpServletRequest)
    def httpServletResponse = Mock(HttpServletResponse)
    def user = Mock(User)

    def setup() {
        underTest.setScimUserProvisioning(provisioning)
    }

    def "should return the user found by provisioning on getUser"() {
        when:
        def result = underTest.getUser("one")
        then:
        1 * provisioning.getById("one") >> user
        result == user
    }

    def "should create the user and add the location header"() {
        given:
        user.getId() >> "test"
        httpServletRequest.getRequestURL() >> new StringBuffer("http://host:port/deployment/User/")
        def uri = new URI("http://host:port/deployment/User/test")

        when:
        def result = underTest.createUser(user, httpServletRequest, httpServletResponse)

        then:
        1 * provisioning.createUser(user) >> user
        1 * httpServletResponse.setHeader("Location", uri.toASCIIString())
        result == user
    }

    def "should update an user and set location header"() {
        given:
        def id = UUID.randomUUID().toString()
        user.id >> "schlemmer"
        when:
        def result = underTest.updateUser(id, user, httpServletRequest, httpServletResponse)
        then:
        1 * provisioning.replaceUser(id, user) >> user
        1 * httpServletRequest.getRequestURL() >> new StringBuffer("http://localhorst/horst/yo")
        1 * httpServletResponse.setHeader("Location", "http://localhorst/horst/schlemmer")
        result == user
    }
}