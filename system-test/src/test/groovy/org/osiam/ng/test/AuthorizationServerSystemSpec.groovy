package org.osiam.ng.test


class AuthorizationServerSystemSpec extends AbstractSystemSpec {

    String state = "testState"
    String scope = "GET"

    def "OSNG-7: the client should get an authorization code if the user grants access"() {
        when:
        client.requestAuthorization(scope, state)

        and:
        user.login()

        and:
        user.grantAccess()

        then:
        client.authorizationCode
        client.state == state
    }

    def "OSNG-7: the client should not get an authorization code if the user denies access"() {
        when:
        client.requestAuthorization(scope, state)

        and:
        user.login()

        and:
        user.denyAccess()

        then:
        !client.authorizationCode
    }

    def "OSNG-8: the client should get an access token if it sends a valid athorization code"() {
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
        client.state == state
    }

    def "OSNG-8: the client should get no access token if it sends an invalid athorization code"() {
        when:
        client.requestAuthorization(scope, state)

        and:
        user.login()

        and:
        user.grantAccess()

        and:
        client.requestAccessToken("invalid")

        then:
        !client.accessToken
    }

    def "OSNG-9: the client should be able to access the requested resource if it sends a valid access token"() {
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


    def "OSNG-9: the client should not be able to access the requested resource if it sends an invalid access token"() {
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
        client.accessResource("", "invalid")
    }
}
