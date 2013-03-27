package org.osiam.oauth2.client

import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.PostMethod
import org.apache.http.StatusLine
import org.json.JSONException
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 25.03.13
 * Time: 15:06
 * To change this template use File | Settings | File Templates.
 */
class AddResourceControllerSpec extends Specification {

    def servletRequest = Mock(HttpServletRequest)
    def httpClient = Mock(HttpClient)
    def jsonString = "{\"userName\":\"userName\",\"password\":\"password\",\"externalId\":\"externalId\"}"

    AddResourceController addResourceController = new AddResourceController(httpClient: httpClient)


    def "should be able to create a user resource"() {
        given:
        servletRequest.getScheme() >> "http"
        servletRequest.getServerName() >> "localhost"

        when:
        addResourceController.createResource(servletRequest, "externalId", "userName", "password", "access_token")

        then:
        1 * httpClient.executeMethod({ PostMethod post ->
            post.responseStream = new ByteArrayInputStream(jsonString.getBytes())
        })
        1 * servletRequest.setAttribute("userResponse", _)
        1 * servletRequest.setAttribute("LocationHeader", _)
    }

    def "should wrap json exception to IllegalStateException"() {
        given:
        servletRequest.getScheme() >> "http"
        servletRequest.getServerName() >> "localhost"
        String jsonString = "{\"userName\":\"userName\",\"password\":\"password\",\"externalId\":\"externalId\""


        when:
        addResourceController.createResource(servletRequest, "externalId", "userName", "password", "access_token")

        then:
        _ * httpClient.executeMethod({ PostMethod post ->
            post.statusLine = Mock(org.apache.commons.httpclient.StatusLine)
            post.responseStream = new ByteArrayInputStream(jsonString.getBytes())
        })


        IllegalStateException e = thrown()
        e.message == "Expected a ',' or '}' at character 70"
        e.cause.class == JSONException.class
    }
}