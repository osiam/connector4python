package org.osiam.ng.resourceserver.dao

import org.osiam.ng.resourceserver.entities.UserEntity
import org.osiam.ng.scim.exceptions.ResourceExistsException
import org.osiam.ng.scim.schema.to.entity.GenericSCIMToEntityWrapper
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
    SCIMUserProvisioningBean scimUserProvisioningBean = new SCIMUserProvisioningBean(userDao: userDao)


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
        def user = scimUserProvisioningBean.create(scimUser)

        then:
        user.userName == "test"
        user.id ==~ "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"
    }

    def "should throw exception if user already exists"() {
        given:
        userDao.create(_) >> { throw new Exception() }
        scimUser.getUserName() >> "Bäm"

        when:
        scimUserProvisioningBean.create(scimUser)

        then:
        def e = thrown(ResourceExistsException)
        e.getMessage() == "The user with name Bäm already exists."
    }

    def "should get an user before update, set the expected fields, merge the result"() {
        given:
        def internalId = UUID.randomUUID()
        def scimUser = new User.Builder("test").build()
        def entity = new UserEntity()
        entity.setId(internalId)

        when:
        scimUserProvisioningBean.replace(internalId.toString(), scimUser)
        then:
        1 * userDao.getById(internalId.toString()) >> entity
        1 * userDao.update(entity)
    }

    def "should wrap IllegalAccessEsception to an IllegalState"() {
        given:
        GenericSCIMToEntityWrapper setUserFields = Mock(GenericSCIMToEntityWrapper)

        when:
        scimUserProvisioningBean.setFieldsWrapException(setUserFields);
        then:
        1 * setUserFields.setFields() >> { throw new IllegalAccessException("Blubb") }
        def e = thrown(IllegalStateException)
        e.message == "This should not happen."

    }

    def "should call dao delete on delete"() {
        given:
        def id = UUID.randomUUID().toString()
        when:
        scimUserProvisioningBean.delete(id)
        then:
        1 * userDao.delete(id)
    }

    def "should call dao search on search"() {
        given:
        def scimSearchResultMock = Mock(SCIMSearchResult)
        userDao.search("anyFilter", "userName", "ascending", 100, 1) >> scimSearchResultMock

        def userEntityMock = Mock(UserEntity)
        def user = Mock(User)
        def userList = [userEntityMock] as List
        scimSearchResultMock.getResult() >> userList
        scimSearchResultMock.getTotalResult() >> 1000.toLong()

        when:
        def result = scimUserProvisioningBean.search("anyFilter", "userName", "ascending", 100, 1)

        then:
        result != null
        result.getTotalResult() == 1000.toLong()
    }
}