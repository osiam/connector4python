package org.osiam.ng.test

import org.osiam.ng.test.actors.AuthorizationResponse

class AuthorizationServerSystemSpec extends AbstractSystemSpec {

    def "OSNG-7: when the client tries to fetch a resource, OAuth2 flow should kick in"() {
        given:
        String state = "testState"
        String scope = "testScope"

        when:
        AuthorizationResponse response = client.requestAuthorization(scope, state)

        then:
        response.code

        and:
        response.state == state
    }
}
