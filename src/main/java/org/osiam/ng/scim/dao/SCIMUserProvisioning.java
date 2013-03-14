package org.osiam.ng.scim.dao;

import scim.schema.v2.User;

/**
 * This interface has the purpose to get SCIM user out of and into a database, which must be provided by the using
 * application. Inside UserController
 */
public interface SCIMUserProvisioning {
    /**
     * This method returns a SCIM user.
     * <p/>
     * It must throw an ResourceNotFoundException if no user got found.
     *
     * @param id the identifier of the user
     * @return the found user
     * @throws org.osiam.ng.scim.exceptions.ResourceNotFoundException
     *          if no user with the given id got found
     */
    User getById(String id);
}
