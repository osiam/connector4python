package org.osiam.ng.resourceserver;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 06.03.13
 * Time: 14:22
 * To change this template use File | Settings | File Templates.
 */
public class Attribute {
    private final String name;

    private final Object value;

    public Attribute(String name, Object value) {
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
