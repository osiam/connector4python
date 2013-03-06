package org.osiam.ng.test

import org.osiam.ng.test.actors.AuthorizationResponse
import org.osiam.ng.test.clients.http.HttpResponse

class AuthorizationServerSystemSpec extends AbstractSystemSpec {

    def "OSNG-7: when the client tries to fetch a resource, OAuth2 flow should kick in"() {
        given:
        String state = "testState"
        String scope = "GET"

        when:
        client.requestAuthorization(scope, state)

        and:
        user.login()

        and:
        user.grantAccess()

        then:
        true
    }
}
