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

import scim.schema.v2.MultiValuedAttribute;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * Group Entity
 */
@Entity(name = "scim_group")
public class GroupEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private UUID value;

    @Column(nullable = false)
    private String displayName;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<MemberEntity> members;

    @Column(name = "additional")
    private String any;

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

    public Set<MemberEntity> getMembers() {
        return members;
    }

    public void setMembers(Set<MemberEntity> members) {
        this.members = members;
    }

    public Object getAny() {
        return any;
    }

    public void setAny(String any) {
        this.any = any;
    }

    public MultiValuedAttribute toScim() {
        return new MultiValuedAttribute.Builder().
                setDisplay(getDisplayName()).
                setValue(getValue()).
                build();
    }
}