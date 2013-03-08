package org.osiam.oauth2.client

import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

class GetResourceServiceTest extends Specification {
    def underTest = new GetResourceService()
    def httpRequest = Mock(HttpServletRequest)

    def "should redirect to resource server with given access_token"() {
        given:
        httpRequest.getScheme() >> "http"
        httpRequest.getServerName() >> "localhorst";
        when:
        def e = underTest.redirectToResourceServer(httpRequest, "abc")
        then:
        e == "redirect:http://localhorst:8080/authorization-server/secured/attributes?access_token=abc"
    }
}
