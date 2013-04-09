package org.osiam.oauth2.client

import org.apache.http.impl.client.DefaultHttpClient
import spock.lang.Specification

class GenerateClientTest extends Specification {
    def generateClient = new GenerateClient()

    def "should return a new http client"() {
        when:
        def result = generateClient.getClient()
        def result2 = generateClient.getClient()
        then:
        result != result2
        result instanceof DefaultHttpClient
    }

}
