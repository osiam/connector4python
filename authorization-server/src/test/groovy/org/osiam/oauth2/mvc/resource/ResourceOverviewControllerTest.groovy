package org.osiam.oauth2.mvc.resource

import spock.lang.Specification

class ResourceOverviewControllerTest extends Specification {
    def underTest = new ResourceOverviewController();

    def "should return attributes link"(){
        when:
          def result = underTest.getResources()
        then:
        result.size() == 1
        def link = result.getAt(0)
        link.rel == "Attributes"
        link.href == "http://localhost:8080/authorization-server/secured/attributes"

    }
}
