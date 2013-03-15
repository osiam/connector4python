package org.osiam.ng.resourceserver.entities

import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 16:09
 * To change this template use File | Settings | File Templates.
 */
class RolesEntitySpec extends Specification {

    RolesEntity rolesEntity = new RolesEntity()

    def "setter and getter for the Id should be present"() {
        when:
        rolesEntity.setId(123456)

        then:
        rolesEntity.getId() == 123456
    }

    def "setter and getter for the value should be present"(){
        when:
        rolesEntity.setValue("someValue")

        then:
        rolesEntity.getValue() == "someValue"
    }
}