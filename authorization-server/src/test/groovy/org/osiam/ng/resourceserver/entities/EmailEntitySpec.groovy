package org.osiam.ng.resourceserver.entities

import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 13:56
 * To change this template use File | Settings | File Templates.
 */
class EmailEntitySpec extends Specification {

    EmailEntity emailEntity = new EmailEntity()

    def "setter and getter for the Id should be present"() {
        when:
        emailEntity.setId(123456)

        then:
        emailEntity.getId() == 123456
    }

    def "setter and getter for the email should be present"() {
        when:
        emailEntity.setValue("work@high.tech")

        then:
        emailEntity.getValue() == "work@high.tech"
    }

    def "setter and getter for the type should be present"() {
        when:
        emailEntity.setType("home")

        then:
        emailEntity.getType() == "home"
    }

    def "setter and getter for primary should be present"() {
        when:
        emailEntity.setPrimary(true)

        then:
        emailEntity.isPrimary() == true
    }
}