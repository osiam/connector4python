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

import geb.spock.GebReportingSpec
import geb.spock.GebSpec

import org.osiam.ng.test.actors.ClientTestActor
import org.osiam.ng.test.actors.UserTestActor

/**
 * Abstract super specification for all system tests.
 *
 * @author Michael Kutz, tarent Solutions GmbH, 28.02.2013
 */
abstract class AbstractSystemSpec extends GebReportingSpec {

    protected ClientTestActor client
    protected UserTestActor user

    private Properties systemTestProperties = new Properties()

    def setup() {
        systemTestProperties.load(ClassLoader.getSystemResourceAsStream("systemTestConfig.properties"))
        String authorizationServerUri = systemTestProperties.get("authorizationServer.uri")
        String resourceServerUri = systemTestProperties.get("resourceServer.uri")

        client = new ClientTestActor(browser, authorizationServerUri, resourceServerUri, "testClient", "secret", "http://localhost:8080/oauth2-client/accessToken")
        user = new UserTestActor(browser, "marissa", "koala")
    }

    def valid_access_token(def scope, def state){
        client.requestAuthorization(scope, state)
        user.login()
        user.grantAccess()
        client.requestAccessToken()
    }


    def clearnup() {
    }
}
