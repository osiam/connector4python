package org.osiam.ng.resourceserver.dao

import org.osiam.ng.resourceserver.entities.UserEntity
import org.osiam.ng.scim.exceptions.ResourceExistsException
import org.osiam.ng.scim.schema.to.entity.SetUserFields
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

    def "should be possible to create a user with generated UUID as internalId"() {
        given:
        def scimUser = new User.Builder("test").build()

        when:
        def user = scimUserProvisioningBean.createUser(scimUser)

        then:
        user.userName == "test"
        user.id ==~ "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"
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

    def "should get an user before update, set the expected fields, merge the result"() {
        given:
        def internalId = UUID.randomUUID()
        def scimUser = new User.Builder("test").build()
        def entity = new UserEntity()
        entity.setInternalId(internalId)

        when:
        scimUserProvisioningBean.replaceUser(internalId.toString(), scimUser)
        then:
        1 * userDao.getById(internalId.toString()) >> entity
        1 * userDao.update(entity)
    }

    def "should wrap IllegalAccessEsception to an IllegalState"() {
        given:
        SetUserFields setUserFields = Mock(SetUserFields)

        when:
        scimUserProvisioningBean.setUserFieldsWrapException(setUserFields);
        then:
        1 * setUserFields.setFields() >> {throw new IllegalAccessException("Blubb")}
        def e = thrown(IllegalStateException)
        e.message == "This should not happen."

    }
}