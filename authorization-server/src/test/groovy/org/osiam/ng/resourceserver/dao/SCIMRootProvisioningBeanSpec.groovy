package org.osiam.ng.resourceserver.dao

import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 13.05.13
 * Time: 13:17
 * To change this template use File | Settings | File Templates.
 */
class SCIMRootProvisioningBeanSpec extends Specification {

    def rootDao = Mock(RootDAO)
    def scimRootProvisioningBean = new SCIMRootProvisioningBean(rootDAO: rootDao)

    def "should call dao search on search"() {
        when:
        scimRootProvisioningBean.search("anyFilter")

        then:
        1 * rootDao.search("anyFilter")
    }
}
