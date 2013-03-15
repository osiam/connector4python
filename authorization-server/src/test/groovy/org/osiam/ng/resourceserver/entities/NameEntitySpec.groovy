package org.osiam.ng.resourceserver.entities

import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 15:48
 * To change this template use File | Settings | File Templates.
 */
class NameEntitySpec extends Specification {

    NameEntity nameEntity = new NameEntity()

    def "setter and getter for the Id should be present"() {
        when:
        nameEntity.setId(123456)

        then:
        nameEntity.getId() == 123456
    }

    def "setter and getter for formatted should be present"() {
        when:
        nameEntity.setFormatted("Ms. Barbara J Jensen III")

        then:
        nameEntity.getFormatted() == "Ms. Barbara J Jensen III"
    }

    def "setter and getter for the family name should be present"() {
        when:
        nameEntity.setFamilyName("Jensen")

        then:
        nameEntity.getFamilyName() == "Jensen"
    }

    def "setter and getter for the given name should be present"() {
        when:
        nameEntity.setGivenName("Barbara")

        then:
        nameEntity.getGivenName() == "Barbara"
    }

    def "setter and getter for the middle name should be present"() {
        when:
        nameEntity.setMiddleName("Jane")

        then:
        nameEntity.getMiddleName() == "Jane"
    }

    def "setter and getter for the honorific prefix should be present"() {
        when:
        nameEntity.setHonorificPrefix("Ms.")

        then:
        nameEntity.getHonorificPrefix() == "Ms."
    }

    def "setter and getter for the honorific suffix should be present"() {
        when:
        nameEntity.setHonorificSuffix("III")

        then:
        nameEntity.getHonorificSuffix() == "III"
    }
}