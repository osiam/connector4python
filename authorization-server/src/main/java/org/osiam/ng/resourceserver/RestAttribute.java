package org.osiam.ng.resourceserver;

import org.springframework.hateoas.ResourceSupport;

/**
 * This is a rest implementation of @link{Attribute} it enhances a attribute with a link.
 */
public class RestAttribute extends ResourceSupport {
    private Attribute attribute;

    public RestAttribute(String name, Object value) {
        this.attribute = new Attribute(name, value);
    }

    public String getKey() {
        return attribute.getKey();
    }

    public Object getValue() {
        return attribute.getValue();
    }


}
