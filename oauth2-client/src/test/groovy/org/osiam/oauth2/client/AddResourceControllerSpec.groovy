package org.osiam.oauth2.client

import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.PostMethod
import org.json.JSONException
import org.osiam.oauth2.client.exceptions.UserFriendlyException
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

    def setup() {
        servletRequest.getScheme() >> "http"
        servletRequest.getServerName() >> "localhost"
    }



    AddResourceController addResourceController = new AddResourceController(httpClient: httpClient)


    def "should be able to create a user resource"() {
        when:
        addResourceController.createResource(servletRequest, "externalId", "userName", "password", "access_token")

        then:
        1 * httpClient.executeMethod({ PostMethod post ->
            post.statusLine = Mock(org.apache.commons.httpclient.StatusLine)
            post.responseStream = new ByteArrayInputStream(jsonString.getBytes())
        })
        1 * servletRequest.setAttribute("userResponse", _)
        1 * servletRequest.setAttribute("LocationHeader", _)
    }

    def "should return userFriendlyException when StatusCode is 409"() {
        given:
        def post2 = Mock(PostMethod)
        post2.getStatusCode() >> 409

        when:
        addResourceController.readJsonFromBody(servletRequest, post2)

        then:
        def e = thrown(UserFriendlyException)
        e.toString() == "Error Code: 409<br>Message: User already exists and can't be created"
    }

    def "should generate valid JSON String"() {
        when:
        def result = addResourceController.getJsonString("externalid", "name", "password")
        then:
        result == '{"schemas":["urn:scim:schemas:core:1.0"],"externalId":"externalid","userName":"name","password":"password"}'
    }
}