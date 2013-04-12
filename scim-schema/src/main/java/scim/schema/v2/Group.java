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

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.*;


/**
 * Java class for Group complex type.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public class Group extends CoreResource{

    private String displayName;
    private Group.Members members;

    //JSON Serializing
    public Group(){}


    public Group(Builder builder) {
        super(builder);
        this.displayName = builder.displayName;
        this.members = builder.members;
//        this.id = builder.id;

    }

    public static class Builder extends CoreResource.Builder{

        protected String displayName;
        protected Group.Members members;
        protected Object any;

        public Builder(){}

        public Builder(Group group) {
            Builder builder = new Builder();
            builder.id = group.id;
            builder.meta = group.meta;
            builder.externalId = group.externalId;
            builder.displayName = group.displayName;
            builder.members = group.members;


        }

        public Builder setDisplayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder setMembers(Members members) {
            this.members = members;
            return this;
        }

        public Builder setAny(Object any) {
            this.any = any;
            return this;
        }

        public Group build(){
            return new Group(this);
        }

    }



    /**
     * Gets the value of the displayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayName() {
        return displayName;
    }


    /**
     * Gets the value of the members property.
     * 
     * @return
     *     possible object is
     *     {@link Group.Members }
     *     
     */
    public Group.Members getMembers() {
        return members;
    }

    /**
     * Java class for anonymous complex type.
     */
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
    public static class Members implements ContainsListOfMultiValue.MustExist{

        protected Set<MultiValuedAttribute> member = new HashSet<>();;

        /**
         * Gets the value of the member property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the member property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMember().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link MultiValuedAttribute }
         * 
         * 
         */
        public Set<MultiValuedAttribute> getMember() {
            return this.member;
        }

        @Override
        public Collection<MultiValuedAttribute> values() {
            return this.member;
        }
    }

}
