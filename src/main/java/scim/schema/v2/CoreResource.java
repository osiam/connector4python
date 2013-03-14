package scim.schema.v2;

/**
 * Java class for CoreResource complex type.
 */
public abstract class CoreResource  extends Resource{

    protected final String externalId;

    public CoreResource(Builder builder) {
        super(builder);
        this.externalId = builder.externalId;
    }

    public static class Builder extends Resource.Builder {
        protected String externalId;

        public Builder setExternalId(String externalId) {
            this.externalId = externalId;
            return this;
        }
    }


    /**
     * Gets the value of the externalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalId() {
        return externalId;
    }
}
