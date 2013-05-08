package org.osiam.ng.resourceserver.dao;

import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
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

    List<T> search(String filter){
        FullTextSession fullTextSession = Search.getFullTextSession((Session) em.getDelegate());

        QueryBuilder userQueryBuilder = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity( UserEntity.class ).get();

        FullTextSession fullTextSession2 = Search.getFullTextSession((Session) em.getDelegate());

        QueryBuilder groupQueryBuilder = fullTextSession2.getSearchFactory()
                .buildQueryBuilder().forEntity( GroupEntity.class ).get();

        org.apache.lucene.search.Query userQuery = filterParser.parse(filter).buildQuery(userQueryBuilder);
        org.apache.lucene.search.Query groupQuery = filterParser.parse(filter).buildQuery(groupQueryBuilder);

        Query[] queryList = new Query[10];
        queryList[0] = groupQuery;

        userQuery.combine(queryList);

/*        org.apache.lucene.search.Sort sort = new Sort(
                new SortField(sortBy, SortField.STRING, sortOrder));*/

        org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(userQuery, UserEntity.class, GroupEntity.class);
/*        fullTextQuery.setMaxResults(count);
        fullTextQuery.setFirstResult(startIndex);
        fullTextQuery.setSort(sort);*/

        return fullTextQuery.list();
    }
}