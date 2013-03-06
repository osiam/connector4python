package org.osiam.oauth2.mvc

import org.springframework.security.oauth2.provider.AuthorizationRequest
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService
import spock.lang.Ignore
import spock.lang.Specification

class AccessConfirmationControllerSpec extends Specification{

    AccessConfirmationController underTest = new AccessConfirmationController()
    ClientDetailsService clientDetailsServiceMock = Mock()

    def setup(){
        underTest.setClientDetailsService(clientDetailsServiceMock)
    }

    def "should call clientDetailsService and put auth_request and client into model on access confirmation"(){
        given:
        AuthorizationRequest request = Mock()
        ClientDetails client = Mock()
        request.clientId >> "huch"
        def model = ['authorizationRequest': request]

        when:
        def result = underTest.getAccessConfirmation(model)

        then:
        1 * clientDetailsServiceMock.loadClientByClientId("huch") >> client
        result.model.get("auth_request") == request
        result.model.get("client") == client
    }

    def "should set error message on handleError"(){
        given:
        def model = ['authorizationRequest': null]

        when:
        String result = underTest.handleError(model)

        then:
        result == "oauth_error"
        model.get("message") == "There was a problem with the OAuth2 protocol"
    }
}
