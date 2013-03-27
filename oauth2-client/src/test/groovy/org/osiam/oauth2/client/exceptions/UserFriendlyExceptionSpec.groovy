package org.osiam.oauth2.client.exceptions

import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 26.03.13
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
class UserFriendlyExceptionSpec extends Specification {

    def "should contain given code and corresponding message for 409"(){
        when:
        def result = new UserFriendlyException("409")
        then:
        result.toString() == "Error Code: 409<br>Message: User already exists and can't be created"
    }

    def "should contain given code and corresponding message for 404"(){
        when:
        def result = new UserFriendlyException("404")
        then:
        result.toString() == "Error Code: 404<br>Message: User doesn't exists and can't be updated"
    }

    def "should return given error code when not 409 nor 404"(){
        when:
        def result = new UserFriendlyException("not404nor409")
        then:
        result.toString() == "not404nor409"
    }
}