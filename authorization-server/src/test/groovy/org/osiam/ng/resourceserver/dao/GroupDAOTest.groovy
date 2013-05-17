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

import org.hibernate.Criteria
import org.hibernate.Session
import org.hibernate.criterion.Criterion

import org.osiam.ng.resourceserver.FilterChain
import org.osiam.ng.resourceserver.FilterParser
import org.osiam.ng.resourceserver.entities.GroupEntity
import org.osiam.ng.resourceserver.entities.InternalIdSkeleton
import org.osiam.ng.resourceserver.entities.UserEntity
import org.osiam.ng.scim.exceptions.ResourceNotFoundException
import spock.lang.Specification

import javax.persistence.EntityManager
import javax.persistence.Query

class GroupDAOTest extends Specification {

    EntityManager em = Mock(EntityManager)

    Query query = Mock(Query)
    InternalIdSkeleton internalidSkeleton = new GroupEntity(id: UUID.randomUUID())

    def filterParser = Mock(FilterParser)
    def hibernateSessionHelper = Mock(HibernateSessionHelper)
    def underTest = new GroupDAO()
    String id = UUID.randomUUID().toString()

    def setup(){
        em.createNamedQuery("getById") >> query
        underTest.setHibernateSessionHelper(hibernateSessionHelper)
        underTest.setFilterParser(filterParser)
        underTest.setEm(em)
    }

    def "should abort when a given member does not exists"() {
        given:
        def entity = new GroupEntity(members: [internalidSkeleton] as Set)
        when:
        underTest.create(entity)
        then:
        1 * query.getResultList() >> []
        thrown(ResourceNotFoundException)
    }

    def "should persist on empty members "() {
        given:
        def entity = new GroupEntity()
        when:
        underTest.create(entity)
        then:
        1 * em.persist(entity)
    }

    def "should persist with known members "() {
        given:
        def entity = new GroupEntity(members: [internalidSkeleton] as Set)
        when:
        underTest.create(entity)
        then:
        1 * query.getResultList() >> [internalidSkeleton]
        1 * em.persist(entity)
        entity.members.size() == 1
    }

    def "should get a group"(){
        when:
        def result = underTest.getById(id)
        then:
        1 * query.getResultList() >> [internalidSkeleton]
        result == internalidSkeleton

    }

    def "should wrap class cast exception to ResourceNotFoundException"(){
        given:
        internalidSkeleton = new UserEntity()
        when:
        underTest.getById(id)
        then:
        1 * query.getResultList() >> [internalidSkeleton]
        thrown(ResourceNotFoundException)
    }

    def "should first try to get and then delete a group"(){
        when:
        underTest.delete(id)
        then:
        1 * query.getResultList() >> [internalidSkeleton]
        1 * em.remove(internalidSkeleton)
    }

    def "should not delete an unknown group"(){
        when:
        underTest.delete(id)
        then:
        1 * query.getResultList() >> []
        0 * em.remove(internalidSkeleton)
        thrown(ResourceNotFoundException)
    }

    def "should do filtered searches on groups sorting ascending"() {
        given:
        def hibernateSessionMock = Mock(Session)
        def filterChainMock = Mock(FilterChain)
        def criteriaMock = Mock(Criteria)
        def criterionMock = Mock(Criterion)
        def groupList = ["group"] as List

        hibernateSessionHelper.getHibernateSession(em) >> hibernateSessionMock
        hibernateSessionMock.createCriteria(GroupEntity.class) >> criteriaMock
        filterParser.parse("anyFilter") >> filterChainMock
        filterChainMock.buildCriterion() >> criterionMock
        criteriaMock.add(criterionMock) >> criteriaMock
        criteriaMock.setProjection(_) >> criteriaMock
        criteriaMock.uniqueResult() >> 1000.toLong()
        criteriaMock.setResultTransformer(_) >> criteriaMock
        criteriaMock.list() >> groupList

        when:
        def result = underTest.search("anyFilter", "displayName", "ascending", 100, 1)

        then:
        result != null
        result.getTotalResult() == 1000
        result.getResult() == groupList

    }

}
