package org.osiam.ng.resourceserver.dao

import org.osiam.ng.resourceserver.entities.UserEntity
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
    def entityToScimMapper = Mock(EntityToScimMapper)
    def scimToEntityMapper = Mock(ScimToEntityMapper)
    def userEntity = Mock(UserEntity)
    def scimUser = Mock(User)
    ScimUserProvisioningBean scimUserProvisioningBean =
        new ScimUserProvisioningBean(userDao: userDao, entityToScimMapper: entityToScimMapper, scimToEntityMapper: scimToEntityMapper)


    def "should be possible to get an user by his id"() {
        given:
        userDao.getById("1234") >> userEntity
        entityToScimMapper.entityUserToScim(userEntity) >> scimUser

        when:
        def user = scimUserProvisioningBean.getById("1234")

        then:
        user == scimUser
    }

    def "should be possible to create a user"() {
        given:
        scimToEntityMapper.scimUserToEntity(scimUser) >> userEntity
        userDao.createUser(userEntity) >> userEntity
        entityToScimMapper.entityUserToScim(userEntity) >> scimUser

        when:
        def user = scimUserProvisioningBean.createUser(scimUser)

        then:
        user == scimUser
    }
}