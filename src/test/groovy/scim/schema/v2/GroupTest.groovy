package scim.schema.v2

import spock.lang.Specification

class GroupTest extends Specification {
    def "should be able to generate a group"() {
        given:
        def builder = new Group.Builder().setDisplayName("display").setMembers(new Group.Members()).setAny(new Object())
        when:
        def group = builder.build()

        then:
        group.any == builder.any
        group.displayName == builder.displayName
        group.members == builder.members
    }

    def "should be able to enrich group members"() {
        given:
        def group = new Group.Builder().setDisplayName("display").setMembers(new Group.Members()).setAny(new Object()).build()
        when:
        group.members.member.add(new MultiValuedAttribute())

        then:
        group.members.member.size() == 1
    }
}
