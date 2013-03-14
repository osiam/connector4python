package org.osiam.ng.scim.mvc.user

import org.osiam.ng.scim.exceptions.ResourceNotFoundException
import spock.lang.Specification

class UserControllerTest extends Specification {
    def underTest = new UserController()

    def "should throw an ResourceNotFoundException when no user got found with a specific id"() {
        when:
        underTest.getUser(UUID.randomUUID().toString())
        then:
        def e = thrown(ResourceNotFoundException)
        e.getMessage() == "No user with id found."
    }

    def "should return user found by id"() {}

}
