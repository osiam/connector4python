/*
 * Copyright (C) 2013 tarent AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.osiam.ng.scim.interceptor

import org.aspectj.lang.JoinPoint
import org.osiam.ng.scim.exceptions.SchemaUnknownException
import scim.schema.v2.Constants
import scim.schema.v2.User
import spock.lang.Specification

class CheckSchemaTest extends Specification {
    def underTest = new CheckSchema()
    def joint = Mock(JoinPoint)


    def "should do nothing when no args are delivered"() {
        when:
        underTest.checkUser(joint)
        then:
        notThrown(SchemaUnknownException)

    }

    def "should throw an org.osiam.ng.scim.exceptions.SchemaUnknownException when User schema is empty"() {
        given:
        def schema = [] as Set
        User user = new User.Builder("test").setSchemas(schema).build()
        joint.args >> [user]
        when:
        underTest.checkUser(joint)
        then:
        thrown(SchemaUnknownException)
    }

    def "should throw an org.osiam.ng.scim.exceptions.SchemaUnknownException when User schema is null"() {
        given:
        User user = new User.Builder("test").setSchemas(null).build()
        joint.args >> [user]
        when:
        underTest.checkUser(joint)
        then:
        thrown(SchemaUnknownException)
    }

    def "should throw an org.osiam.ng.scim.exceptions.SchemaUnknownException when User schema is unknown"() {
        given:
        def schema = [] as Set
        User user = new User.Builder("test").setSchemas(schema).build()
        joint.args >> [user]
        when:
        underTest.checkUser(joint)
        then:
        thrown(SchemaUnknownException)

    }

    def "should do nothing when schema is correct"() {
        given:
        def schema = Constants.CORE_SCHEMAS
        User user = new User.Builder("test").setSchemas(schema).build()
        joint.args >> [user]
        when:
        underTest.checkUser(joint)
        then:
        notThrown(SchemaUnknownException)
    }
}
