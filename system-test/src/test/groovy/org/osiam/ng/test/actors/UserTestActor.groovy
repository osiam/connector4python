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

    /** This user's user name. */
    private final String userName

    /** This user's password. */
    private final String password

    /** This user's {@link Browser}. */
    private final Browser userAgent

    /**
     * @param userAgent the {@link Browser} of the user.
     * @param userName the user name.
     * @param password the password.
     */
    public UserTestActor(Browser userAgent, String userName, String password) {
        this.userAgent = userAgent
        this.userName = userName
        this.password = password
    }

    /**
     * If the {@link #userAgent} is currently at the {@link LoginPage} this will fill the supplied login form, otherwise
     * it will just return.
     */
    public void login() {
        if (userAgent.isAt(AuthorizationRequestPage)) {
            return
        }

        userAgent.at(LoginPage)
        userAgent.page.userNameInput = userName
        userAgent.page.passwordInput = password
        userAgent.page.submitButton.click()
    }

    /**
     * Grants access for the currently displayed request. If the {@link #userAgent} is not located at the
     * {@link AuthorizationRequestPage} this will fail.
     */
    public void grantAccess() {
        userAgent.at(AuthorizationRequestPage)
        userAgent.page.conformationButton.click()
    }

    /**
     * Denies access for the currently displayed request. If the {@link #userAgent} is not located at the
     * {@link AuthorizationRequestPage} this will fail.
     */
    public void denyAccess() {
        userAgent.at(AuthorizationRequestPage)
        userAgent.page.denialButton.click()
    }
}
