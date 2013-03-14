package org.osiam.ng.scim.mvc.user

import org.osiam.ng.scim.dao.SCIMUserProvisioning
import scim.schema.v2.User
import spock.lang.Specification

class UserControllerTest extends Specification {

    def underTest = new UserController()
    def provisioning = Mock(SCIMUserProvisioning)

    def setup() {
        underTest.setScimUserProvisioning(provisioning)
    }

    def "should return the user found by provisioning on getUser"() {
        given:
        def user = Mock(User)
        when:
        def result = underTest.getUser("one")
        then:
        1 * provisioning.getById("one") >> user
        result == user
    }

}
