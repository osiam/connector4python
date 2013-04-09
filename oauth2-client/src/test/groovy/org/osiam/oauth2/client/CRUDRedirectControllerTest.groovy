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

package org.osiam.oauth2.client

import org.springframework.web.bind.annotation.RequestMapping
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

class CRUDRedirectControllerTest extends Specification {
    def underTest = new CRUDRedirectController()
    def request = Mock(HttpServletRequest)

    def "should contain /crud"(){
        when:
        def mapping = CRUDRedirectController.class.getAnnotation(RequestMapping)

        then:
        mapping.value() == ["/crud"]

    }

    def "should contain /user/get for getting an user"() {
        given:
        //@RequestMapping()
        def mapping = CRUDRedirectController.class.getDeclaredMethod("redirectToGetUser", HttpServletRequest)
                .getAnnotation(RequestMapping)
        when:
        def result = underTest.redirectToGetUser(request)

        then:
        result == "get_user"
        mapping.value() == ["/user/get"]
        1 * request.getParameter("access_token") >> "abc"
        1 * request.setAttribute("access_token", "abc")
    }

    def "should contain /crud/user/put for adding as well as updating user"() {
        given:
        def mapping = CRUDRedirectController.class.getDeclaredMethod("redirectToCreateUpdateUser", HttpServletRequest)
                .getAnnotation(RequestMapping)
        when:
        def result = underTest.redirectToCreateUpdateUser(request)

        then:
        result == "create_update_user"
        mapping.value() == ["/user/put"]
        1 * request.getParameter("access_token") >> "abc"
        1 * request.setAttribute("access_token", "abc")
    }

    def "should contain /crud/user/patch patching an user"() {
        given:
        def mapping = CRUDRedirectController.class.getDeclaredMethod("redirectToPatchUser", HttpServletRequest)
                .getAnnotation(RequestMapping)
        when:
        def result = underTest.redirectToPatchUser(request)

        then:
        result == "patch_user"
        mapping.value() == ["/user/patch"]
        1 * request.getParameter("access_token") >> "abc"
        1 * request.setAttribute("access_token", "abc")
    }

}
