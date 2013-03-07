package org.osiam.oauth2.client.service

import spock.lang.Specification

class ClientDetailsLoadingBeanTest extends Specification {
    def underTest = new ClientDetailsLoadingBean()

    def "should return one fake client"(){
        when:
        def result = underTest.loadClientByClientId("client!")
        then:
        result.clientId == "client!"
        result.isScoped() == true
        result.isSecretRequired() == true
        result.getAccessTokenValiditySeconds() == 1337
        result.getRefreshTokenValiditySeconds() == 1337

        result.getScope().contains("GET")
        result.getScope().size() == 1
        result.getResourceIds().size() == 0
        result.getAuthorizedGrantTypes().size() == 3
        result.getRegisteredRedirectUri().size() == 1
        !result.getAuthorities()
        !result.getAdditionalInformation()
        result.getClientSecret() == "secret"


    }
}
