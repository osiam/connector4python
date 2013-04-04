package org.osiam.ng.resourceserver

import spock.lang.Ignore
import spock.lang.Specification

@Ignore("Spring MVC doesn't support PATCH yet")
class UserPatchTest extends Specification {
    def "should delete single attribute of a multi-value-attribute list"() { when: true; then: false; }

    def "should delete all attributes of a multi-value-attribute list"() { when: true; then: false; }

    def "should replace attributes of a multi-value-attribute list"() { when: true; then: false; }

    def "should delete and add a value to a multi-value-attribute list"() { when: true; then: false; }

    def "should update a single attribute"() { when: true; then: false; }

    def "should replace a non Sub-Attribute able attribute of an user (e.q. addresses)"() { when: true; then: false; }

    def "should delete simple attributes"() { when: true; then: false; }

    def "should delete and update simple attributes"() { when: true; then: false; }

    def "should ignore update when simple-attribute is in meta"() { when: true; then: false; }

    def "should update parts of an complex attribute"() { when: true; then: false; }

    def "should remove parts of an complex attribute"() { when: true; then: false; }

    def "should abort when trying to override read-only attributes"() { when: true; then: false; }


}
