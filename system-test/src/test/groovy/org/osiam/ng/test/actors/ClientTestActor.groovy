package org.osiam.ng.test.actors

import geb.Browser

import org.osiam.ng.test.clients.rest.RestClient

/**
 * Test actor representing a client in terms of OAuth 2.0 (see
 * <a href="http://tools.ietf.org/html/rfc6749">RFC6749</a>).
 *
 * @author Michael Kutz, tarent Solutions GmbH, 28.02.2013
 */
class ClientTestActor {

    /** The authorization server's base URI. */
    private final String authorizationRequestUri

    /** The {@code client_id} to authenticate the client with the authorization server. */
    private final String clientId

    /** The {@code client_secret} to authenticate the client with the authorization server.. */
    private final String clientSecret

    /** The {@code redirect_uri} used for call backs at the client. */
    private final String redirectUri

    /** The user agent (a Browser). */
    private final Browser userAgent

    /** {@link RestClient\ for communication with the resource server. */
    private final RestClient restClient = new RestClient()

    public ClientTestActor(Browser userAgent, String authorizationServerUri, String clientId, String clientSecret, String redirectUri) {
        this.userAgent = userAgent
        this.authorizationRequestUri = "${authorizationServerUri}/oauth/authorize"
        this.clientId = clientId
        this.clientSecret = clientSecret
        this.redirectUri = redirectUri
    }

    public void requestAuthorization(String scope, String state = null) {
        Map parameters = ["response_type": "code", "client_id": clientId, "redirect_uri": redirectUri, "scope": scope]
        if (state) parameters.put("state", state)
        userAgent.go(parameters, authorizationRequestUri)
    }
}
