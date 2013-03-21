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

package org.osiam.ng.resourceserver.entities

import scim.schema.v2.MultiValuedAttribute
import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 15:07
 * To change this template use File | Settings | File Templates.
 */
class ImsEntitySpec extends Specification {

    ImEntity imsEntity = new ImEntity()
    def userEntity = Mock(UserEntity)

    def "setter and getter for the Id should be present"() {
        when:
        imsEntity.setId(123456)

        then:
        imsEntity.getId() == 123456
    }

    def "setter and getter for the value should be present"() {
        when:
        imsEntity.setValue("someone@googlemail.com")

        then:
        imsEntity.getValue() == "someone@googlemail.com"
    }

    def "setter and getter for the type should be present"() {
        when:
        imsEntity.setType("gTalk")

        then:
        imsEntity.getType() == "gTalk"
    }

    def "setter and getter for the user should be present"() {
        when:
        imsEntity.setUser(userEntity)

        then:
        imsEntity.getUser() == userEntity
    }

    def "mapping to scim should be present"() {
        when:
        def multivalue = imsEntity.toScim()

        then:
        multivalue.value == imsEntity.value
        multivalue.type == imsEntity.type
    }

    def "mapping from scim should be present"() {
        given:
        MultiValuedAttribute multiValuedAttribute = new MultiValuedAttribute.Builder().
                setValue("blaaaa").
                setType("type").
                build()

        when:
        def result = ImEntity.fromScim(multiValuedAttribute)

        then:
        result != null
    }
}