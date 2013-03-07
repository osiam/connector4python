package org.osiam.oauth2.ng.scopes

import org.springframework.security.access.ConfigAttribute
import org.springframework.security.access.SecurityConfig
import org.springframework.security.oauth2.provider.vote.ScopeVoter
import org.springframework.security.web.FilterInvocation
import spock.lang.Specification
import sun.net.httpserver.Request

import javax.servlet.http.HttpServletRequest

class DynamicHTTPMethodScopeEnhancerTest extends Specification {
    def scope_haha = new SecurityConfig("haha")
    def scope_dynamic = new SecurityConfig("SCOPE_DYNAMIC");
    def basedOnVoter = Mock(ScopeVoter)
    def underTest = new DynamicHTTPMethodScopeEnhancer(basedOnVoter)
    def filter = Mock(FilterInvocation)

    def setup(){
        filter.getRequest() >> Mock(HttpServletRequest)
        filter.getRequest().getMethod() >> "get"

    }

    def "should just delegate support because we don't need to enhance something there"() {
        given:
        def basedOnVoter = Mock(ScopeVoter)
        def underTest = new DynamicHTTPMethodScopeEnhancer(basedOnVoter)
        when:
        underTest.supports(String)
        underTest.supports(scope_haha)

        then:
        1 * basedOnVoter.supports(String)
        1 * basedOnVoter.supports(scope_haha)
    }

    def "should remove SCOPE_DYNAMIC and add SCOPE_HTTP-METHOD in attributes when scope is dynamic"() {
        given:
        def attributes = [scope_dynamic, scope_haha]

        when:
        underTest.vote(null, filter, attributes)
        then:
        _ * basedOnVoter.vote(_, filter, { Collection<ConfigAttribute> a ->
            assert a.contains(scope_haha)
            assert a.contains(new SecurityConfig("SCOPE_GET"))
            assert !a.contains(scope_dynamic)
        })
    }

    def "should not touch sent attributes when scope is not dynamic"() {
        given:
        def attributes = [scope_haha]

        when:
        underTest.vote(null, filter, attributes)
        then:
        _ * basedOnVoter.vote(_, filter, { Collection<ConfigAttribute> a ->
            assert a.contains(scope_haha)
        })

    }

    def "should not touch sent attributes when object is no filter"() {
        given:
        def attributes = [scope_haha, scope_dynamic]

        when:
        underTest.vote(null, new Object(), attributes)
        then:
        _ * basedOnVoter.vote(_, filter, { Collection<ConfigAttribute> a ->
            assert a.contains(scope_haha)
            assert a.contains(scope_dynamic)
        })

    }
}
