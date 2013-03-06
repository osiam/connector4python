package org.osiam.oauth2.client

import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 06.03.13
 * Time: 13:22
 * To change this template use File | Settings | File Templates.
 */
class HTTPConnectionWraperTest extends Specification {
    def "should create httpClient and postMethod"() {
        when:
        def a = HTTPConnectionWraper.createClient()
        def b = HTTPConnectionWraper.createPost()
        then:
        a
        b
    }
}
