package org.osiam.oauth2.client

import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthCodeServletSpec extends Specification {

    def httpRequest = Mock(HttpServletRequest)
    def httpResponse = Mock(HttpServletResponse)
    def authCodeServlet = new AuthCodeServlet()

    def "should create and send request to obtain auth code"() {
        given:
        httpRequest.getScheme() >> "http"
        httpRequest.getServerName() >> "localhost"

        when:
        authCodeServlet.doGet(httpRequest, httpResponse);

        then:
        1* httpResponse.sendRedirect("http://localhost:8080/authorization-server/oauth/authorize?response_type=code&client_id=testClient&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Foauth2-client%2FaccessToken")
    }
}
