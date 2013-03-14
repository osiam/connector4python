package org.osiam.ng.resourceserver.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Instant messaging Entity
 */
@Entity(name = "ims")
public class ImsEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String aim;

    @Column
    private String gtalk;

    @Column
    private String icq;

    @Column
    private String xmpp;

    @Column
    private String msn;

    @Column
    private String skype;

    @Column
    private String qq;

    @Column
    private String yahoo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAim() {
        return aim;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }

    public String getGtalk() {
        return gtalk;
    }

    public void setGtalk(String gtalk) {
        this.gtalk = gtalk;
    }

    public String getIcq() {
        return icq;
    }

    public void setIcq(String icq) {
        this.icq = icq;
    }

    public String getXmpp() {
        return xmpp;
    }

    public void setXmpp(String xmpp) {
        this.xmpp = xmpp;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getYahoo() {
        return yahoo;
    }

    public void setYahoo(String yahoo) {
        this.yahoo = yahoo;
    }
}