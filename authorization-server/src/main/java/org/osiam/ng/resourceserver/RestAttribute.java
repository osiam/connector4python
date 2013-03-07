package org.osiam.ng.resourceserver;

import org.springframework.hateoas.ResourceSupport;

public class RestAttribute extends ResourceSupport {
    private final String name;

    private final Object value;

    public RestAttribute(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getKey() {
        return name;
    }

    public Object getValue() {
        return value;
    }




}
