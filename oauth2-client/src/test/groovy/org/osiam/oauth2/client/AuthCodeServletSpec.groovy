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

import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

class AuthCodeServletSpec extends Specification {

    def httpRequest = Mock(HttpServletRequest)
    def authCodeServlet = new AuthCodeController()

    def "should create and send request to obtain auth code"() {
        given:
        httpRequest.getScheme() >> "http"
        httpRequest.getServerName() >> "localhost"

        when:
        def result = authCodeServlet.redirectTogetAuthCode(httpRequest);

        then:
        result == "redirect:http://localhost:8080/authorization-server/oauth/authorize?response_type=code&scope=GET%20POST%20PUT%20PATCH&state=haha&client_id=testClient&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Foauth2-client%2FaccessToken"
    }
}