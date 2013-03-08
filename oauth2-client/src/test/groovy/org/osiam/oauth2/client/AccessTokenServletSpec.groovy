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
    def httpClient = Mock(HttpClient)
    def requestDispatcher = Mock(RequestDispatcher)

    def accessTokenServlet = new AccessTokenServlet(httpClient: httpClient)

    def jsonString = "{\"scope\":\"ROLE_USER READ\",\"expires_in\":1336,\"token_type\":\"bearer\",\"access_token\":\"a06db533-841f-4047-85f8-1e42b216b65d\"}"
    def combined = "testClient:secret"
    def encoding = new String(Base64.encodeBase64(combined.getBytes()))

    def setup(){

    }

    def "should execute request with auth code to obtain access token"() {
        given:
        httpRequest.getParameter("code") >> "theAuthCode"
        httpRequest.getScheme() >> "http"
        httpRequest.getServerName() >> "localhost"

        httpRequest.getRequestDispatcher("/parameter.jsp") >> requestDispatcher

        when:
        def result = accessTokenServlet.doGet(httpRequest)

        then:
        1 * httpClient.executeMethod({PostMethod post ->
            post.getParameter('code').value == 'theAuthCode'
            post.getParameter('grant_type').value == 'authorization_code'
            post.getParameter('redirect_uri').value == 'http://localhost:8080/oauth2-client/accessToken'
            post.addRequestHeader("Authorization", "Basic " + encoding)
            post.responseStream = new ByteArrayInputStream(jsonString.getBytes())
        })

        1 * httpRequest.setAttribute("response", _)
        1 * httpRequest.setAttribute("client_id", _)
        1 * httpRequest.setAttribute("client_secret", _)
        1 * httpRequest.setAttribute("redirect_uri", _)
        1 * httpRequest.setAttribute("code", _)
        result == "parameter"
    }

    def "should return error when user declined access"() {
        when:
        def result = accessTokenServlet.access_denied("haha", "unso")

        then:
        result == "error"
    }



    def "should wrap json exception to IllegalStateException"() {
        given:

        httpRequest.getScheme() >> "http"
        httpRequest.getServerName() >> "localhost"
        httpRequest.getParameter("code") >> "theAuthCode"
        def jsonString = "{\"scope\":\"ROLE_USER READ\",\"expires_in\":1336,\"token_type\":\"bearer\",\"access_token\":\"a06db533-841f-4047-85f8-1e42b216b65d\""
        httpRequest.getRequestDispatcher("/parameter.jsp") >> requestDispatcher

        when:
        accessTokenServlet.doGet(httpRequest)

        then:
        1 * httpClient.executeMethod({PostMethod post ->
            post.getParameter('code').value == 'theAuthCode'
            post.getParameter('grant_type').value == 'authorization_code'
            post.getParameter('redirect_uri').value == 'http://localhost:8080/oauth2-client/accessToken'
            post.addRequestHeader("Authorization", "Basic " + encoding)
            post.responseStream = new ByteArrayInputStream(jsonString.getBytes())
        })
        def e = thrown(IllegalStateException)
        e.message == "Expected a ',' or '}' at character 119"
        e.cause.class == JSONException.class
    }

}
