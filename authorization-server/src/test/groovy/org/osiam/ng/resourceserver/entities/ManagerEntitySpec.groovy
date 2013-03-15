package org.osiam.ng.resourceserver.entities

import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 16:21
 * To change this template use File | Settings | File Templates.
 */
class ManagerEntitySpec extends Specification {

    ManagerEntity managerEntity = new ManagerEntity()

    def "setter and getter for the Id should be present"() {
        when:
        managerEntity.setId(123456)

        then:
        managerEntity.getId() == 123456
    }

    def "setter and getter for the managerId should be present"() {
        given:
        def value = UUID.randomUUID()

        when:
        managerEntity.setManagerId(value)

        then:
        managerEntity.getManagerId() == value
    }

    def "setter and getter for the display name should be present"() {
        when:
        managerEntity.setDisplayName("John Smith")

        then:
        managerEntity.getDisplayName() == "John Smith"
    }
}