package org.osiam.ng.resourceserver.entities

import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 15:07
 * To change this template use File | Settings | File Templates.
 */
class ImsEntitySpec extends Specification {

    ImEntity imsEntity = new ImEntity()

    def "setter and getter for the Id should be present"() {
        when:
        imsEntity.setId(123456)

        then:
        imsEntity.getId() == 123456
    }

    def "setter and getter for the value should be present"() {
        when:
        imsEntity.setValue("someone@googlemail.com")

        then:
        imsEntity.getValue() == "someone@googlemail.com"
    }

    def "setter and getter for the type should be present"() {
        when:
        imsEntity.setType("gTalk")

        then:
        imsEntity.getType() == "gTalk"
    }
}
