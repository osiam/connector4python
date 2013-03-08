package org.osiam.oauth2.mvc.resource;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(value = "/")
/**
 * This class returns a list of known resources on root access.
 */
public class ResourceOverviewController {

    static final String PROTOCOLL = "http://";
    static final String DOMAIN_NAME = "localhost:8080";
    static final String APP_NAME = "authorization-server";

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Set<Link> getResources() {
        Set<Link> attributes = new HashSet<>();
        Link detail = new Link(buildHref(PseudoAttributeController.class) + "?access_token={access_token}", "Attributes");

        attributes.add(detail);
        return attributes;
    }

    static String buildHref(Class<?> clazz) {
        String mapping = clazz.getAnnotation(RequestMapping.class).value()[0];
        return PROTOCOLL + DOMAIN_NAME + "/" + APP_NAME + mapping;
    }

}
