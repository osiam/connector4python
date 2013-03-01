package org.osiam.ng.hash

import spock.lang.Specification

class CalculateHashTest extends Specification {
    def "should hash a given string"() {
        given:
        def input = "input"
        when:
        def result = CalculateHash.calculate(input)

        then:
        input != result
        result.size() == 88
    }

    def "a generated hash should equal if input equals"() {
        given:
        def input = "input"
        when:
        def result = CalculateHash.calculate(input)
        def result2 = CalculateHash.calculate(input)

        then:
        result == result2

    }

    def "should generate a salted hash"() {
        given:
        def input = "input"
        when:
        def result = CalculateHash.calculate(input)
        def salted = CalculateHash.calculateWithSalt(CalculateHash.calculateSalt(), input)

        then:
        result != salted

    }

    def "a generated salted hash should not equal if input equals but salt does not"() {
        given:
        def input = "input"
        when:
        def salted = CalculateHash.calculateWithSalt(CalculateHash.calculateSalt(), input)
        def salted2 = CalculateHash.calculateWithSalt(CalculateHash.calculateSalt(), input)

        then:
        salted != salted2

    }

    def "a generated salted hash should equal if input and salt are equal"() {
        given:
        def input = "input"
        def salt = CalculateHash.calculateSalt()
        when:
        def salted = CalculateHash.calculateWithSalt(salt, input)
        def salted2 = CalculateHash.calculateWithSalt(salt, input)

        then:
        salted == salted2
    }

    def "should give a hint in exception if hashing goes wrong"() {
        when:
        CalculateHash.calculate(null)
        then:
        def e = thrown(IllegalArgumentException)
        e.message == "An error occured while trying to calculate the hash value of null"
    }
}
