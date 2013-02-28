package org.osiam.oauth2.mvc

import org.springframework.security.oauth2.provider.AuthorizationRequest
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService
import spock.lang.Specification

class AccessConfirmationControllerTest extends Specification{
    def underTest = new AccessConfirmationController()
    def clientDetailsService = Mock(ClientDetailsService)

    def setup(){
        underTest.setClientDetailsService(clientDetailsService)
    }
    def "should call clientDetailsService and put auth_request and client into model on access confirmation"(){
        given:
        def request = Mock(AuthorizationRequest)
        def client = Mock(ClientDetails)
        request.clientId >> "huch"
        def model = ['authorizationRequest': request]
        when:
        def result = underTest.getAccessConfirmation(model)
        then:
        1 * clientDetailsService.loadClientByClientId("huch") >> client
        result.model.get("auth_request") == request
        result.model.get("client") == client
    }

    def "should set error message on handleError"(){
        def model = ['authorizationRequest': null]
        when:
        def result = underTest.handleError(model)
        then:
        result == "oauth_error"
        model.get("message") == "There was a problem with the OAuth2 protocol"

    }
}
