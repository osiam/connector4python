package org.osiam.ng.resourceserver.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchException;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.osiam.ng.resourceserver.FilterParser;
import org.osiam.ng.resourceserver.entities.GroupEntity;
import org.osiam.ng.resourceserver.entities.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import scim.schema.v2.Resource;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class RootDAO <T extends Resource> extends GetInternalIdSkeleton{

    @Inject
    private FilterParser filterParser;

    public List<T> search(String filter) {

        List<T> resultList = new ArrayList<>();
        try {
            resultList.addAll(searchUser(filter));
            resultList.addAll(searchGroup(filter));
        } catch (SearchException e) {
            //Catching exception if keyword doesn't match fr one of the resource. For root search it is desired
        }

        return resultList;
    }

    private List<T> searchUser(String filter) {

        FullTextSession fullTextSession = Search.getFullTextSession((Session) em.getDelegate());

        QueryBuilder userQueryBuilder = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity( UserEntity.class ).get();

        Criteria criteria = ((Session) em.getDelegate()).createCriteria(UserEntity.class);
        //TODO: removed hardcoded string and replace with key from parser(queryBuilder)
        criteria.add(Restrictions.isNotNull("displayName"));

        org.apache.lucene.search.Query userQuery = filterParser.parse(filter).buildQuery(userQueryBuilder);

/*        org.apache.lucene.search.Sort sort = new Sort(
                new SortField(sortBy, SortField.STRING, sortOrder));*/

        org.hibernate.search.FullTextQuery fullTextQueryUser = fullTextSession.createFullTextQuery(userQuery, UserEntity.class);
/*        fullTextQuery.setMaxResults(count);
        fullTextQuery.setFirstResult(startIndex);
        fullTextQuery.setSort(sort);*/
        fullTextQueryUser.setCriteriaQuery(criteria);

        return fullTextQueryUser.list();
    }

    private List<T> searchGroup(String filter) {

        FullTextSession fullTextSession = Search.getFullTextSession((Session) em.getDelegate());

        QueryBuilder groupQueryBuilder = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity( GroupEntity.class ).get();

        org.apache.lucene.search.Query groupQuery = filterParser.parse(filter).buildQuery(groupQueryBuilder);

/*        org.apache.lucene.search.Sort sort = new Sort(
                new SortField(sortBy, SortField.STRING, sortOrder));*/

        org.hibernate.search.FullTextQuery fullTextQueryGroup = fullTextSession.createFullTextQuery(groupQuery, GroupEntity.class);
/*        fullTextQuery.setMaxResults(count);
        fullTextQuery.setFirstResult(startIndex);
        fullTextQuery.setSort(sort);*/

        return fullTextQueryGroup.list();
    }
}