package org.osiam.oauth2.mvc.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(value = "/")
public class ResourceOverviewController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Set<Link> getResources() throws Exception {
        Set<Link> attributes = new HashSet<>();
        String mapping = PseudoAttributeController.class.getAnnotation(RequestMapping.class).value()[0];
        Link detail = ControllerLinkBuilder.linkTo(ResourceOverviewController.class).slash(mapping).withRel("Attributes");
        attributes.add(detail);
        return attributes;
    }

}
