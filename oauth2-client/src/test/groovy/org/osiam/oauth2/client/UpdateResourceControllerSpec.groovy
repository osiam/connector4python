//package org.osiam.oauth2.client
//
//
//
//import org.osiam.oauth2.client.exceptions.UserFriendlyException
//import spock.lang.Specification
//
//import javax.servlet.http.HttpServletRequest
//
///**
// * Created with IntelliJ IDEA.
// * User: jtodea
// * Date: 26.03.13
// * Time: 10:11
// * To change this template use File | Settings | File Templates.
// */
//class UpdateResourceControllerSpec extends Specification {
//
//    def servletRequest = Mock(HttpServletRequest)
//    def httpClient = Mock(HttpClient)
//    def jsonString = "{\"userName\":\"userName\",\"password\":\"password\",\"externalId\":\"externalId\"}"
//
//    UpdateResourceController updateResourceController = new UpdateResourceController(httpClient: httpClient)
//
//
//    def "should be able to update a user resource"() {
//        given:
//        servletRequest.getScheme() >> "http"
//        servletRequest.getServerName() >> "localhost"
//
//        when:
//        updateResourceController.updateResource(servletRequest,  "schema",
//                "user_name",
//                "firstname",
//                "lastname",
//                "displayname",
//                "nickname",
//                "profileurl",
//                "title",
//                "usertype",
//                "preferredlanguage",
//                "locale",
//                "timezone",
//                //timezone
//                "password",
//                "emails",
//                "phonenumbers",
//                "ims",
//                "photos",
//                "addresses",
//                "groups",
//                "entitlements",
//                "roles",
//                "x509",
//                "any",
//                "access_token",
//                "idForUpdate"
//        )
//
//        then:
//        1 * httpClient.executeMethod({ PutMethod put ->
//            put.statusLine = new StatusLine("HTTP/1.1 200 OK")
//        })
//        1 * servletRequest.setAttribute("userResponse", _)
//        1 * servletRequest.setAttribute("LocationHeader", _)
//    }
//
//    def "should return userFriendlyException when StatusCode is 404"() {
//        given:
//        def put = Mock(PutMethod)
//        put.getStatusCode() >> 404
//
//        when:
//        updateResourceController.readJsonFromBody(servletRequest, put)
//
//        then:
//        def e = thrown(UserFriendlyException)
//        e.toString() == "Error Code: 404<br>Message: User doesn't exists and can't be updated"
//    }
//}