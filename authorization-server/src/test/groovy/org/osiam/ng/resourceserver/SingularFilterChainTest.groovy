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

<<<<<<< HEAD
import org.apache.lucene.search.Query
import org.hibernate.Criteria
import org.hibernate.criterion.Criterion
import org.hibernate.criterion.NotNullExpression
import org.hibernate.criterion.SimpleExpression
import org.hibernate.search.query.dsl.*
=======
>>>>>>> 81a14accbde563ba27fe5ad72e1581b75666d206
import spock.lang.Specification

class SingularFilterChainTest extends Specification{

    def "should parse datetime correctly"(){
        when:
        def result = new SingularFilterChain("meta.created eq 2011-05-13 04:42:34")
        then:
<<<<<<< HEAD
        result.value instanceof Date

=======
        result.key == 'userName'
        result.constraint == SingularFilterChain.Constraints.EQUALS
        result.value == "\"bjensen\""
    }

    def "should build query for (eq) constraint"() {
        given:
        def singularFilterChain = new SingularFilterChain("userName eq \"bjensen\"")

        when:
        def result = singularFilterChain.buildCriterion()

        then:
        result != null
>>>>>>> 81a14accbde563ba27fe5ad72e1581b75666d206
    }

    def "should parse long correctly"(){
        when:
        def result = new SingularFilterChain("internal_id eq 1")
        then:
<<<<<<< HEAD
        result.value instanceof Long
=======
        result.key == 'userName'
        result.constraint == SingularFilterChain.Constraints.EQUALS
        result.value == 1
    }
>>>>>>> 81a14accbde563ba27fe5ad72e1581b75666d206

    }



    def "should build query for (eq) constraint"() {
        given:
<<<<<<< HEAD
        def singularFilterChain = new SingularFilterChain("userName eq \"bjensen\"")
=======
        def singularFilterChain = new SingularFilterChain("name.familyName co \"O'Malley\"")
>>>>>>> 81a14accbde563ba27fe5ad72e1581b75666d206

        when:
        def result = singularFilterChain.buildCriterion()

        then:
<<<<<<< HEAD
        result.op == "="
        result.value == "bjensen"
        result.propertyName == "userName"
        result instanceof SimpleExpression
=======
        result != null
>>>>>>> 81a14accbde563ba27fe5ad72e1581b75666d206
    }

    def "should parse contains (co)"(){
        when:
        def result = new SingularFilterChain("name.familyName co \"O'Malley\"").buildCriterion()
        then:
        result.propertyName == 'name.familyName'
        result.op == " like "
        result.value == "%O'Malley%"
        result instanceof SimpleExpression
    }

<<<<<<< HEAD
    def "should parse starts with (sw)"(){
        when:
        def result = new SingularFilterChain("userName sw \"L\"").buildCriterion()
        then:
        result.propertyName == 'userName'
        result.op == " like "
        result.value == "L%"
        result instanceof SimpleExpression
=======
    def "should build query for (sw) constraint"() {
        given:
        def singularFilterChain = new SingularFilterChain("userName sw \"L\"")

        when:
        def result = singularFilterChain.buildCriterion()

        then:
        result != null
>>>>>>> 81a14accbde563ba27fe5ad72e1581b75666d206
    }


    def "should parse present (pr)"(){
        when:
        def result = new SingularFilterChain("title pr").buildCriterion()
        then:
        result.propertyName == 'title'
        result instanceof NotNullExpression

    }

<<<<<<< HEAD
=======
    def "should build query for (pr) constraint"() {
        given:
        def singularFilterChain = new SingularFilterChain("title pr")

        when:
        def result = singularFilterChain.buildCriterion()

        then:
        result != null
    }

>>>>>>> 81a14accbde563ba27fe5ad72e1581b75666d206
    def "should parse greater than (gt)"(){
        when:
        def result = new SingularFilterChain("meta.lastModified gt \"2011-05-13 04:42:34\"")
        then:
        result.key == 'meta.lastModified'
        result.constraint == SingularFilterChain.Constraints.GREATER_THAN
<<<<<<< HEAD
        result.value instanceof Date


=======
        result.value == "\"2011-05-13T04:42:34Z\""

    }

    def "should build query for (gt) constraint"() {
        given:
        def singularFilterChain = new SingularFilterChain("meta.lastModified gt \"2011-05-13T04:42:34Z\"")

        when:
        def result = singularFilterChain.buildCriterion()

        then:
        result != null
>>>>>>> 81a14accbde563ba27fe5ad72e1581b75666d206
    }


    def "should parse greater than or equal (ge)"(){
        when:
        def result = new SingularFilterChain("meta.lastModified ge \"2011-05-13 04:42:34 \"")
        then:
        result.key == 'meta.lastModified'
        result.constraint == SingularFilterChain.Constraints.GREATER_EQUALS
        result.value instanceof Date

<<<<<<< HEAD

=======
    }

    def "should build query for (ge) constraint"() {
        given:
        def singularFilterChain = new SingularFilterChain("meta.lastModified ge \"2011-05-13T04:42:34Z\"")

        when:
        def result = singularFilterChain.buildCriterion()

        then:
        result != null
>>>>>>> 81a14accbde563ba27fe5ad72e1581b75666d206
    }


    def "should parse less than (lt)"(){
        when:
        def result = new SingularFilterChain("meta.lastModified lt \"2011-05-13 04:42:34\"")
        then:
        result.key == 'meta.lastModified'
        result.constraint == SingularFilterChain.Constraints.LESS_THAN
        result.value instanceof Date


<<<<<<< HEAD
=======
    def "should build query for (lt) constraint"() {
        given:
        def singularFilterChain = new SingularFilterChain("meta.lastModified lt \"2011-05-13T04:42:34Z\"")

        when:
        def result = singularFilterChain.buildCriterion()

        then:
        result != null
>>>>>>> 81a14accbde563ba27fe5ad72e1581b75666d206
    }


    def "should parse less than or equal (le)"(){
        when:
        def result = new SingularFilterChain("meta.lastModified le \"2011-05-13 04:42:34\"")
        then:
        result.key == 'meta.lastModified'
        result.constraint == SingularFilterChain.Constraints.LESS_EQUALS
<<<<<<< HEAD
        result.value instanceof Date
=======
        result.value == "\"2011-05-13T04:42:34Z\""

    }

    def "should build query for (le) constraint"() {
        given:
        def singularFilterChain = new SingularFilterChain("meta.lastModified le \"2011-05-13T04:42:34Z\"")

        when:
        def result = singularFilterChain.buildCriterion()

        then:
        result != null
>>>>>>> 81a14accbde563ba27fe5ad72e1581b75666d206
    }

    def "should throw exception if no constraint matches"(){
        when:
        new SingularFilterChain("userName xx \"bjensen\"")

        then:
        def exception = thrown(IllegalArgumentException)
        exception.getMessage() == "userName xx \"bjensen\"" + " is not a SingularFilterChain."
    }
}