package org.osiam.oauth2.mvc.resource

import spock.lang.Specification

class PseudoAttributeControllerTest extends Specification {
    def underTest = new PseudoAttributeController()

    def "should return ten generated attributes"() {
        when:
        def attributes = underTest.getAttributes()
        then:
        attributes.size() == 10
    }

    def "should return one attribute"() {
        when:
        def attribute = underTest.getAttribute(23)
        then:
        attribute.key == "23"
        attribute.value == "val23"
    }

}
