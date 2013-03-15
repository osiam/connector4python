package org.osiam.ng.resourceserver.entities

import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
class MemberEntitySpec extends Specification {

    MemberEntity memberEntity = new MemberEntity()

    def "setter and getter for the Id should be present"() {
        when:
        memberEntity.setId(123456)

        then:
        memberEntity.getId() == 123456
    }

    def "setter and getter for the value should be present"() {
        given:
        def value = UUID.randomUUID()

        when:
        memberEntity.setValue(value)

        then:
        memberEntity.getValue() == value
    }

    def "setter and getter for display should be present"() {
        when:
        memberEntity.setDisplay("John Do")

        then:
        memberEntity.getDisplay() == "John Do"
    }
}