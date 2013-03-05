package org.osiam.oauth2.client

import org.apache.commons.codec.binary.Base64
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.PostMethod
import org.json.JSONException
import spock.lang.Specification

import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AccessTokenServletSpec extends Specification {

    def httpRequest = Mock(HttpServletRequest)
    def httpResponse = Mock(HttpServletResponse)
    def httpClient = Mock(HttpClient)
    def postMethod = Mock(PostMethod)
    def requestDispatcher = Mock(RequestDispatcher)

    def accessTokenServlet = new AccessTokenServlet()

    def jsonString = "{\"scope\":\"ROLE_USER READ\",\"expires_in\":1336,\"token_type\":\"bearer\",\"access_token\":\"a06db533-841f-4047-85f8-1e42b216b65d\"}"
    def combined = "testClient:secret"
    def encoding = new String(Base64.encodeBase64(combined.getBytes()))

    def "should execute request with auth code to obtain access token"() {
        given:
        accessTokenServlet.setHttpClient(httpClient)
        accessTokenServlet.setPost(postMethod)

        httpRequest.getParameter("code") >> "theAuthCode"
        httpRequest.getScheme() >> "http"
        httpRequest.getServerName() >> "localhost"

        postMethod.getResponseBodyAsStream() >> new ByteArrayInputStream(jsonString.getBytes())

        httpRequest.getRequestDispatcher("/parameter.jsp") >> requestDispatcher

        when:
        accessTokenServlet.doGet(httpRequest, httpResponse)

        then:
        1 * postMethod.addParameter('code', 'theAuthCode')
        1 * postMethod.addRequestHeader("Authorization", "Basic " + encoding)
        1 * postMethod.addParameter("grant_type", "authorization_code")
        1 * postMethod.addParameter("redirect_uri", "http://localhost:8080/oauth2-client/accessToken")
        1 * httpClient.executeMethod(postMethod)

        1 * httpRequest.setAttribute("response", _)
        1 * httpRequest.setAttribute("client_id", _)
        1 * httpRequest.setAttribute("client_secret", _)
        1 * httpRequest.setAttribute("redirect_uri", _)
        1 * httpRequest.setAttribute("code", _)

        1 * requestDispatcher.forward(httpRequest, httpResponse)
    }

    def "should not get auth code and access token if user declines authorization"() {
        given:
        accessTokenServlet.setHttpClient(httpClient)
        accessTokenServlet.setPost(postMethod)

        httpRequest.getParameter("code") >> null
        httpRequest.getScheme() >> "http"
        httpRequest.getServerName() >> "localhost"

        postMethod.getResponseBodyAsStream() >> new ByteArrayInputStream(jsonString.getBytes())

        httpRequest.getRequestDispatcher("/parameter.jsp") >> requestDispatcher

        when:
        accessTokenServlet.doGet(httpRequest, httpResponse)

        then:
        0 * postMethod.addParameter("code", null)
        0 * postMethod.addRequestHeader("Authorization", "Basic " + encoding)
        0 * postMethod.addParameter("grant_type", "authorization_code")
        0 * postMethod.addParameter("redirect_uri", "http://localhost:8080/oauth2-client/accessToken")
        0 * httpClient.executeMethod(postMethod)

        0 * httpRequest.setAttribute("response", _)
        1 * httpRequest.setAttribute("client_id", _)
        1 * httpRequest.setAttribute("client_secret", _)
        1 * httpRequest.setAttribute("redirect_uri", _)
        0 * httpRequest.setAttribute("code", _)

        1 * requestDispatcher.forward(httpRequest, httpResponse)
    }



    def "should wrap json exception to IllegalStateException"() {
        given:
        accessTokenServlet.setHttpClient(httpClient)
        accessTokenServlet.setPost(postMethod)

        httpRequest.getParameter("code") >> null
        httpRequest.getScheme() >> "http"
        httpRequest.getServerName() >> "localhost"

        def jsonString = "{\"scope\":\"ROLE_USER READ\",\"expires_in\":1336,\"token_type\":\"bearer\",\"access_token\":\"a06db533-841f-4047-85f8-1e42b216b65d\""

        postMethod.getResponseBodyAsStream() >> new ByteArrayInputStream(jsonString.getBytes())

        httpRequest.getRequestDispatcher("/parameter.jsp") >> requestDispatcher

        when:
        accessTokenServlet.doGet(httpRequest, httpResponse)

        then:
        def e = thrown(IllegalStateException)
        e.message == "Expected a ',' or '}' at character 119"
        e.cause.class == JSONException.class
    }

}
