package org.osiam.ng.resourceserver.entities

import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
class AddressEntitySpec extends Specification {

    AddressEntity addressEntity = new AddressEntity()

    def "setter and getter for the Id should be present"() {
        when:
        addressEntity.setId(123456)

        then:
        addressEntity.getId() == 123456
    }

    def "setter and getter for the formatted address should be present"() {
        when:
        addressEntity.setFormatted("funky street 1337 Honkytown")

        then:
        addressEntity.getFormatted() == "funky street 1337 Honkytown"
    }

    def "setter and getter for the street address should be present"() {
        when:
        addressEntity.setStreetAddress("funky street")

        then:
        addressEntity.getStreetAddress() == "funky street"
    }

    def "setter and getter for the locality should be present"() {
        when:
        addressEntity.setLocality("locality")

        then:
        addressEntity.getLocality() == "locality"
    }

    def "setter and getter for the region should be present"() {
        when:
        addressEntity.setRegion("region")

        then:
        addressEntity.getRegion() == "region"
    }

    def "setter and getter for the postal code should be present"() {
        when:
        addressEntity.setPostalCode(123456)

        then:
        addressEntity.getPostalCode() == 123456
    }

    def "setter and getter for the country should be present"() {
        when:
        addressEntity.setCountry("Germany")

        then:
        addressEntity.getCountry() == "Germany"
    }

    def "setter and getter for the type should be present"() {
        when:
        addressEntity.setType("home")

        then:
        addressEntity.getType() == "home"
    }

    def "setter and getter for primary should be present"() {
        when:
        addressEntity.setPrimary(true)

        then:
        addressEntity.isPrimary() == true
    }

}