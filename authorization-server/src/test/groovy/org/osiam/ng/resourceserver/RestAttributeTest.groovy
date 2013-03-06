package org.osiam.ng.resourceserver

import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 06.03.13
 * Time: 14:24
 * To change this template use File | Settings | File Templates.
 */
class RestAttributeTest extends Specification {
    def "an attribute should contain a key and value"() {
        when:
        def attr = new RestAttribute("key", "val")
        then:
        attr.getKey() == "key"
        attr.value == "val"
    }

}
