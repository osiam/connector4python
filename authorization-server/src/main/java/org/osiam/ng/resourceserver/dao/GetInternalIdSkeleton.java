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

package org.osiam.ng.resourceserver.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.osiam.ng.HibernateSessionHelper;
import org.osiam.ng.resourceserver.FilterParser;
import org.osiam.ng.resourceserver.entities.InternalIdSkeleton;
import org.osiam.ng.scim.exceptions.ResourceNotFoundException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public abstract class GetInternalIdSkeleton {
    protected final static Logger LOGGER = Logger.getLogger(GetInternalIdSkeleton.class.getName());
    @PersistenceContext
    protected EntityManager em;

    @Inject
    protected FilterParser filterParser;

    private HibernateSessionHelper hibernateSessionHelper = new HibernateSessionHelper();


    protected <T extends InternalIdSkeleton> T getInternalIdSkeleton(String id) {
        Query query = em.createNamedQuery("getById");
        query.setParameter("id", UUID.fromString(id));
        return getSingleInternalIdSkeleton(query, id);
    }

    @SuppressWarnings("unchecked")
    protected <T extends InternalIdSkeleton> T getSingleInternalIdSkeleton(Query query, String identifier) {
        List result = query.getResultList();
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Resource " + identifier + " not found.");
        }
        return (T) result.get(0);

    }

    protected <T> SCIMSearchResult<T> search(Class<T> clazz, String filter, int count, int startIndex, String sortBy, String sortOrder) {
        Criteria criteria = hibernateSessionHelper.getHibernateSession(em).createCriteria(clazz);
        if (filter != null && !filter.isEmpty()) {
            criteria = criteria.add(filterParser.parse(filter).buildCriterion());
        }
        createAliasesForCriteria(criteria);
        criteria.setMaxResults(count);
        criteria.setFirstResult(startIndex);
        long totalResult = (long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        if (sortOrder.equalsIgnoreCase("descending"))
            criteria.addOrder(Order.desc(sortBy));
        else
            criteria.addOrder(Order.asc(sortBy));
        List list = criteria.setProjection(null).setResultTransformer(Criteria.ROOT_ENTITY).list();
        return new SCIMSearchResult(list, totalResult);
    }

    protected abstract void createAliasesForCriteria(Criteria criteria);


}
