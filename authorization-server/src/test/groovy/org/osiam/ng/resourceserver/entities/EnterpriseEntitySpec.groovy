package org.osiam.ng.resourceserver.entities

import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 16:27
 * To change this template use File | Settings | File Templates.
 */
class EnterpriseEntitySpec extends Specification {

    EnterpriseEntity enterpriseEntity = new EnterpriseEntity()

    def "setter and getter for the Id should be present"() {
        when:
        enterpriseEntity.setId(123456)

        then:
        enterpriseEntity.getId() == 123456
    }

    def "setter and getter for the employee number should be present"() {
        when:
        enterpriseEntity.setEmployeeNumber("701984")

        then:
        enterpriseEntity.getEmployeeNumber() == "701984"
    }

    def "setter and getter for the cost center should be present"() {
        when:
        enterpriseEntity.setCostCenter("4130")

        then:
        enterpriseEntity.getCostCenter() == "4130"
    }

    def "setter and getter for the organization should be present"() {
        when:
        enterpriseEntity.setOrganization("Universal Studios")

        then:
        enterpriseEntity.getOrganization() == "Universal Studios"
    }

    def "setter and getter for the division should be present"() {
        when:
        enterpriseEntity.setDivision("Theme Park")

        then:
        enterpriseEntity.getDivision() == "Theme Park"
    }

    def "setter and getter for the department should be present"() {
        when:
        enterpriseEntity.setDepartment("Tour Operations")

        then:
        enterpriseEntity.getDepartment() == "Tour Operations"
    }

    def "setter and getter for the manager should be present"() {
        given:
        def manager = Mock(ManagerEntity)

        when:
        enterpriseEntity.setManager(manager)

        then:
        enterpriseEntity.getManager() == manager
    }
}