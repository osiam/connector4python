package org.osiam.ng.resourceserver;

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
