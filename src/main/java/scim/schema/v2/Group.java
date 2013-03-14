package scim.schema.v2;

import java.util.ArrayList;
import java.util.List;


/**
 * Java class for Group complex type.
 */
public class Group extends CoreResource{

    private final String displayName;
    private final Group.Members members;
    private final Object any;

    public Group(Builder builder) {
        super(builder);
        this.displayName = builder.displayName;
        this.members = builder.members;
        this.any = builder.any;

    }

    public static class Builder extends CoreResource.Builder{

        protected String displayName;
        protected Group.Members members;
        protected Object any;

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
     * Gets the value of the any property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getAny() {
        return any;
    }

    /**
     * Java class for anonymous complex type.
     */
    public static class Members {

        protected List<MultiValuedAttribute> member = new ArrayList<>();;

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
        public List<MultiValuedAttribute> getMember() {
            return this.member;
        }

    }

}
