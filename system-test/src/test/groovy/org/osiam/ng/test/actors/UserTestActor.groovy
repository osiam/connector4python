package org.osiam.ng.test.actors

import groovy.transform.Immutable

import org.osiam.ng.test.clients.http.HttpClient

/**
 * @author Michael Kutz, tarent Solutions GmbH, 28.02.2013
 */
class UserTestActor {

    private final String userName
    private final String password

    private final HttpClient httpClient = new HttpClient()

    public UserTestActor(String userName, String password) {
        this.userName = userName
        this.password = password
    }

    public AuthorizationResponse grantAuthorization(AuthorizationResponse authorizationResponse) {
        httpClient.post(userAuthenticationUri, [userName: userName, password: password])
    }
}
