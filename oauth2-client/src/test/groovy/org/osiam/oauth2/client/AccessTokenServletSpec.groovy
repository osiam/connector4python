package org.osiam.oauth2.client

import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AccessTokenServletSpec extends Specification {

    def httpRequest = Mock(HttpServletRequest)
    def httpResponse = Mock(HttpServletResponse)
    def accessTokenServlet = new AccessTokenServlet()

    def "should execute request with auth code to obtain access token"() {

    }

    def "should not get auth code and access token if user declines authorization"() {

    }
}
