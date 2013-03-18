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
