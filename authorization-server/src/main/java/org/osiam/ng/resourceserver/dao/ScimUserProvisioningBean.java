package org.osiam.ng.resourceserver.dao;

import org.osiam.ng.resourceserver.entities.UserEntity;
import org.osiam.ng.scim.dao.SCIMUserProvisioning;
import scim.schema.v2.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
public class ScimUserProvisioningBean implements SCIMUserProvisioning {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User getById(String id) {
        Query query = em.createNamedQuery("getUserById");
        query.setParameter("externalId", id);
        List result = query.getResultList();

        if (result.isEmpty()) {
            //TODO
            return null;
        }

        UserEntity userEntity = (UserEntity) result.get(0);
        //TODO: Mapping to SCIM User
        return null;
    }
}
