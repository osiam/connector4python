package org.osiam.ng.test.actors

import geb.Browser
import groovy.util.slurpersupport.GPathResult

import org.apache.http.util.EntityUtils
import org.osiam.ng.test.clients.http.HttpResponse
import org.osiam.ng.test.pages.AuthorizationRequestPage
import org.osiam.ng.test.pages.LoginPage

/**
 * @author Michael Kutz, tarent Solutions GmbH, 28.02.2013
 */
class UserTestActor {

    private final String authorizationServerUri
    private final String userName
    private final String password
    private final Browser userAgent

    public UserTestActor(Browser userAgent, String authorizationServerUri, String userName, String password) {
        this.authorizationServerUri = authorizationServerUri
        this.userAgent = userAgent
        this.userName = userName
        this.password = password
    }

    public void login() {
        userAgent.to(LoginPage)
        userAgent.page.userNameInput = userName
        userAgent.page.passwordInput = password
        userAgent.page.submitButton.click()
    }

    public HttpResponse grantAccess() {
        userAgent.at(AuthorizationRequestPage)
        userAgent.page.conformationButton.click()
    }

    public HttpResponse denyAccess() {
        userAgent.at(AuthorizationRequestPage)
        userAgent.page.denialButton.click()
    }
}
