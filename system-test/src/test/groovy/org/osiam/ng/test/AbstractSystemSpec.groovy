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

    def clearnup() {
    }
}
