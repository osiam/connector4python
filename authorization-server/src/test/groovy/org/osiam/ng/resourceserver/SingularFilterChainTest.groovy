/*
 * Copyright 2013
 *     tarent AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package org.osiam.ng.resourceserver


import org.hibernate.Criteria
import org.hibernate.criterion.Criterion
import spock.lang.Specification

class SingularFilterChainTest extends Specification{


    def "should parse equals (eq)"(){
        when:
        def result = new SingularFilterChain("userName eq \"bjensen\"")
        then:
        result.key == 'userName'
        result.constraint == SingularFilterChain.Constraints.EQUALS
        result.value == "\"bjensen\""
    }

    def "should parse without \""(){
        when:
        def result = new SingularFilterChain("userName eq 1")
        then:
        result.key == 'userName'
        result.constraint == SingularFilterChain.Constraints.EQUALS
        result.value == 1
    }

    def "should parse contains (co)"(){
        when:
        def result = new SingularFilterChain("name.familyName co \"O'Malley\"")
        then:
        result.key == 'name.familyName'
        result.constraint == SingularFilterChain.Constraints.CONTAINS
        result.value == "\"O'Malley\""
    }

    def "should parse starts with (sw)"(){
        when:
        def result = new SingularFilterChain("userName sw \"L\"")
        then:
        result.key == 'userName'
        result.constraint == SingularFilterChain.Constraints.STARTS_WITH
        result.value == "\"L\""

    }

    def "should build query for (sw) constraint"() {
        given:
        def singularFilterChain = new SingularFilterChain("userName sw \"L\"")
        def queryBuilder = Mock(QueryBuilder)
        def criteria = Mock(Criteria)
        def termContext = Mock(TermContext)
        def wildcardContext = Mock(WildcardContext)
        def termMatchingContext = Mock(TermMatchingContext)
        def termTermination = Mock(TermTermination)

        queryBuilder.keyword() >> termContext
        termContext.wildcard() >> wildcardContext
        wildcardContext.onField("userName") >> termMatchingContext
        termMatchingContext.matching("\"L\"" + "*") >> termTermination

        when:
        singularFilterChain.buildQuery(queryBuilder, criteria)

        then:
        1 * termTermination.createQuery() >> Mock(Query)
    }

    def "should parse present (pr)"(){
        when:
        def result = new SingularFilterChain("title pr")
        then:
        result.key == 'title'
        result.constraint == SingularFilterChain.Constraints.PRESENT
        !result.value

    }

    def "should parse greater than (gt)"(){
        //
        when:
        def result = new SingularFilterChain("meta.lastModified gt \"2011-05-13T04:42:34Z\"")
        then:
        result.key == 'meta.lastModified'
        result.constraint == SingularFilterChain.Constraints.GREATER_THAN
        result.value == "\"2011-05-13T04:42:34Z\""

    }

    def "should parse greater than or equal (ge)"(){
        when:
        def result = new SingularFilterChain("meta.lastModified ge \"2011-05-13T04:42:34Z\"")
        then:
        result.key == 'meta.lastModified'
        result.constraint == SingularFilterChain.Constraints.GREATER_EQUALS
        result.value == "\"2011-05-13T04:42:34Z\""

    }

    def "should parse less than (lt)"(){
        when:
        def result = new SingularFilterChain("meta.lastModified lt \"2011-05-13T04:42:34Z\"")
        then:
        result.key == 'meta.lastModified'
        result.constraint == SingularFilterChain.Constraints.LESS_THAN
        result.value == "\"2011-05-13T04:42:34Z\""

    }

    def "should parse less than or equal (le)"(){
        when:
        def result = new SingularFilterChain("meta.lastModified le \"2011-05-13T04:42:34Z\"")
        then:
        result.key == 'meta.lastModified'
        result.constraint == SingularFilterChain.Constraints.LESS_EQUALS
        result.value == "\"2011-05-13T04:42:34Z\""

    }


    def "should parse a empty or null chain and choose (empty) constraint"(){
        when:
        def result = new SingularFilterChain("")
        then:
        result.key == null
        result.constraint == SingularFilterChain.Constraints.EMPTY
        result.value == null
    }

    def "should throw exception if no constraint matches"(){
        when:
        new SingularFilterChain("userName xx \"bjensen\"")

        then:
        def exception = thrown(IllegalArgumentException)
        exception.getMessage() == "userName xx \"bjensen\"" + " is not a SingularFilterChain."
    }
}