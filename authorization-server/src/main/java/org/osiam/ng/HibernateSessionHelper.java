package org.osiam.ng;

import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.impl.FullTextSessionImpl;

import javax.persistence.EntityManager;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 13.05.13
 * Time: 10:30
 * To change this template use File | Settings | File Templates.
 */
public class HibernateSessionHelper {

    public FullTextSession getFullTextSession(EntityManager em) {
        return new FullTextSessionImpl(getHibernateSession(em));
    }

    public Session getHibernateSession(EntityManager em) {
        return (Session) em.getDelegate();
    }
}