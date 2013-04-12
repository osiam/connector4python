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

import org.junit.Ignore
import scim.schema.v2.Group
import scim.schema.v2.MultiValuedAttribute
import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 14:05
 * To change this template use File | Settings | File Templates.
 */
class GroupEntitySpec extends Specification {

    GroupEntity groupEntity = new GroupEntity()

    def "setter and getter for the Id should be present"() {
        def id = UUID.randomUUID()
        when:
        groupEntity.setId(id)

        then:
        groupEntity.getId() == id
    }

    def "setter and getter for the display name should be present"() {
        when:
        groupEntity.setDisplayName("Tour Guides")

        then:
        groupEntity.getDisplayName() == "Tour Guides"
    }

    def "setter and getter for the member should be present"() {
        given:
        def memberEntity = Mock(InternalIdSkeleton)
        def members = [memberEntity] as Set<InternalIdSkeleton>


        when:
        groupEntity.setMembers(members)

        then:
        groupEntity.getMembers() == members
    }

    def "setter and getter for any object should be present"() {
        when:
        groupEntity.setAny("Some Object")

        then:
        groupEntity.getAny() == "Some Object"
    }

    def "mapping to scim should be present"() {
        groupEntity.setId(UUID.randomUUID())
        when:
        def multivalue = groupEntity.toScim()

        then:
        multivalue.id == groupEntity.id.toString()
        multivalue.displayName == groupEntity.displayName
    }

    def "mapping from scim should be present"() {
        Group.Members members = new Group.Members()
        members.member.add(new MultiValuedAttribute.Builder().
                setValue(UUID.randomUUID().toString()).
                setDisplay("display").
                build())
        given:
        Group group = new Group.Builder().setDisplayName("displayname").setMembers(members).setId(UUID.randomUUID().toString()).build()
        when:
        def result = GroupEntity.fromScim(group)
        then:
        result != null
    }
}