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

package scim.schema.v2;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Java class for meta complex type.
 */
public class Meta {

    private final Date created;
    private final Date lastModified;
    private final String location;
    private final String version;
    private final Meta.Attributes attributes;

    private Meta(Builder builder) {
        this.created = builder.created;
        this.lastModified = builder.lastModified;
        this.attributes = builder.attributes;
        this.location = builder.location;
        this.version = builder.version;
    }

    public static class Builder{
        private final Date created;
        private final Date lastModified;
        private String location;
        private String version;
        private Meta.Attributes attributes;

        /**
         * Will set created, as well as lastModified to System.currentTime
         */
        public Builder(){
            this.created = new Date(System.currentTimeMillis());
            this.lastModified = new Date(System.currentTimeMillis());
        }

        /**
         * Will set created to given value and lastModified to System.currentTime
         */
        public Builder(Date created){
            this.created = new Date(created.getTime());
            this.lastModified = new Date(System.currentTimeMillis());
        }

        /**
         * Will set created to given value and lastModified to System.currentTime
         */
        public Builder(Date created, Date lastModified){
            this.created = new Date(created.getTime());
            this.lastModified = new Date(lastModified.getTime());
        }

        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }

        public Builder setVersion(String version) {
            this.version = version;
            return this;
        }

        public Builder setAttributes(Attributes attributes) {
            this.attributes = attributes;
            return this;
        }

        public Meta build(){
            return new Meta(this);
        }
    }



    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }


    /**
     * Gets the value of the attributes property.
     * 
     * @return
     *     possible object is
     *     {@link Meta.Attributes }
     *     
     */
    public Meta.Attributes getAttributes() {
        return attributes;
    }

    public Date getCreated() {
        return new Date(created.getTime());
    }

    public Date getLastModified() {
        return new Date(lastModified.getTime());
    }

    /**
     * Java class for anonymous complex type.
     */
    public static class Attributes {

        private List<MultiValuedAttribute> attribute = new ArrayList<>();

        /**
         * Gets the value of the attribute property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the attribute property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAttribute().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link MultiValuedAttribute }
         * 
         * 
         */
        public List<MultiValuedAttribute> getAttribute() {
            return this.attribute;
        }

    }

}
