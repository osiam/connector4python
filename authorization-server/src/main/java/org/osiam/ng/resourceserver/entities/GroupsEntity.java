package org.osiam.ng.resourceserver.entities;

import javax.persistence.*;

/**
 * Groups Entity
 */
@Entity
public class GroupsEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String right;

    @Column
    private String displayName;

    @ManyToMany
    private UserEntity members;

    @Column
    private Object any;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public UserEntity getMembers() {
        return members;
    }

    public void setMembers(UserEntity members) {
        this.members = members;
    }

    public Object getAny() {
        return any;
    }

    public void setAny(Object any) {
        this.any = any;
    }
}