package org.osiam.ng.resourceserver

import spock.lang.Specification

class CombinedFilterChainTest extends Specification {
    def "should be able to parse title pr and userType eq Employee"() {
        given:
        def filter = 'title pr and userType eq "Employee"'
        when:
        def cf = new CombinedFilterChain(filter)

        then:
        cf.term1 instanceof SingularFilterChain
        cf.combinedWith == CombinedFilterChain.Combiner.AND
        cf.term2 instanceof SingularFilterChain
    }

    def "should be able to parse title pr or userType eq Intern"() {
        given:
        def filter = 'title pr or userType eq "Intern"'
        when:
        def cf = new CombinedFilterChain(filter)

        then:
        cf.term1 instanceof SingularFilterChain
        cf.term1.key == 'title'
        cf.term1.constraint == SingularFilterChain.Constraints.PRESENT
        cf.term1.value == ""
        cf.combinedWith == CombinedFilterChain.Combiner.OR
        cf.term2 instanceof SingularFilterChain
        cf.term2.key == 'userType'
        cf.term2.constraint == SingularFilterChain.Constraints.EQUALS
        cf.term2.value == 'Intern'

    }

    def "should be able to parse userType eq Employee and (emails co example.com or emails co example.org)"() {
        given:
        def filter = 'userType eq "Employee" and (emails co "example.com" or emails co "example.org")'
        when:
        def cf = new CombinedFilterChain(filter)
        then:
        cf.term1 instanceof SingularFilterChain
        cf.term1.key == 'userType'
        cf.term1.constraint == SingularFilterChain.Constraints.EQUALS
        cf.term1.value == 'Employee'
        cf.combinedWith == CombinedFilterChain.Combiner.AND
        cf.term2 instanceof CombinedFilterChain
        cf.term2.combinedWith == CombinedFilterChain.Combiner.OR
        cf.term2.term1 instanceof SingularFilterChain
        cf.term2.term1.key == 'emails'
        cf.term2.term1.constraint == SingularFilterChain.Constraints.CONTAINS
        cf.term2.term1.value == 'example.com'
        cf.term2.term2 instanceof SingularFilterChain
        cf.term2.term2.key == 'emails'
        cf.term2.term2.constraint == SingularFilterChain.Constraints.CONTAINS
        cf.term2.term2.value == 'example.org'


    }
}
