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
import org.hibernate.search.FullTextQuery
import org.hibernate.search.FullTextSession
import org.hibernate.search.SearchFactory
import org.hibernate.search.query.dsl.EntityContext
import org.hibernate.search.query.dsl.QueryBuilder
import org.hibernate.search.query.dsl.QueryContextBuilder
import org.osiam.ng.HibernateSessionHelper
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
    def underTest = new GroupDAO(em: em, filterParser: filterParser, hibernateSessionHelper: hibernateSessionHelper)
    String id = UUID.randomUUID().toString()

    def setup(){
        em.createNamedQuery("getById") >> query
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

    def "should do filtered searches on groups"() {
        given:
        def fullTextSessionMock = Mock(FullTextSession)
        def searchFactoryMock = Mock(SearchFactory)
        def queryContextBuilderMock = Mock(QueryContextBuilder)
        def entityContextMock = Mock(EntityContext)
        def queryBuilderMock = Mock(QueryBuilder)
        def hibernateSessionMock = Mock(Session)
        def filterChainMock = Mock(FilterChain)
        def criteriaMock = Mock(Criteria)
        def queryMock = Mock(org.apache.lucene.search.Query)
        def fullTextQuery = Mock(FullTextQuery)

        hibernateSessionHelper.getFullTextSession(em) >> fullTextSessionMock
        fullTextSessionMock.getSearchFactory() >> searchFactoryMock
        searchFactoryMock.buildQueryBuilder() >> queryContextBuilderMock
        queryContextBuilderMock.forEntity(GroupEntity) >> entityContextMock
        entityContextMock.get() >> queryBuilderMock

        hibernateSessionHelper.getHibernateSession(em) >> hibernateSessionMock
        hibernateSessionMock.createCriteria(GroupEntity) >> criteriaMock
        filterParser.parse("anyFilter") >> filterChainMock
        filterChainMock.buildCriterion(queryBuilderMock, criteriaMock) >> queryMock

        fullTextSessionMock.createFullTextQuery(queryMock, GroupEntity) >> fullTextQuery

        when:
        underTest.search("anyFilter")

        then:
        1 * fullTextQuery.list()
    }

}
