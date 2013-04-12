package org.osiam.ng.resourceserver

import org.osiam.ng.resourceserver.dao.GroupDAO
import org.osiam.ng.resourceserver.dao.SCIMGroupProvisioningBean
import org.osiam.ng.resourceserver.entities.GroupEntity
import org.osiam.ng.scim.exceptions.ResourceExistsException
import org.osiam.ng.scim.exceptions.ResourceNotFoundException
import org.springframework.dao.DataIntegrityViolationException
import scim.schema.v2.Group
import scim.schema.v2.MultiValuedAttribute
import spock.lang.Specification

import javax.persistence.EntityManager
import javax.persistence.Query

class GroupCreateTest extends Specification {
    EntityManager em = Mock(EntityManager)
    GroupDAO dao = new GroupDAO(em: em)
    def underTest = new SCIMGroupProvisioningBean(groupDAO: dao)
    Group.Members members = new Group.Members()



    def "should abort when a member in group is not findable"() {
        given:
        def internalId = UUID.randomUUID()
        def query = Mock(Query)
        def queryResults = []
        members.member.add(new MultiValuedAttribute.Builder().setValue(internalId.toString()).build())
        def group = new Group.Builder().setMembers(members).build()
        when:
        underTest.createGroup(group)
        then:
        1 * em.createNamedQuery("getById") >> query
        1 * query.setParameter("id", internalId);
        1 * query.getResultList() >> queryResults
        def e = thrown(ResourceNotFoundException)
        e.message == "Resource " + internalId.toString() + " not found."

    }


    def "should abort when a group already exists"() {
        given:
        def internalId = UUID.randomUUID()
        def query = Mock(Query)
        members.member.add(new MultiValuedAttribute.Builder().setValue(internalId.toString()).build())
        def group = new Group.Builder().setMembers(members).build()
        when:
        underTest.createGroup(group)
        then:
        1 * em.createNamedQuery("getById") >> query
        1 * query.setParameter("id", internalId);
        1 * query.getResultList() >> { throw new DataIntegrityViolationException("moep") }
        def e = thrown(ResourceExistsException)
        e.message == "null already exists."
    }


    def "should create a group with known member"() {
        given:
        def internalId = UUID.randomUUID()
        def query = Mock(Query)
        members.member.add(new MultiValuedAttribute.Builder().setValue(internalId.toString()).build())
        def group = new Group.Builder().setMembers(members).build()
        def queryResults = [GroupEntity.fromScim(group)]
        when:
        def result = underTest.createGroup(group)
        then:
        1 * em.createNamedQuery("getById") >> query
        1 * query.setParameter("id", internalId);
        1 * query.getResultList() >> queryResults
        result.members.member.size() == 1
    }

    def "should create a group without member"() {
        given:
        def group = new Group.Builder().build()
        when:
        def result = underTest.createGroup(group)
        then:
        result.members.member.size() == 0
    }


}
