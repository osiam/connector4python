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

import org.apache.http.Header
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.StatusLine
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase
import org.apache.http.message.BasicHeader
import org.codehaus.jackson.map.ObjectMapper
import scim.schema.v2.User
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

class GetResponseAndCastTest extends Specification {
    GenerateClient generateClient = Mock(GenerateClient)
    def underTest = new GetResponseAndCast(generateClient: generateClient)
    HttpServletRequest request = Mock(HttpServletRequest)
    def access_token = UUID.randomUUID().toString()
    def user = new User.Builder("username").setId(UUID.randomUUID().toString()).build()
    def inputParam = new ObjectMapper().writeValueAsString(user)
    def httpMethod = Mock(HttpEntityEnclosingRequestBase)
    def client = Mock(HttpClient)
    HttpResponse response = Mock(HttpResponse)
    HttpEntity entity = Mock(HttpEntity)
    InputStream userStream = new ByteArrayInputStream(inputParam.bytes)
    InputStream errorStream = new ByteArrayInputStream("somehow an error occured ...".bytes)

    def setup() {

        generateClient.getClient() >> client
        client.execute(_) >> response
        response.getEntity() >> entity
        entity.getContent() >> userStream
        response.getStatusLine() >> Mock(StatusLine)
    }


    def "should accept json, set content and call the http method"() {
        when:
        underTest.getResponseAndSetAccessToken(request, access_token, inputParam, httpMethod)
        then:
        1 * httpMethod.addHeader("accept", "application/json")
        1 * httpMethod.setEntity(_)
        1 * client.execute(httpMethod) >> response
    }

    def "should set location and response as attribute"() {
        given:
        def header = new BasicHeader("location", "location")
        when:
        underTest.getResponseAndSetAccessToken(request, access_token, inputParam, httpMethod)
        then:
        1 * request.setAttribute("userResponse", inputParam)
        1 * response.getFirstHeader("Location") >> header
        1 * request.setAttribute("LocationHeader", header)

    }


    def "should add user id when response is scim.schema.v2.User as attribute"() {
        given:
        def idSize = CRUDRedirectController.userIds.size()

        when:
        underTest.getResponseAndSetAccessToken(request, access_token, inputParam, httpMethod)
        then:
        CRUDRedirectController.userIds.size() == idSize + 1
    }

    def "should not set user id when response is not a scim.schema.v2.User type"() {
        given:
        def idSize = CRUDRedirectController.userIds.size()
        when:
        underTest.getResponseAndSetAccessToken(request, access_token, inputParam, httpMethod)
        then:
        1 * entity.getContent() >> errorStream
        CRUDRedirectController.userIds.size() == idSize

    }

}
