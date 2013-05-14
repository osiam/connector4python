package org.osiam.ng.scim.mvc.user

import org.osiam.ng.scim.dao.SCIMRootProvisioning
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import java.lang.reflect.Method

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 07.05.13
 * Time: 09:48
 * To change this template use File | Settings | File Templates.
 */
class RootControllerSpec extends Specification{

    def provisioning = Mock(SCIMRootProvisioning)
    def underTest = new RootController(scimRootProvisioning: provisioning)

    def "should be able to search a resource on / URI with GET method" () {
        given:
        Method method = RootController.class.getDeclaredMethod("searchWithGet", HttpServletRequest)
        def servletRequestMock = Mock(HttpServletRequest)
        servletRequestMock.getParameter("filter") >> "filter"

        when:
        RequestMapping mapping = method.getAnnotation(RequestMapping)
        ResponseBody body = method.getAnnotation(ResponseBody)
        underTest.searchWithGet(servletRequestMock)

        then:
        mapping.value() == []
        mapping.method() == [RequestMethod.GET]
        body
        1* provisioning.search("filter")
    }

    def "should be able to search a resource on /.search URI with POST method" () {
        given:
        Method method = RootController.class.getDeclaredMethod("searchWithPost", HttpServletRequest)
        def servletRequestMock = Mock(HttpServletRequest)
        servletRequestMock.getParameter("filter") >> "filter"

        when:
        RequestMapping mapping = method.getAnnotation(RequestMapping)
        ResponseBody body = method.getAnnotation(ResponseBody)
        underTest.searchWithPost(servletRequestMock)

        then:
        mapping.value() == [".search"]
        mapping.method() == [RequestMethod.POST]
        body
        1* provisioning.search("filter")
    }
}