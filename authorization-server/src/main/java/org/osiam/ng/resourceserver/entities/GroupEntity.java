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

import scim.schema.v2.Group;
import scim.schema.v2.MultiValuedAttribute;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Group Entity
 */
@Entity(name = "scim_group")
public class GroupEntity extends InternalIdSkeleton {


    @OneToMany(fetch = FetchType.EAGER)
    private Set<InternalIdSkeleton> members = new HashSet<>();

    @Column(name = "additional")
    private String any;


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

    @Override
    public Group toScim() {
        return new Group.Builder().setDisplayName(getDisplayName()).setMembers(membersToScim())
                .setExternalId(getExternalId()).setId(getId().toString()).build();
    }

    private Group.Members membersToScim() {
        Group.Members members1 = new Group.Members();
        for (InternalIdSkeleton i : members) {
            members1.getMember().add(new MultiValuedAttribute.Builder().setValue(i.getId().toString())
                    .setDisplay(i.getDisplayName()).build());
        }
        return members1;
    }

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
        if (group.getMembers() != null)
            transferMultiValueAttributeToInternalIdSkeleton(group, result);

        return result;
    }

    private static void transferMultiValueAttributeToInternalIdSkeleton(Group group, Set<InternalIdSkeleton> result) {
        for (MultiValuedAttribute m : group.getMembers().getMember()) {
            InternalIdSkeleton skeleton = new InternalIdSkeleton() {
                @Override
                public <T> T toScim() {
                    return null;  //To change body of implemented methods use File | Settings | File Templates.
                }
            };
            skeleton.setDisplayName(m.getDisplay());
            skeleton.setId(UUID.fromString(String.valueOf(m.getValue())));
            result.add(skeleton);
        }
    }
}