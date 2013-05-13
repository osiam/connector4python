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
import org.osiam.ng.resourceserver.entities.UserEntity
import spock.lang.Specification

import javax.persistence.EntityManager

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 13.05.13
 * Time: 13:37
 * To change this template use File | Settings | File Templates.
 */
class RootDAOSpec extends Specification {

    def filterParser = Mock(FilterParser)
    def hibernateSessionHelper = Mock(HibernateSessionHelper)

    def em = Mock(EntityManager)

    def rootDao = new RootDAO(em: em, filterParser: filterParser, hibernateSessionHelper: hibernateSessionHelper)

    def "should search on root for users and groups"() {
        given:
        //user search
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
        queryContextBuilderMock.forEntity(UserEntity) >> entityContextMock
        entityContextMock.get() >> queryBuilderMock

        hibernateSessionHelper.getHibernateSession(em) >> hibernateSessionMock
        hibernateSessionMock.createCriteria(UserEntity) >> criteriaMock
        filterParser.parse("anyFilter") >> filterChainMock
        filterChainMock.buildQuery(queryBuilderMock, criteriaMock) >> queryMock

        fullTextSessionMock.createFullTextQuery(queryMock, UserEntity) >> fullTextQuery
        fullTextQuery.list() >> ["users"]

        //group search
        def entityContextMock2 = Mock(EntityContext)
        def queryBuilderMock2 = Mock(QueryBuilder)
        def criteriaMock2 = Mock(Criteria)
        def queryMock2 = Mock(org.apache.lucene.search.Query)
        def fullTextQuery2 = Mock(FullTextQuery)

        hibernateSessionHelper.getFullTextSession(em) >> fullTextSessionMock
        fullTextSessionMock.getSearchFactory() >> searchFactoryMock
        searchFactoryMock.buildQueryBuilder() >> queryContextBuilderMock
        queryContextBuilderMock.forEntity(GroupEntity) >> entityContextMock2
        entityContextMock2.get() >> queryBuilderMock2

        hibernateSessionHelper.getHibernateSession(em) >> hibernateSessionMock
        hibernateSessionMock.createCriteria(GroupEntity) >> criteriaMock2
        filterParser.parse("anyFilter") >> filterChainMock
        filterChainMock.buildQuery(queryBuilderMock2, criteriaMock2) >> queryMock2

        fullTextSessionMock.createFullTextQuery(queryMock2, GroupEntity) >> fullTextQuery2
        fullTextQuery2.list() >> ["groups"]

        when:
        def result = rootDao.search("anyFilter")

        then:
        result == ["users", "groups"]
    }
}