package org.osiam.ng.resourceserver.entities;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * Group Entity
 */
@Entity(name = "group")
public class GroupEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private UUID value;

    @Column(nullable = false)
    private String displayName;

    @ManyToOne
    private List<MemberEntity> members;

    @Column
    private Object any;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getValue() {
        return value;
    }

    public void setValue(UUID value) {
        this.value = value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<MemberEntity> getMembers() {
        return members;
    }

    public void setMembers(List<MemberEntity> members) {
        this.members = members;
    }

    public Object getAny() {
        return any;
    }

    public void setAny(Object any) {
        this.any = any;
    }
}