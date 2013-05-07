package org.osiam.ng.resourceserver.dao;

import org.osiam.ng.scim.dao.SCIMRootProvisioning;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 07.05.13
 * Time: 10:16
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SCIMRootProvisioningBean implements SCIMRootProvisioning {

    @Inject
    private RootDAO rootDAO;

    protected RootDAO getDao() {
        return rootDAO;
    }

    @Override
    public List search(String filter) {
        return getDao().search(filter);
    }
}
