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
        result == "redirect:http://localhost:8080/authorization-server/oauth/authorize?response_type=code&scope=GET&state=haha&client_id=testClient&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Foauth2-client%2FaccessToken"
    }
}
