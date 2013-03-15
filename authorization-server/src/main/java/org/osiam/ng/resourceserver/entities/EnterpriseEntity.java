package org.osiam.ng.resourceserver.entities;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 16:19
 * To change this template use File | Settings | File Templates.
 */
@Entity(name = "enterprise")
public class EnterpriseEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String employeeNumber;

    @Column
    private String costCenter;

    @Column
    private String organization;

    @Column
    private String division;

    @Column
    private String department;

    @OneToOne
    private ManagerEntity manager;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public ManagerEntity getManager() {
        return manager;
    }

    public void setManager(ManagerEntity manager) {
        this.manager = manager;
    }
}