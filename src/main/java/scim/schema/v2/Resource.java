package scim.schema.v2;

/**
 * Java class for Resource complex type.
 */
public abstract class Resource {

    protected final String id;
    protected final Meta meta;

    protected Resource(Builder builder) {
        this.id = builder.id;
        this.meta = builder.meta;
    }

    public static class Builder {
        protected String id;
        protected Meta meta;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setMeta(Meta meta) {
            this.meta = meta;
            return this;
        }
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the value of the meta property.
     *
     * @return possible object is
     *         {@link Meta }
     */
    public Meta getMeta() {
        return meta;
    }


}
