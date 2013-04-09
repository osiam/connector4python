package org.osiam.oauth2.client

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
    def getResponseAndCast = Mock(GetResponseAndCast)

    AddResourceController addResourceController = new AddResourceController(getResponseAndCast: getResponseAndCast)


    def setup() {
        servletRequest.getScheme() >> "http"
        servletRequest.getServerName() >> "localhost"
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
                "access_token"
        )

        then:
        1 * getResponseAndCast.getResponseAndSetAccessToken(servletRequest, "access_token", _, _)
        1 * servletRequest.getScheme()
        1 * servletRequest.getServerName()

    }



}