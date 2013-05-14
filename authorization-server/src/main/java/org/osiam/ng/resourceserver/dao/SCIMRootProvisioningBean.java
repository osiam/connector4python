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
    private UserDAO userDAO;
    @Inject
    private GroupDAO groupDAO;

    @Override
    public List search(String filter) {
        List result = new ArrayList();
        addUserToSearchResult(filter, result);
        addGroupToSearchResult(filter, result);
        return result;
    }

    private void addUserToSearchResult(String filter, List result) {
        try {
            result.addAll(userDAO.search(filter));
        } catch (SearchException e) {
            LOGGER.log(Level.FINE, "Filter " + filter + " not useable on User", e);
        }
    }

    private void addGroupToSearchResult(String filter, List result) {
        try {
            result.addAll(groupDAO.search(filter));
        } catch (SearchException e) {
            LOGGER.log(Level.FINE, "Filter " + filter + " not useable on Group", e);
        }
    }
}
