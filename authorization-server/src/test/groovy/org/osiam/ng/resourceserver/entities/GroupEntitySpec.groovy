package org.osiam.ng.resourceserver.entities

import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 14:05
 * To change this template use File | Settings | File Templates.
 */
class GroupEntitySpec extends Specification {

    GroupEntity groupEntity = new GroupEntity()

    def "setter and getter for the Id should be present"() {
        when:
        groupEntity.setId(123456)

        then:
        groupEntity.getId() == 123456
    }

    def "setter and getter for the externalId should be present"() {
        given:
        def value = UUID.randomUUID()

        when:
        groupEntity.setValue(value)

        then:
        groupEntity.getValue() == value
    }

    def "setter and getter for the display name should be present"() {
        when:
        groupEntity.setDisplayName("Tour Guides")

        then:
        groupEntity.getDisplayName() == "Tour Guides"
    }

    def "setter and getter for the member should be present"() {
        given:
        def memberEntity = Mock(MemberEntity)
        def List<MemberEntity> members = new ArrayList<>()
        members.add(memberEntity)

        when:
        groupEntity.setMembers(members)

        then:
        groupEntity.getMembers() == members
    }

    def "setter and getter for any object should be present"() {
        when:
        groupEntity.setAny("Some Object")

        then:
        groupEntity.getAny() == "Some Object"
    }
}