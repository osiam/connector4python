package org.osiam.ng.resourceserver.entities

import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
class PhoneNumberEntitySpec extends Specification {

    PhoneNumberEntity phoneNumberEntity = new PhoneNumberEntity()

    def "setter and getter for the Id should be present"() {
        when:
        phoneNumberEntity.setId(123456)

        then:
        phoneNumberEntity.getId() == 123456
    }

    def "setter and getter for the value should be present"() {
        when:
        phoneNumberEntity.setValue("555-555-555")

        then:
        phoneNumberEntity.getValue() == "555-555-555"
    }

    def "setter and getter for the type should be present"() {
        when:
        phoneNumberEntity.setType("work")

        then:
        phoneNumberEntity.getType() == "work"
    }
}