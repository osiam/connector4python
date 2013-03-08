package org.osiam.ng.resourceserver;

/**
 * This is a simple Attribute, it contains a key to find the attribute and a value.
 */
public class Attribute {
    private final String key;

    private final Object value;

    public Attribute(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }


}
