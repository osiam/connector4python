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

class CRUDListControllerTest extends Specification {
    def underTest = new CRUDListController()
    HttpServletRequest request = Mock(HttpServletRequest)
    String accessToken = "accessToken"

    def "should contain createMultiValueAttribute and redirect to create_multivalueattribute"() {
        given:
        def method = CRUDListController.getDeclaredMethod("redirectTo", HttpServletRequest, String, String)
        def annotation = method.getAnnotation(RequestMapping)
        when:
        def result = underTest.redirectTo(request, "not address", accessToken)
        then:
        annotation.value() == ["/createMultiValueAttribute"]
        1 * request.setAttribute("access_token", accessToken)
        1 * request.setAttribute("used_for", "not address")
        result == "create_multivalueattribute"
    }

    def "should not redirect to create_multivalueattribute when used_for is address"() {
        given:
        def method = CRUDListController.getDeclaredMethod("redirectTo", HttpServletRequest, String, String)
        def annotation = method.getAnnotation(RequestMapping)
        when:
        def result = underTest.redirectTo(request, "addresses", accessToken)
        then:
        annotation.value() == ["/createMultiValueAttribute"]
        1 * request.setAttribute("access_token", accessToken)
        1 * request.setAttribute("used_for", "addresses")
        result == "create_address"
    }

    def "should contain addListAttribute and add value KnownValueAttribute if used_for is in enum"() {
        given:
        def method = CRUDListController.getDeclaredMethod("addListAttribute", HttpServletRequest)
        def annotation = method.getAnnotation(RequestMapping)
        def size = CRUDListController.KnownMultiValueAttributeLists.EMAIL.set.values().size()
        when:
         def result = underTest.addListAttribute(request)
        then:
        annotation.value() == ["/addListAttribute"]
        1 * request.getParameter("used_for") >> CRUDListController.KnownMultiValueAttributeLists.EMAIL.name()
        1 * request.setAttribute("access_token", _)
        1 * request.setAttribute("used_for", CRUDListController.KnownMultiValueAttributeLists.EMAIL.name())
        result == "create_update_user"
        CRUDListController.KnownMultiValueAttributeLists.EMAIL.set.values().size() == size +1
    }

    def "should return delete when delete param is true"() {
        when:
        def result = underTest.getDelete(request)
        then:
        1 * request.getParameter("delete") >> "true"
        result == "delete"
    }

    def "should return delete when null param is not true"() {
        when:
        def result = underTest.getDelete(request)
        then:
        1 * request.getParameter("delete") >> ""
        !result
    }


    def "KnownMultiValueAttributeLists should know #name"(){
        expect:
        def attribute = CRUDListController.KnownMultiValueAttributeLists.getFromString(name)
        attribute
        where:
        name << ["phone", "photo", "email", "im", "entitlement", "role", "x509", "group"]
    }


}
