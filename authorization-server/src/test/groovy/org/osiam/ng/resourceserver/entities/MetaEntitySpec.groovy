package org.osiam.ng.resourceserver.entities

import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
class MetaEntitySpec extends Specification {

    MetaEntity metaEntity = new MetaEntity()
    def gregorianCalendar = Mock(GregorianCalendar)

    def "setter and getter for the Id should be present"() {
        when:
        metaEntity.setId(123456)

        then:
        metaEntity.getId() == 123456
    }

    def "setter and getter for the created field should be present"() {
        when:
        metaEntity.setCreated(gregorianCalendar)

        then:
        metaEntity.getCreated() == gregorianCalendar
    }

    def "setter and getter for the modified field should be present"() {
        when:
        metaEntity.setLastModified(gregorianCalendar)

        then:
        metaEntity.getLastModified() == gregorianCalendar
    }

    def "setter and getter for the location should be present"() {
        when:
        metaEntity.setLocation("https://example.com/Users/2819c223-7f76-453a-919d-413861904646")

        then:
        metaEntity.getLocation() == "https://example.com/Users/2819c223-7f76-453a-919d-413861904646"
    }

    def "setter and getter for the version should be present"() {
        when:
        metaEntity.setVersion("3694e05e9dff591")

        then:
        metaEntity.getVersion() == "3694e05e9dff591"
    }
}