package org.osiam.ng.resourceserver.dao;

import org.hibernate.Criteria;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.SearchException;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.osiam.ng.HibernateSessionHelper;
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

    private HibernateSessionHelper hibernateSessionHelper = new HibernateSessionHelper();

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

        FullTextSession fullTextSession = hibernateSessionHelper.getFullTextSession(em);

        QueryBuilder userQueryBuilder = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(UserEntity.class).get();

        Criteria criteria = hibernateSessionHelper.getHibernateSession(em).createCriteria(UserEntity.class);

        org.apache.lucene.search.Query userQuery = filterParser.parse(filter).buildQuery(userQueryBuilder, criteria);

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

        FullTextSession fullTextSession = hibernateSessionHelper.getFullTextSession(em);

        QueryBuilder groupQueryBuilder = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(GroupEntity.class).get();

        Criteria criteria = hibernateSessionHelper.getHibernateSession(em).createCriteria(GroupEntity.class);

        org.apache.lucene.search.Query groupQuery = filterParser.parse(filter).buildQuery(groupQueryBuilder, criteria);

/*        org.apache.lucene.search.Sort sort = new Sort(
                new SortField(sortBy, SortField.STRING, sortOrder));*/

        org.hibernate.search.FullTextQuery fullTextQueryGroup = fullTextSession.createFullTextQuery(groupQuery, GroupEntity.class);
/*        fullTextQuery.setMaxResults(count);
        fullTextQuery.setFirstResult(startIndex);
        fullTextQuery.setSort(sort);*/

        return fullTextQueryGroup.list();
    }
}