package org.osiam.ng.test.actors

import geb.Browser
import groovy.util.slurpersupport.GPathResult

import java.io.InputStreamReader
import java.util.regex.Matcher

import org.apache.commons.codec.binary.Base64
import org.json.JSONObject
import org.json.JSONTokener
import org.osiam.ng.test.clients.http.HttpClient
import org.osiam.ng.test.clients.http.HttpResponse
import org.osiam.ng.test.clients.rest.RestClient

/**
 * Test actor representing a client in terms of OAuth 2.0 (see
 * <a href="http://tools.ietf.org/html/rfc6749">RFC6749</a>).
 *
 * @author Michael Kutz, tarent Solutions GmbH, 28.02.2013
 */
class ClientTestActor {

    /** The authorization server's URI for authorization requests. */
    private final String authorizationRequestUri

    /** The authorization server's URI for access token requests. */
    private final String accessTokenRequestUri

    /** The {@code client_id} to authenticate the client with the authorization server. */
    private final String clientId

    /** The {@code client_secret} to authenticate the client with the authorization server. */
    private final String clientSecret

    /** Base64 encoded combination of {@link #clientId} and {@link #clientSecret} for basic HTTP authorization. */
    private final String basicAuthorizationString

    /** The {@code redirect_uri} used for call backs at the client. */
    private final String redirectUri

    /** The user agent (a Browser). Needed to redirect it. */
    private final Browser userAgent

    /** {@link HttpClient} needed for direct interaction with the authorization server. */
    private HttpClient http = new HttpClient()

    /** The last received authorization code. */
    private String authorizationCode = null

    /** The last received access token. */
    private String accessToken = null

    /** {@link RestClient\ for communication with the resource server. */
    private final RestClient restClient = new RestClient()

    /**
     * @param userAgent the {@link Browser} of the user.
     * @param authorizationServerUri the base URI of the authorization server.
     * @param clientId this client's ID.
     * @param clientSecret the client's secret.
     * @param redirectUri this client redirect URI.
     */
    public ClientTestActor(Browser userAgent, String authorizationServerUri, String clientId, String clientSecret, String redirectUri) {
        this.userAgent = userAgent
        this.authorizationRequestUri = "${authorizationServerUri}/oauth/authorize"
        this.accessTokenRequestUri = "${authorizationServerUri}/oauth/token"
        this.clientId = clientId
        this.clientSecret = clientSecret
        this.redirectUri = redirectUri
        this.basicAuthorizationString = new String(Base64.encodeBase64("${clientId}:${clientSecret}".bytes))
    }

    /**
     * Requests an authorization code for the given {@code scope} from the authorization server through the
     * {@link #userAgent}.
     * 
     * @param scope the requested scope.
     * @param state a state variable for the client.
     */
    public void requestAuthorization(String scope, String state = null) {
        Map parameters = ["response_type": "code", "client_id": clientId, "redirect_uri": redirectUri, "scope": scope]
        if (state) parameters.put("state", state)
        userAgent.go(parameters, authorizationRequestUri)
    }

    /**
     * Requests an access token from the authorization server using the {@link #authorizationCode}.
     */
    public void requestAccessToken() {
        Map parameters = ["grant_type": "authorization_code", "code": getAuthorizationCode(), "redirect_uri": redirectUri]
        Map headers = ["Authorization": "Basic ${basicAuthorizationString}"]

        HttpResponse accessTokenResponse = http.post(accessTokenRequestUri, parameters, headers)

        accessToken = accessTokenResponse.jsonBody["access_token"]
    }

    /**
     * @return the {@link #authorizationCode}.
     */
    public String getAuthorizationCode() {
        if (!authorizationCode) {
            Matcher matcher = userAgent.driver.currentUrl =~ ".*code=([^&]+).*"
            authorizationCode = matcher[0][1]
        }
        return authorizationCode
    }

    /**
     * @return the {@link #accessToken}.
     */
    public String getAccessToken() {
        return accessToken
    }
}
