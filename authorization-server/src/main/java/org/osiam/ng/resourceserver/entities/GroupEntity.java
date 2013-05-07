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

package org.osiam.ng.resourceserver.entities;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import scim.schema.v2.Group;
import scim.schema.v2.MultiValuedAttribute;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Group Entity
 */
@Entity(name = "scim_group")
@Indexed
//@NamedQueries({@NamedQuery(name = "removeMemberWithId", query = "DELETE FROM scim_group.members i WHERE i.id= :id")})
public class GroupEntity extends InternalIdSkeleton {

    @IndexedEmbedded
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Set<InternalIdSkeleton> members = new HashSet<>();

    @Column(name = "additional")
    private String any;

    @Field
    @Column(unique = true, nullable = false)
    private String displayName;


    public static GroupEntity fromScim(Group group) {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setDisplayName(group.getDisplayName());
        groupEntity.setId(group.getId() != null ? UUID.fromString(group.getId()) : UUID.randomUUID());
        groupEntity.setExternalId(group.getExternalId());
        groupEntity.setMembers(createMembers(group));
        return groupEntity;
    }

    private static Set<InternalIdSkeleton> createMembers(Group group) {
        Set<InternalIdSkeleton> result = new HashSet<>();
        if (group.getMembers() != null) { transferMultiValueAttributeToInternalIdSkeleton(group, result); }

        return result;
    }

    private static void transferMultiValueAttributeToInternalIdSkeleton(Group group, Set<InternalIdSkeleton> result) {
        for (final MultiValuedAttribute m : group.getMembers()) {
            InternalIdSkeleton skeleton = new InternalIdSkeleton() {
                @Override
                public String getDisplayName() {
                    return m.getDisplay();
                }

                @Override
                public <T> T toScim() {
                    return null;  //To change body of implemented methods use File | Settings | File Templates.
                }
            };
            skeleton.setId(UUID.fromString(String.valueOf(m.getValue())));
            result.add(skeleton);
        }
    }

    public Set<InternalIdSkeleton> getMembers() {
        return members;
    }

    public void setMembers(Set<InternalIdSkeleton> members) {
        this.members = members;
    }

    public Object getAny() {
        return any;
    }

    public void setAny(String any) {
        this.any = any;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public Group toScim() {
        return new Group.Builder().setDisplayName(getDisplayName()).setMembers(membersToScim())
                .setExternalId(getExternalId()).setId(getId().toString()).build();
    }

    private Set<MultiValuedAttribute> membersToScim() {
        Set<MultiValuedAttribute> members1 = new HashSet<>();
        for (InternalIdSkeleton i : members) {
            members1.add(
                    new MultiValuedAttribute.Builder().setValue(i.getId().toString()).setDisplay(i.getDisplayName())
                            .build());
        }
        return members1;
    }
}