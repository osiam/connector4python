package org.osiam.ng.test.actors

import org.osiam.ng.test.clients.http.HttpClient
import org.osiam.ng.test.clients.rest.RestClient

/**
 * Test actor representing a client in terms of OAuth 2.0 (see
 * <a href="http://tools.ietf.org/html/rfc6749">RFC6749</a>).
 *
 * @author Michael Kutz, tarent Solutions GmbH, 28.02.2013
 */
class ClientTestActor {

    /** The authorization server's base URI. */
    private String authorizationRequestUri = "http://localhost:8080/authorization-server/oauth/authorize"

    /** The {@code client_id} to authenticate the client with the authorization server. */
    private String clientId

    /** The {@code client_secret} to authenticate the client with the authorization server.. */
    private String clientSecret

    /** The {@code redirect_uri} used for call backs at the client. */
    private String redirectUri

    /** {@link HttpClient} for direct communication with the authorization server. */
    private HttpClient httpClient = new HttpClient()

    /** {@link RestClient\ for communication with the resource server. */
    private RestClient restClient = new RestClient()

    /**
     * Requests authorization form the authorization server using the {@link #httpClient}.
     * 
     * @param scope scope to authorize for.
     * @param state optional state to transmit with the request.
     * @return the {@link AuthorizationResponse}.
     */
    public AuthorizationResponse requestAuthorization(String scope, String state = null) {
        return new AuthorizationResponse(httpClient.get(
        "${authorizationRequestUri}?response_type=code&client_id=${clientId}&redirect_uri=${redirectUri}"
        + (state ?: "state=${state}")))
    }
}
