package org.osiam.ng.scim.mvc.user

import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.05.13
 * Time: 13:54
 * To change this template use File | Settings | File Templates.
 */
class RequestParamHelperSpec extends Specification {

    def servletRequestMock = Mock(HttpServletRequest)
    def requestParamHelper = new RequestParamHelper()

    def "should exist default value if sortBy is null"() {
        given:
        servletRequestMock.getParameter("filter") >> "someFilter"
        servletRequestMock.getParameter("sortBy") >> null
        servletRequestMock.getParameter("sortOrder") >> "someSortOrder"
        servletRequestMock.getParameter("startIndex") >> "1"
        servletRequestMock.getParameter("count") >> "10"

        when:
        Map result = requestParamHelper.getRequestParameterValues(servletRequestMock)

        then:
        result.get("sortBy") == "internal_id"
    }

    def "should not use default value for sortBy if not null"() {
        given:
        servletRequestMock.getParameter("filter") >> "someFilter"
        servletRequestMock.getParameter("sortBy") >> "userName"
        servletRequestMock.getParameter("sortOrder") >> "someSortOrder"
        servletRequestMock.getParameter("startIndex") >> "1"
        servletRequestMock.getParameter("count") >> "10"

        when:
        Map result = requestParamHelper.getRequestParameterValues(servletRequestMock)

        then:
        result.get("sortBy") == "userName"
    }

    def "should exist default value if sortOrder is null"() {
        given:
        servletRequestMock.getParameter("filter") >> "someFilter"
        servletRequestMock.getParameter("sortBy") >> "userName"
        servletRequestMock.getParameter("sortOrder") >> null
        servletRequestMock.getParameter("startIndex") >> "1"
        servletRequestMock.getParameter("count") >> "10"

        when:
        Map result = requestParamHelper.getRequestParameterValues(servletRequestMock)

        then:
        result.get("sortOrder") == "ascending"
    }

    def "should not use default value for sortOrder if not null"() {
        given:
        servletRequestMock.getParameter("filter") >> "someFilter"
        servletRequestMock.getParameter("sortBy") >> "userName"
        servletRequestMock.getParameter("sortOrder") >> "descending"
        servletRequestMock.getParameter("startIndex") >> "1"
        servletRequestMock.getParameter("count") >> "10"

        when:
        Map result = requestParamHelper.getRequestParameterValues(servletRequestMock)

        then:
        result.get("sortOrder") == "descending"
    }

    def "should exist default value if startIndex is null"() {
        given:
        servletRequestMock.getParameter("filter") >> "someFilter"
        servletRequestMock.getParameter("sortBy") >> "userName"
        servletRequestMock.getParameter("sortOrder") >> "descending"
        servletRequestMock.getParameter("startIndex") >> null
        servletRequestMock.getParameter("count") >> "10"

        when:
        Map result = requestParamHelper.getRequestParameterValues(servletRequestMock)

        then:
        result.get("startIndex") == 0
    }

    def "should not use default value for startIndex if not null"() {
        given:
        servletRequestMock.getParameter("filter") >> "someFilter"
        servletRequestMock.getParameter("sortBy") >> "userName"
        servletRequestMock.getParameter("sortOrder") >> "descending"
        servletRequestMock.getParameter("startIndex") >> "11"
        servletRequestMock.getParameter("count") >> "10"

        when:
        Map result = requestParamHelper.getRequestParameterValues(servletRequestMock)

        then:
        result.get("startIndex") == 11
    }

    def "should exist default value if count is null"() {
        given:
        servletRequestMock.getParameter("filter") >> "someFilter"
        servletRequestMock.getParameter("sortBy") >> "userName"
        servletRequestMock.getParameter("sortOrder") >> "descending"
        servletRequestMock.getParameter("startIndex") >> "11"
        servletRequestMock.getParameter("count") >> null

        when:
        Map result = requestParamHelper.getRequestParameterValues(servletRequestMock)

        then:
        result.get("count") == 100
    }

    def "should not use default value for count if not null"() {
        given:
        servletRequestMock.getParameter("filter") >> "someFilter"
        servletRequestMock.getParameter("sortBy") >> "userName"
        servletRequestMock.getParameter("sortOrder") >> "descending"
        servletRequestMock.getParameter("startIndex") >> "11"
        servletRequestMock.getParameter("count") >> "10"

        when:
        Map result = requestParamHelper.getRequestParameterValues(servletRequestMock)

        then:
        result.get("count") == 10
    }

    def "should throw exception if count values are negative"() {
        given:
        servletRequestMock.getParameter("filter") >> "someFilter"
        servletRequestMock.getParameter("sortBy") >> "userName"
        servletRequestMock.getParameter("sortOrder") >> "descending"
        servletRequestMock.getParameter("startIndex") >> "11"
        servletRequestMock.getParameter("count") >> "-10"

        when:
        requestParamHelper.getRequestParameterValues(servletRequestMock)

        then:
        def e = thrown(RuntimeException)
        e.getMessage() == "Negative count values are not allowed"
    }
}
