package org.osiam.oauth2.mvc.resource;

import org.osiam.ng.resourceserver.Attribute;
import org.osiam.ng.resourceserver.RestAttribute;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;

@Controller("pseudoResourceService")
@RequestMapping(value = "/secured/attributes")
public class PseudoAttributeController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Set<RestAttribute> getAttributes() throws Exception {
        Set<RestAttribute> attributes = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            generateLinkedAttribute(attributes, i);
        }
        return attributes;
    }

    private void generateLinkedAttribute(final Set<RestAttribute> attributes, final int i) {
        RestAttribute attr = new RestAttribute("key" + i, i);
        Link detail = new Link(ResourceOverviewController.buildHref(PseudoAttributeController.class) + "/" + i
                + "?access_token={access_token}", Attribute.class.getSimpleName() + i);
        attr.add(detail);
        attributes.add(attr);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Attribute getAttribute(@PathVariable final Long id) throws Exception {
        return new Attribute(String.valueOf(id), "val" + id);
    }


}
