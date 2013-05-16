package org.osiam.ng.resourceserver.dao

import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 5/16/13
 * Time: 4:14 PM
 * To change this template use File | Settings | File Templates.
 */
class SCIMSearchResultTest extends Specification {

    def "should contain total result"(){
        when:
          def a = new SCIMSearchResult(null, 2342)
        then:
        !a.result
        a.totalResult == 2342
    }
    def "should contain result"(){
        given:
        def result = []
        when:
        def a = new SCIMSearchResult(result, 2342)
        then:
        a.result == result
        a.totalResult == 2342
    }
}
