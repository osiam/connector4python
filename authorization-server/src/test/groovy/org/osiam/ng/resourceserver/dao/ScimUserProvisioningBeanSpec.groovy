package org.osiam.ng.resourceserver.dao

import org.osiam.ng.resourceserver.entities.UserEntity
import org.osiam.ng.scim.exceptions.ResourceExistsException
import scim.schema.v2.User
import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 19.03.13
 * Time: 17:51
 * To change this template use File | Settings | File Templates.
 */
class ScimUserProvisioningBeanSpec extends Specification {


    def userDao = Mock(UserDAO)
    def userEntity = Mock(UserEntity)
    def scimUser = Mock(User)
    ScimUserProvisioningBean scimUserProvisioningBean =
        new ScimUserProvisioningBean(userDao: userDao)


    def "should be possible to get an user by his id"() {
        given:
        userDao.getById("1234") >> userEntity
        userEntity.toScim() >> scimUser

        when:
        def user = scimUserProvisioningBean.getById("1234")

        then:
        user == scimUser
    }

    def "should be possible to create a user"() {
        given:
        userEntity.toScim() >> scimUser

        when:
        def user = scimUserProvisioningBean.createUser(scimUser)

        then:
        user == scimUser
    }

    def "should throw exception if user already exists"() {
        given:
        userDao.createUser(_) >> {throw new Exception()}
        scimUser.getUserName() >> "Bäm"

        when:
        scimUserProvisioningBean.createUser(scimUser)

        then:
        def e = thrown(ResourceExistsException)
        e.getMessage() == "The user with name Bäm already exists."
    }
}