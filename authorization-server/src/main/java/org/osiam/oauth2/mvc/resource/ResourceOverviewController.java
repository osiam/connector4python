/*
 * Copyright 2013
 *     tarent AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
