package org.osiam.ng.test.pages

import geb.Page

/**
 * @author Michael Kutz, tarent Solutions GmbH, 05.03.2013
 */
class LoginPage extends Page {

    static url = "http://localhost:8080/authorization-server/login.jsp"

    static at = { loginForm }

    static content = {
        loginForm { $("form#loginForm") }
        userNameInput { loginForm.j_username() }
        passwordInput { loginForm.j_password() }
        submitButton  { loginForm.login() }
    }
}
