package org.osiam.ng.scim.mvc.user;

import org.osiam.ng.scim.dao.SCIMUserProvisioning;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import scim.schema.v2.User;

import javax.inject.Inject;

/**
 * This Controller is used to manage User
 * <p/>
 * http://tools.ietf.org/html/draft-ietf-scim-core-schema-00#section-6
 * <p/>
 * it is based on the SCIM 2.0 API Specification:
 * <p/>
 * http://tools.ietf.org/html/draft-ietf-scim-api-00#section-3
 *
 * @Author phil
 */
@Controller
@RequestMapping(value = "/User")
public class UserController {

    public SCIMUserProvisioning getScimUserProvisioning() {
        return scimUserProvisioning;
    }

    public void setScimUserProvisioning(SCIMUserProvisioning scimUserProvisioning) {
        this.scimUserProvisioning = scimUserProvisioning;
    }

    @Inject
    private SCIMUserProvisioning scimUserProvisioning;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@PathVariable final String id) {
        return scimUserProvisioning.getById(id);
    }


}
