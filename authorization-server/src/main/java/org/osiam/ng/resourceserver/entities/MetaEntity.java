package org.osiam.ng.resourceserver.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 14:35
 * To change this template use File | Settings | File Templates.
 */
@Entity(name = "meta")
public class MetaEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private GregorianCalendar created;

    @Column
    private GregorianCalendar lastModified;

    @Column
    private String location;

    @Column
    private String version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GregorianCalendar getCreated() {
        return created;
    }

    public void setCreated(GregorianCalendar created) {
        this.created = created;
    }

    public GregorianCalendar getLastModified() {
        return lastModified;
    }

    public void setLastModified(GregorianCalendar lastModified) {
        this.lastModified = lastModified;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}