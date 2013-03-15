package org.osiam.ng.resourceserver.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Roles Entity
 */
@Entity(name = "roles")
public class RolesEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String value;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}