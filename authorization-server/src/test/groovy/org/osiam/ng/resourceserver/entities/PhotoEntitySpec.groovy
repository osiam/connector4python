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
 * Time: 16:04
 * To change this template use File | Settings | File Templates.
 */
class PhotoEntitySpec extends Specification {

    PhotoEntity photoEntity = new PhotoEntity()
    def userEntity = Mock(UserEntity)


    def "setter and getter for the value should be present"() {
        when:
        photoEntity.setValue("https://photos.example.com/profilephoto/72930000000Ccne/T")

        then:
        photoEntity.getValue() == "https://photos.example.com/profilephoto/72930000000Ccne/T"
    }

    def "setter and getter for the type should be present"() {
        when:
        photoEntity.setType("thumbnail")

        then:
        photoEntity.getType() == "thumbnail"
    }

    def "setter and getter for the user should be present"() {
        when:
        photoEntity.setUser(userEntity)

        then:
        photoEntity.getUser() == userEntity
    }

    def "mapping to scim should be present"() {
        when:
        def multivalue = photoEntity.toScim()

        then:
        multivalue.value == photoEntity.value
        multivalue.type == photoEntity.type
    }

    def "mapping from scim should be present"() {
        given:
        MultiValuedAttribute multiValuedAttribute = new MultiValuedAttribute.Builder().
                setValue("blaaaa").
                setType("type").
                build()

        when:
        def result = PhotoEntity.fromScim(multiValuedAttribute)

        then:
        result != null
    }
}