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

import groovy.json.JsonException

class SCIMUserSystemSpec extends AbstractSystemSpec {

    String state = "testState"
    String scope = "GET POST"

    def "OSNG-10: the client should be able to access the requested user if it sends a valid access token and the user exists"() {
        given:
        valid_access_token(scope, state)
        when:
        def result = client.accessResource("marissa")
        then:
        result.externalId == "marissa"

    }

    def "OSNG-10: a resource not found exception should occur if the requested user does not exist"() {
        given:
        valid_access_token(scope, state)
        when:
        client.accessResource("JohnDo")
        then:
        thrown(JsonException)
    }

    def "OSNG-11: the client should be able to create a new user if it sends a valid access token"() {
        given:
        valid_access_token(scope, state)
        def jsonString = "{\"userName\":\"bam\",\"password\":\"1234\",\"externalId\":\"bam\"}"

        when:
        def result = client.createResource(jsonString)

        then:
        result.externalId == "bam"
    }
}