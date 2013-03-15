package org.osiam.ng.resourceserver.entities

import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 14:51
 * To change this template use File | Settings | File Templates.
 */
class EntitlementsEntitySpec extends Specification {

    EntitlementsEntity entitlementsEntity = new EntitlementsEntity()

    def "setter and getter for the Id should be present"() {
        when:
        entitlementsEntity.setId(123456)

        then:
        entitlementsEntity.getId() == 123456
    }

    def "setter and getter for the value should be present"(){
        when:
        entitlementsEntity.setValue("someValue")

        then:
        entitlementsEntity.getValue() == "someValue"
    }
}