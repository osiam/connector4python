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

import org.apache.commons.codec.binary.Base64
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.json.JSONException
import spock.lang.Specification

import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest

class AccessTokenServletSpec extends Specification {

    def httpRequest = Mock(HttpServletRequest)
    def httpClient = Mock(HttpClient)
    def requestDispatcher = Mock(RequestDispatcher)



    String jsonString = "{\"scope\":\"ROLE_USER READ\",\"expires_in\":1336,\"token_type\":\"bearer\",\"access_token\":\"a06db533-841f-4047-85f8-1e42b216b65d\"}"


    def response = Mock(HttpResponse)
    def entity = Mock(HttpEntity)
    GenerateClient generateClient = Mock(GenerateClient)

    AccessTokenController accessTokenServlet = new AccessTokenController(generateClient: generateClient)

    def "should execute request with auth code to obtain access token"() {
        given:
        httpRequest.getParameter("code") >> "theAuthCode"
        httpRequest.getScheme() >> "http"
        httpRequest.getServerName() >> "localhost"

        httpRequest.getRequestDispatcher("/parameter.jsp") >> requestDispatcher

        when:
        def result = accessTokenServlet.doGet(httpRequest)

        then:
        1 * generateClient.getClient() >> httpClient
        1 * httpClient.execute(_) >> response
        1 * response.entity >> entity
        1 * entity.content >> new ByteArrayInputStream(jsonString.getBytes())
        1 * httpRequest.setAttribute("response", _)
        1 * httpRequest.setAttribute("client_id", _)
        1 * httpRequest.setAttribute("client_secret", _)
        1 * httpRequest.setAttribute("redirect_uri", _)
        1 * httpRequest.setAttribute("code", _)
        result == "parameter"
    }

    def "should return error when user declined access"() {
        when:
        def result = accessTokenServlet.accessDenied("haha", "unso")

        then:
        result == "error"
    }

    def "should wrap json exception to IllegalStateException"() {
        given:

        httpRequest.getScheme() >> "http"
        httpRequest.getServerName() >> "localhost"
        httpRequest.getParameter("code") >> "theAuthCode"
        String jsonString = "{\"scope\":\"ROLE_USER READ\",\"expires_in\":1336,\"token_type\":\"bearer\",\"access_token\":\"a06db533-841f-4047-85f8-1e42b216b65d\""
        httpRequest.getRequestDispatcher("/parameter.jsp") >> requestDispatcher

        when:
        accessTokenServlet.doGet(httpRequest)

        then:
        1 * generateClient.getClient() >> httpClient
        1 * httpClient.execute(_) >> response
        1 * response.entity >> entity
        1 * entity.content >> new ByteArrayInputStream(jsonString.getBytes())
        IllegalStateException e = thrown()
        e.message == "Expected a ',' or '}' at character 119"
        e.cause.class == JSONException.class
    }
}