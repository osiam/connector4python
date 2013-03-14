package scim.schema.v2;

/**
 * Java class for address complex type.
 */
public class Address extends MultiValuedAttribute {

    private final String formatted;
    private final String streetAddress;
    private final String locality;
    private final String region;
    private final String postalCode;
    private final String country;

    private Address(Builder builder) {
        this.formatted = builder.formatted;
        this.streetAddress = builder.streetAddress;
        this.locality = builder.locality;
        this.region = builder.region;
        this.postalCode = builder.postalCode;
        this.country = builder.country;

    }

    public static class Builder {
        private String formatted;
        private String streetAddress;
        private String locality;
        private String region;
        private String postalCode;
        private String country;

        public Builder setFormatted(String formatted) {
            this.formatted = formatted;
            return this;
        }

        public Builder setStreetAddress(String streetAddress) {
            this.streetAddress = streetAddress;
            return this;
        }

        public Builder setLocality(String locality) {
            this.locality = locality;
            return this;
        }

        public Builder setRegion(String region) {
            this.region = region;
            return this;
        }

        public Builder setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Address build() {
            return new Address(this);
        }


    }

    /**
     * Gets the value of the formatted property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getFormatted() {
        return formatted;
    }

    /**
     * Gets the value of the streetAddress property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Gets the value of the locality property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getLocality() {
        return locality;
    }

    /**
     * Gets the value of the region property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getRegion() {
        return region;
    }

    /**
     * Gets the value of the postalCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Gets the value of the country property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCountry() {
        return country;
    }
}
