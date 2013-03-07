package org.osiam.oauth2.user.please.remove.me.asap

import spock.lang.Specification

class PseudoAuthenticationBeanTest extends Specification {
    def underTest = new PseudoAuthenticationBean()

    def "should return an activated account"(){
        when:
           def result = underTest.loadUserByUsername("blubb")
        then:
        result.accountNonExpired
        result.accountNonLocked
        result.credentialsNonExpired
        result.enabled
    }

    def "should dummy data of account"(){
        when:
        def result = underTest.loadUserByUsername("blubb")
        then:
        result.password == "koala"
        result.username == "blubb"
        result.getAuthorities().size() == 1
        result.getAuthorities().iterator().next().authority == "ROLE_USER"
    }

}
