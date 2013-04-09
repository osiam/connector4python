package org.osiam.oauth2.client

import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 26.03.13
 * Time: 10:11
 * To change this template use File | Settings | File Templates.
 */
class PatchResourceControllerSpec extends Specification {

    def getResponseAndCast = Mock(GetResponseAndCast)
    HttpServletRequest servletRequest = Mock(HttpServletRequest)



    def updateResourceController = new PatchResourceController(getResponseAndCast: getResponseAndCast)


    def "should be able to update a user resource"() {
        when:
        updateResourceController.updateResource(servletRequest,
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
                "access_token",
                "idForUpdate",
                "one,two"
        )

        then:
        1 * getResponseAndCast.getResponseAndSetAccessToken(servletRequest, "access_token", _, _)
        1 * servletRequest.getScheme()
        1 * servletRequest.getServerName()

    }

    def "should ignore attributes when empty"() {
        when:
        def result = updateResourceController.generateAttributesToDelete("")
        then:
        !result
    }

    def "should ignore attributes when null"() {
        when:
        def result = updateResourceController.generateAttributesToDelete(null)
        then:
        !result
    }

}