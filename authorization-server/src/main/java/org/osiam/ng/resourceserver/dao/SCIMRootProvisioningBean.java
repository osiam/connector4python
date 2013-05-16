package org.osiam.ng.resourceserver.dao;

import org.hibernate.search.SearchException;
import org.osiam.ng.scim.dao.SCIMRootProvisioning;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 07.05.13
 * Time: 10:16
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SCIMRootProvisioningBean implements SCIMRootProvisioning {

    private static final Logger LOGGER = Logger.getLogger(SCIMRootProvisioningBean.class.getName());

    @Inject
    private SCIMUserProvisioningBean scimUserProvisioningBean;

    @Inject
    private SCIMGroupProvisioningBean scimGroupProvisioningBean;

    @Override
    public List search(String filter, String sortBy, String sortOrder, int count, int startIndex) {
        List result = new ArrayList();
        addUserToSearchResult(filter, sortBy, sortOrder, count, startIndex, result);
        addGroupToSearchResult(filter, sortBy, sortOrder, count, startIndex, result);
        return result;
    }

    private void addUserToSearchResult(String filter, String sortBy, String sortOrder, int count, int startIndex, List result) {
        try {
            result.addAll(scimUserProvisioningBean.search(filter, sortBy, sortOrder, count, startIndex));
        } catch (SearchException e) {
            LOGGER.log(Level.FINE, "Filter " + filter + " not usable on User", e);
        }
    }

    private void addGroupToSearchResult(String filter, String sortBy, String sortOrder, int count, int startIndex, List result) {
        try {
            result.addAll(scimGroupProvisioningBean.search(filter, sortBy, sortOrder, count, startIndex));
        } catch (SearchException e) {
            LOGGER.log(Level.FINE, "Filter " + filter + " not usable on Group", e);
        }
    }
}
