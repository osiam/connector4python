package scim.schema.v2;

import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 * Java class for name complex type.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Name {

    protected String formatted;
    protected String familyName;
    protected String givenName;
    protected String middleName;
    protected String honorificPrefix;
    protected String honorificSuffix;

    /**
     * Gets the value of the formatted property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormatted() {
        return formatted;
    }

    /**
     * Sets the value of the formatted property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormatted(String value) {
        this.formatted = value;
    }

    /**
     * Gets the value of the familyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Sets the value of the familyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFamilyName(String value) {
        this.familyName = value;
    }

    /**
     * Gets the value of the givenName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Sets the value of the givenName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGivenName(String value) {
        this.givenName = value;
    }

    /**
     * Gets the value of the middleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the value of the middleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddleName(String value) {
        this.middleName = value;
    }

    /**
     * Gets the value of the honorificPrefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHonorificPrefix() {
        return honorificPrefix;
    }

    /**
     * Sets the value of the honorificPrefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHonorificPrefix(String value) {
        this.honorificPrefix = value;
    }

    /**
     * Gets the value of the honorificSuffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHonorificSuffix() {
        return honorificSuffix;
    }

    /**
     * Sets the value of the honorificSuffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHonorificSuffix(String value) {
        this.honorificSuffix = value;
    }

}
