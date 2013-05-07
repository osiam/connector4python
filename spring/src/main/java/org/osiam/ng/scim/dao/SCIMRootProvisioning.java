package org.osiam.ng.scim.dao;

import scim.schema.v2.Resource;

import java.util.List;

/**
 * This interface has the purpose to provide SCIM root URI functionality
 */
public interface SCIMRootProvisioning<T extends Resource> {

    /**
     * This method provide a search across booth, users and groups.
     *
     * @param filter the filter expression.
     * @return the search results
     */
    List<T> search(String filter);
}