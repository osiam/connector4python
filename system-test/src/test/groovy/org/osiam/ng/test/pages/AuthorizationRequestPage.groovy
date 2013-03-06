package org.osiam.ng.test.pages

import geb.Page

/**
 * @author Michael Kutz, tarent Solutions GmbH, 05.03.2013
 */
class AuthorizationRequestPage extends Page {

    static at = {
        confirmationForm
        denialForm
    }

    static content = {
        confirmationForm { $("form#confirmationForm") }
        conformationButton { confirmationForm.authorize() }
        denialForm { $("form#denialForm") }
        denialButton { denialForm.deny() }
    }
}
