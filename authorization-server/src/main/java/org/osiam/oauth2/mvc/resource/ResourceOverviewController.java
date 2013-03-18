/*
 * Copyright (C) 2013 tarent AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
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
