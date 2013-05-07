package org.osiam.ng.scim.mvc.user;

import org.osiam.ng.scim.dao.SCIMRootProvisioning;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import scim.schema.v2.Resource;

import javax.inject.Inject;
import java.util.List;

/**
 * This Controller is used to manage Root URI actions
 * <p/>
 * http://tools.ietf.org/html/draft-ietf-scim-core-schema-00
 * <p/>
 * it is based on the SCIM 2.0 API Specification:
 * <p/>
 * http://tools.ietf.org/html/draft-ietf-scim-api-00
 *
 *
 */
@Controller
@RequestMapping(value = "/")
public class RootController {

    @Inject
    private SCIMRootProvisioning scimRootProvisioning;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Resource> searchWithGet(@RequestParam String filter) {
        return scimRootProvisioning.search(filter);
    }

    @RequestMapping(value = ".search", method = RequestMethod.POST)
    @ResponseBody
    public List<Resource> searchWithPost(@RequestBody String filter) {
        return scimRootProvisioning.search(filter);
    }
}
