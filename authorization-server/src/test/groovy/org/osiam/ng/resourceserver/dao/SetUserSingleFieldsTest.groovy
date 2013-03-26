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

package org.osiam.ng.resourceserver.dao

import org.osiam.ng.resourceserver.entities.UserEntity
import scim.schema.v2.Meta
import scim.schema.v2.Name
import scim.schema.v2.User
import spock.lang.Specification

class SetUserSingleFieldsTest extends Specification {

    def "should update a single field of an entity with the given value"() {
        given:
        def scimUser = new User.Builder("test")
                .setDisplayName("display")
                .setName(new Name.Builder().setFamilyName("Prefect").build())
                .build()

        def entity = new UserEntity()
        def underTest = new SetUserSingleFields(entity)
        def aha = new SetUserFields(null, null).getFieldsAsNormalizedMap(UserEntity)

        when:
        underTest.updateSingleField(scimUser, aha.get("displayname"), scimUser.displayName, "displayname")
        underTest.updateSingleField(scimUser, aha.get("name"), scimUser.name, "name")
        then:
        scimUser.displayName == entity.displayName
        scimUser.name.familyName == entity.name.familyName

    }

    def "should update a given password of an entity"() {
        given:
        def scimUser = new User.Builder("test")
                .setPassword("hallo")
                .build()

        def entity = new UserEntity()
        entity.setPassword("nicht hallo")
        def underTest = new SetUserSingleFields(entity)
        def aha = new SetUserFields(null, null).getFieldsAsNormalizedMap(UserEntity)
        when:
        underTest.updateSingleField(scimUser, aha.get("password"), scimUser.password, "password")
        then:
        scimUser.password == entity.password

    }

    def "should not update a empty password of an entity"() {
        given:
        def scimUser = new User.Builder("test")
                .setPassword("")
                .build()

        def entity = new UserEntity()
        entity.setPassword("nicht hallo")
        def underTest = new SetUserSingleFields(entity)
        def aha = new SetUserFields(null, null).getFieldsAsNormalizedMap(UserEntity)
        when:
        underTest.updateSingleField(scimUser, aha.get("password"), scimUser.password, "password")
        then:
        entity.password == "nicht hallo"

    }
}
