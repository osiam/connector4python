package org.osiam.oauth2.client

import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.StatusLine
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
    def jsonString = '{"id":"ef25ece4-8a02-4cb0-bdcf-d8be3e5134ab","schemas":["urn:scim:schemas:core:1.0"],"userName":"unionsionldjskla","name":{"formatted":"Arthur Dent","familyName":"Dent","givenName":"Arthur"},"displayName":"Doesn\'t like to speak to birds anymore ...","nickName":"Arthi","profileUrl":"http://en.wikipedia.org/wiki/The_Hitchhiker%27s_Guide_to_the_Galaxy","userType":"A hapless Englishman","preferredLanguage":"Vogon","locale":"vo","timezone":"UTC -42 +23 / 5"}'

    def setup() {
        servletRequest.getScheme() >> "http"
        servletRequest.getServerName() >> "localhost"
    }



    AddResourceController addResourceController = new AddResourceController(httpClient: httpClient)

    def "should set user id"(){
        given:
        def idSize = CRUDRedirectController.userIds.size()
        when:
        addResourceController.setUserId(servletRequest, jsonString)
        then:
        CRUDRedirectController.userIds.size() == idSize + 1

    }

    def "should not set user id when response is null"(){
        given:
        def idSize = CRUDRedirectController.userIds.size()
        when:
        addResourceController.setUserId(servletRequest, null)
        then:
        CRUDRedirectController.userIds.size() == idSize


    }

    def "should not set name when no first nor lastname got submitted"(){
        when:
        def result = AddResourceController.getJsonString(
                "schema",
                "user_name",
                null,
                null,
                "displayname",
                "nickname",
                "profileurl",
                "title",
                "usertype",
                "preferredlanguage",
                "locale",
                "timezone",
                //timezone
                "password",
                "emails",
                "phonenumbers",
                "ims",
                "photos",
                "addresses",
                "groups",
                "entitlements",
                "roles",
                "x509",
                "any",
        )

        then:
        result == '{"schemas":["schema"],"userName":"user_name","displayName":"displayname","nickName":"nickname","profileUrl":"profileurl","title":"title","userType":"usertype","preferredLanguage":"preferredlanguage","locale":"locale","timezone":"timezone","password":"password"}'
    }

    def "should return scim.schema.v2.Constants schema when no schema got submitted"(){
        when:
        def result = AddResourceController.getJsonString(
                null,
                "user_name",
                null,
                null,
                "displayname",
                "nickname",
                "profileurl",
                "title",
                "usertype",
                "preferredlanguage",
                "locale",
                "timezone",
                //timezone
                "password",
                "emails",
                "phonenumbers",
                "ims",
                "photos",
                "addresses",
                "groups",
                "entitlements",
                "roles",
                "x509",
                "any",
        )

        then:
        result == '{"schemas":["urn:scim:schemas:core:1.0"],"userName":"user_name","displayName":"displayname","nickName":"nickname","profileUrl":"profileurl","title":"title","userType":"usertype","preferredLanguage":"preferredlanguage","locale":"locale","timezone":"timezone","password":"password"}'
    }

    def "should return scim.schema.v2.Constants schema when empty schema got submitted"(){
        when:
        def result = AddResourceController.getJsonString(
                "",
                "user_name",
                null,
                null,
                "displayname",
                "nickname",
                "profileurl",
                "title",
                "usertype",
                "preferredlanguage",
                "locale",
                "timezone",
                //timezone
                "password",
                "emails",
                "phonenumbers",
                "ims",
                "photos",
                "addresses",
                "groups",
                "entitlements",
                "roles",
                "x509",
                "any",
        )

        then:
        result == '{"schemas":["urn:scim:schemas:core:1.0"],"userName":"user_name","displayName":"displayname","nickName":"nickname","profileUrl":"profileurl","title":"title","userType":"usertype","preferredLanguage":"preferredlanguage","locale":"locale","timezone":"timezone","password":"password"}'
    }




    def "should be able to create a user resource"() {
        when:
        addResourceController.createResource(servletRequest,
                "schema",
                "user_name",
                "firstname",
                "lastname",
                "displayname",
                "nickname",
                "profileurl",
                "title",
                "usertype",
                "preferredlanguage",
                "locale",
                "timezone",
                //timezone
                "password",
                "emails",
                "phonenumbers",
                "ims",
                "photos",
                "addresses",
                "groups",
                "entitlements",
                "roles",
                "x509",
                "any",
                "access_token"
        )

        then:
        1 * httpClient.executeMethod({ PostMethod post ->
            post.statusLine = new StatusLine("HTTP/1.1 200 OK")
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
        def result = addResourceController.getJsonString(
                "schema",
                "user_name",
                "firstname",
                "lastname",
                "displayname",
                "nickname",
                "profileurl",
                "title",
                "usertype",
                "preferredlanguage",
                "locale",
                "timezone",
                //timezone
                "password",
                "emails",
                "phonenumbers",
                "ims",
                "photos",
                "addresses",
                "groups",
                "entitlements",
                "roles",
                "x509",
                "any"
        )
        then:
        result ==
                '{"schemas":["schema"],"userName":"user_name","name":{"formatted":"firstname lastname","familyName":"lastname","givenName":"firstname"},"displayName":"displayname","nickName":"nickname","profileUrl":"profileurl","title":"title","userType":"usertype","preferredLanguage":"preferredlanguage","locale":"locale","timezone":"timezone","password":"password"}'
    }
}