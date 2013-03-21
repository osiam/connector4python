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

    def "OSNG-10: the client should be able to access the requested user if it sends a valid access token and the user exists"() {
        given:
        valid_access_token("GET", UUID.randomUUID().toString())
        when:
        def result = client.accessResource("marissa")
        then:
        result.externalId == "marissa"

    }

    def "OSNG-10: the client should be able to access the requested user if it sends a valid access token, but resource not found exception occur if user not exists"() {
        given:
        def identifier = "JohnDo"
        valid_access_token("GET", UUID.randomUUID().toString())
        when:
        client.accessResource(identifier)
        then:
        thrown(JsonException)
    }

}