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
/**
 * Mainly used for demonstration, it is a fake of an resource server. it provides a list of 10 attributes and a method
 * to simulate an access of one attribute.
 */
public class PseudoAttributeController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Set<RestAttribute> getAttributes() {
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
    public Attribute getAttribute(@PathVariable final Long id) {
        return new Attribute(String.valueOf(id), "val" + id);
    }


}
