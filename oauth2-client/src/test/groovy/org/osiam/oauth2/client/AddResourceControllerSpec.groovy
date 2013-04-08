package org.osiam.oauth2.client

import org.apache.http.HttpResponse
import org.apache.http.HttpVersion
import org.apache.http.ProtocolVersion
import org.apache.http.client.HttpClient
import org.apache.http.message.BasicStatusLine
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
    def response = Mock(HttpResponse)
    def getResponseAndCast = Mock(GetResponseAndCast)

    def setup() {
        servletRequest.getScheme() >> "http"
        servletRequest.getServerName() >> "localhost"
    }



    AddResourceController addResourceController = new AddResourceController(getResponseAndCast: getResponseAndCast)

//    def "should set user id"() {
//        given:
//        def idSize = CRUDRedirectController.userIds.size()
//        when:
//        addResourceController.setUserId(jsonString)
//        then:
//        CRUDRedirectController.userIds.size() == idSize + 1
//
//    }
//
//    def "should not set user id when response is null"() {
//        given:
//        def idSize = CRUDRedirectController.userIds.size()
//        when:
//        addResourceController.setUserId(null)
//        then:
//        CRUDRedirectController.userIds.size() == idSize
//
    //}

    def "should not set name when no first nor lastname got submitted"() {
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
                "password"
                ,
        )

        then:
        result == '{"schemas":["schema"],"userName":"user_name","displayName":"displayname","nickName":"nickname","profileUrl":"profileurl","title":"title","userType":"usertype","preferredLanguage":"preferredlanguage","locale":"locale","timezone":"timezone","password":"password","emails":{},"phoneNumbers":{},"ims":{},"photos":{},"groups":{},"entitlements":{},"roles":{},"x509Certificates":{}}'
    }

    def "should return scim.schema.v2.Constants schema when no schema got submitted"() {
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
                "password"
                ,
        )

        then:
        result == '{"schemas":["urn:scim:schemas:core:1.0"],"userName":"user_name","displayName":"displayname","nickName":"nickname","profileUrl":"profileurl","title":"title","userType":"usertype","preferredLanguage":"preferredlanguage","locale":"locale","timezone":"timezone","password":"password","emails":{},"phoneNumbers":{},"ims":{},"photos":{},"groups":{},"entitlements":{},"roles":{},"x509Certificates":{}}'
    }

    def "should return scim.schema.v2.Constants schema when empty schema got submitted"() {
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
                "password"
                ,
        )

        then:
        result == '{"schemas":["urn:scim:schemas:core:1.0"],"userName":"user_name","displayName":"displayname","nickName":"nickname","profileUrl":"profileurl","title":"title","userType":"usertype","preferredLanguage":"preferredlanguage","locale":"locale","timezone":"timezone","password":"password","emails":{},"phoneNumbers":{},"ims":{},"photos":{},"groups":{},"entitlements":{},"roles":{},"x509Certificates":{}}'
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
                "password"
                ,
                "access_token"
        )

        then:
        1 * getResponseAndCast.getResponseAndSetAccessToken(servletRequest, "access_token", _, _)
        1 * servletRequest.getScheme()
        1 * servletRequest.getServerName()
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
                "password"
        )
        then:
        result ==
                '{"schemas":["schema"],"userName":"user_name","name":{"formatted":"firstname lastname","familyName":"lastname","givenName":"firstname"},"displayName":"displayname","nickName":"nickname","profileUrl":"profileurl","title":"title","userType":"usertype","preferredLanguage":"preferredlanguage","locale":"locale","timezone":"timezone","password":"password","emails":{},"phoneNumbers":{},"ims":{},"photos":{},"groups":{},"entitlements":{},"roles":{},"x509Certificates":{}}'
    }
}