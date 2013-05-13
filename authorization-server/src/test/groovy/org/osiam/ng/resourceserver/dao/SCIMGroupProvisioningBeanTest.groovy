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

import org.osiam.ng.resourceserver.entities.GroupEntity
import org.osiam.ng.scim.exceptions.ResourceExistsException
import org.springframework.dao.DataIntegrityViolationException
import scim.schema.v2.Group
import spock.lang.Specification

class SCIMGroupProvisioningBeanTest extends Specification {

    def groupDao = Mock(GroupDAO)
    def underTest = new SCIMGroupProvisioningBean(groupDAO: groupDao)
    def group = Mock(Group)
    def entity = Mock(GroupEntity)


    def "should return with id enriched group on create"(){
        when:
        def result = underTest.create(group)
        then:
        result != group
        UUID.fromString(result.id)

    }
    def "should call dao create on create"(){
        when:
          underTest.create(group)
        then:
        1 * groupDao.create(_)
    }

    def "should wrap exceptions to org.osiam.ng.scim.exceptions.ResourceExistsException on create"(){
           given:
        groupDao.create(_) >> {
            throw new DataIntegrityViolationException("moep")
        }
        when:
        underTest.create(group)
        then:
        def e = thrown(ResourceExistsException)
        e.message == "null already exists."
    }

    def "should call dao get on get"(){
        when:
        def result = underTest.getById("id")
        then:
        1 * groupDao.getById("id") >> entity
        1 * entity.toScim() >> group
        result == group
    }

    def "should call dao delete on delete"(){
        when:
        underTest.delete("id")

        then:
        1 * groupDao.delete("id")

    }

    def "should call dao search on search"(){
        when:
        underTest.search("anyFilter")

        then:
        1 * groupDao.search("anyFilter")
    }
}
