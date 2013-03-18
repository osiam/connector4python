/*
 * Copyright (C) 2013 tarent AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
