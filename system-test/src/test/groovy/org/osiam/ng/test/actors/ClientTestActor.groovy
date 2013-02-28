package org.osiam.ng.test.actors

import org.osiam.ng.test.clients.http.HttpClient

/**
 * Test actor representing a client in terms of OAuth 2.0 (see
 * <a href="http://tools.ietf.org/html/rfc6749">RFC6749</a>).
 *
 * @author Michael Kutz, tarent Solutions GmbH, 28.02.2013
 */
class ClientTestActor {

    /** The {@code client_id}. */
    private String clientId

    /** The {@code client_secret}. */
    private String clientSecret

    private String redirectUri

    private HttpClient httpClient
}
