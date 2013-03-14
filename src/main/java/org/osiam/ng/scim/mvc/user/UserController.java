package org.osiam.ng.scim.mvc.user;

import org.osiam.ng.scim.exceptions.ResourceNotFoundException;
import org.osiam.ng.scim.scheme.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/User")
public class UserController {

//    private void generateLinkedAttribute(final Set<RestAttribute> attributes, final int i) {
//        RestAttribute attr = new RestAttribute("key" + i, i);
//        Link detail = new Link(ResourceOverviewController.buildHref(UserController.class) + "/" + i
//                + "?access_token={access_token}", Attribute.class.getSimpleName() + i);
//        attr.add(detail);
//        attributes.add(attr);
//    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@PathVariable final String id) {
        throw new ResourceNotFoundException("No user with id found.");
    }


}
