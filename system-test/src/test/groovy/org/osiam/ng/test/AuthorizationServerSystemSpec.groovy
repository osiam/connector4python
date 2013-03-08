package org.osiam.ng.test


class AuthorizationServerSystemSpec extends AbstractSystemSpec {

    def "OSNG-7: the client should get an authorization code if the user grants access"() {
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
        client.authorizationCode
    }

    def "OSNG-8: the client should get an access token if it sends a valid athorization code"() {
        given:
        String state = "testState"
        String scope = "GET"

        when:
        client.requestAuthorization(scope, state)

        and:
        user.login()

        and:
        user.grantAccess()

        and:
        client.requestAccessToken()

        then:
        client.accessToken
    }

    def "OSNG-9: the client should be able to access the requested resource if it sends a valid access token"() {
        given:
        String state = "testState"
        String scope = "GET"

        when:
        client.requestAuthorization(scope, state)

        and:
        user.login()

        and:
        user.grantAccess()

        and:
        client.requestAccessToken()

        and:
        client.accessToken

        then:
        client.accessResource("")
    }
}
