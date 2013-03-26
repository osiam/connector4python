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

package org.osiam.ng.test.actors

import geb.Browser
import org.codehaus.jackson.map.ObjectMapper
import scim.schema.v2.User

import java.util.regex.Matcher

import org.apache.commons.codec.binary.Base64
import org.apache.http.util.EntityUtils
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

    /** The resource server's root resource URI. */
    private final String explicitUserResourceUri

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

    /** The last received state. */
    private String state = null

    /** The last received access token. */
    private String accessToken = null

    /** {@link RestClient\ for communication with the resource server.  */
    private final RestClient restClient = new RestClient()

    /**
     * @param userAgent the {@link Browser} of the user.
     * @param authorizationServerUri the base URI of the authorization server.
     * @param clientId this client's ID.
     * @param clientSecret the client's secret.
     * @param redirectUri this client redirect URI.
     */
    public ClientTestActor(Browser userAgent, String authorizationServerUri, String resourceServerUri, String clientId, String clientSecret, String redirectUri) {
        this.userAgent = userAgent
        this.authorizationRequestUri = "${authorizationServerUri}/oauth/authorize"
        this.accessTokenRequestUri = "${authorizationServerUri}/oauth/token"
        this.explicitUserResourceUri = "${resourceServerUri}/User"

        this.clientId = clientId
        this.clientSecret = clientSecret
        this.redirectUri = redirectUri
        this.basicAuthorizationString = new String(Base64.encodeBase64("${clientId}:${clientSecret}".bytes))
    }

    /**
     * Requests an authorization code for the given {@code scope} from the authorization server through the
     * {@link #userAgent}.
     *
     * This resets the {@link #authorizationCode} and {@link #accessToken} to null.
     *
     * @param scope the requested scope.
     * @param state a state variable for the client.
     */
    public void requestAuthorization(String scope, String state = null) {
        authorizationCode = null
        accessToken = null

        Map parameters = ["response_type": "code", "client_id": clientId, "redirect_uri": redirectUri, "scope": scope]
        if (state) parameters.put("state", state)
        userAgent.go(parameters, authorizationRequestUri)
    }

    /**
     * Requests an access token from the authorization server using the {@link #authorizationCode}.
     */
    public void requestAccessToken(differendAthorizationCode = null) {
        Map parameters = [
                "grant_type": "authorization_code",
                "code": differendAthorizationCode ? differendAthorizationCode : getAuthorizationCode(),
                "redirect_uri": redirectUri]
        Map headers = [
                "Authorization": "Basic ${basicAuthorizationString}"]

        HttpResponse accessTokenResponse = http.post(accessTokenRequestUri, parameters, headers)

        accessToken = accessTokenResponse.jsonBody["access_token"]

        EntityUtils.consume(accessTokenResponse.response.entity)
    }

    /**
     * Tries to access the specified resource using the current {@link #accessToken}.
     *
     * @param resourcePath the resource's path relative to the {@link #explicitUserResourceUri}.
     */
    public accessResource(String resourcePath = "", String accessToken) {
        HttpResponse resourceRespose = http.get("${explicitUserResourceUri}/${resourcePath}?access_token=${accessToken}")
        def resource = resourceRespose.jsonBody
        EntityUtils.consume(resourceRespose.response.entity)
        return resource
    }

    public postUserResource(User user, String resourcePath = "", String accessToken = accessToken) {


        ObjectMapper mapper = new ObjectMapper();
        def json = mapper.writeValueAsString(user)

        HttpResponse resourceRespose = http.post("${explicitUserResourceUri}/${resourcePath}?access_token=${accessToken}",
                ['user': json]
        )

        def resource = resourceRespose.jsonBody
        EntityUtils.consume(resourceRespose.response.entity)
        return resource
    }

    /**
     * Tries to create the specified resource using the current {@link #accessToken}.
     * @param the JSON representation of the user
     */
    public createResource(String jsonString) {
        def url = "${explicitUserResourceUri}/?access_token=${accessToken}"
        HttpResponse resourceResponse = http.post(url, [:], [:], jsonString)
        def resource = resourceResponse.jsonBody
        EntityUtils.consume(resourceResponse.response.entity)
        return resource
    }

    /**
     * @return the {@link #authorizationCode}.
     */
    public String getAuthorizationCode() {
        if (!authorizationCode) {
            Matcher matcher = userAgent.driver.currentUrl =~ ".*code=([^&]+).*"
            if (matcher.matches()) {
                authorizationCode = matcher.group(1) ? matcher.group(1) : null
            }
        }
        return authorizationCode
    }

    /**
     * @return the state returned by the authorization server at the last {@link #requestAccessToken()} operation.
     */
    public String getState() {
        if (!state) {
            Matcher matcher = userAgent.driver.currentUrl =~ ".*state=([^&]+).*"
            if (matcher.matches()) {
                state = matcher.group(1) ? matcher.group(1) : null
            }
        }
        return state
    }

    /**
     * @return the {@link #accessToken}.
     */
    public String getAccessToken() {
        return accessToken
    }
}
