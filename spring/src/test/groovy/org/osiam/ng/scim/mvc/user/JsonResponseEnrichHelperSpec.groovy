package org.osiam.ng.scim.mvc.user

import scim.schema.v2.Group
import scim.schema.v2.Resource
import scim.schema.v2.User
import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.05.13
 * Time: 17:31
 * To change this template use File | Settings | File Templates.
 */
class JsonResponseEnrichHelperSpec extends Specification {

    def jsonResponseEnrichHelper = new JsonResponseEnrichHelper()

    def "should not throw exception if result list is empty"() {
        given:
        def userList = [] as List<User>
        def parameterMapMock = Mock(Map)

        parameterMapMock.get("count") >> 10
        parameterMapMock.get("startIndex") >> 0

        when:
        def jsonResult = jsonResponseEnrichHelper.getJsonUserResponseWithAdditionalFields(userList, parameterMapMock)

        then:
        jsonResult.contains("\"totalResults\":1000,\"itemsPerPage\":10,\"startIndex\":0,\"schemas\":\"\",\"Resources\":[]")
    }

    def "should return Json string with additional values for user search"() {
        given:
        def user = new User.Builder("username").setSchemas(["schemas:urn:scim:schemas:core:1.0"] as Set).build()
        def userList = [user] as List<User>
        def parameterMapMock = Mock(Map)

        parameterMapMock.get("count") >> 10
        parameterMapMock.get("startIndex") >> 0

        when:
        def jsonResult = jsonResponseEnrichHelper.getJsonUserResponseWithAdditionalFields(userList, parameterMapMock)

        then:
        jsonResult.contains("\"totalResults\":1000,\"itemsPerPage\":10,\"startIndex\":0,\"schemas\":\"schemas:urn:scim:schemas:core:1.0\",\"Resources\":[{\"schemas\":[\"schemas:urn:scim:schemas:core:1.0\"],\"userName\":\"username\"}]")
    }

    def "should return Json string with additional values for group search"() {
        given:
        def group = new Group.Builder().setSchemas(["schemas:urn:scim:schemas:core:1.0"] as Set).build()
        def groupList = [group] as List<Group>
        def parameterMapMock = Mock(Map)

        parameterMapMock.get("count") >> 10
        parameterMapMock.get("startIndex") >> 0

        when:
        def jsonResult = jsonResponseEnrichHelper.getJsonGroupResponseWithAdditionalFields(groupList, parameterMapMock)

        then:
        jsonResult.contains("\"totalResults\":1000,\"itemsPerPage\":10,\"startIndex\":0,\"schemas\":\"schemas:urn:scim:schemas:core:1.0\",\"Resources\":[{\"schemas\":[\"schemas:urn:scim:schemas:core:1.0\"]}]")
    }

    def "should return Json string with additional values for root search"() {
        given:
        def user = new User.Builder("username").setSchemas(["schemas:urn:scim:schemas:core:1.0"] as Set).build()
        def group = new Group.Builder().setSchemas(["schemas:urn:scim:schemas:core:1.0"] as Set).build()
        def resultList = [user, group] as List<Resource>
        def parameterMapMock = Mock(Map)

        parameterMapMock.get("count") >> 10
        parameterMapMock.get("startIndex") >> 0

        when:
        def jsonResult = jsonResponseEnrichHelper.getJsonRootResponseWithAdditionalFields(resultList, parameterMapMock)

        then:
        jsonResult.contains("\"totalResults\":1000,\"itemsPerPage\":10,\"startIndex\":0,\"schemas\":\"schemas:urn:scim:schemas:core:1.0\",\"Resources\":[{\"schemas\":[\"schemas:urn:scim:schemas:core:1.0\"],\"userName\":\"username\"},{\"schemas\":[\"schemas:urn:scim:schemas:core:1.0\"]}]")
    }
}
