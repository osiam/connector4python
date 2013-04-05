package org.osiam.ng.resourceserver

import org.osiam.ng.resourceserver.dao.ScimUserProvisioningBean
import org.osiam.ng.resourceserver.dao.UserDAO
import org.osiam.ng.resourceserver.entities.GroupEntity
import org.osiam.ng.resourceserver.entities.NameEntity
import org.osiam.ng.resourceserver.entities.UserEntity
import scim.schema.v2.Meta
import scim.schema.v2.MultiValuedAttribute
import scim.schema.v2.Name
import scim.schema.v2.User
import spock.lang.Ignore
import spock.lang.Specification


class UserPatchTest extends Specification {
    def userDao = Mock(UserDAO)
    ScimUserProvisioningBean bean = new ScimUserProvisioningBean(userDao: userDao)
    def uId = UUID.randomUUID()
    def id = uId.toString()


    @Ignore("In progress ..")
    def "should delete single attribute of a multi-value-attribute list"() { when: true; then: false; }
    @Ignore("In progress ..")
    def "should delete all attributes of a multi-value-attribute list"() { when: true; then: false; }
    @Ignore("In progress ..")
    def "should replace attributes of a multi-value-attribute list"() { when: true; then: false; }
    @Ignore("In progress ..")
    def "should delete and add a value to a multi-value-attribute list"() { when: true; then: false; }
    @Ignore("In progress ..")
    def "should replace a non Sub-Attribute able attribute of an user (e.q. addresses)"() { when: true; then: false; }
    @Ignore("In progress ..")
    def "should update a single attribute"() {
        given:

        def user = new User.Builder("username").setDisplayName("hallo").build()
        UserEntity entity = createEntityWithInternalId()
        when:
        bean.updateUser(id, user)
        then:
        1 * userDao.getById(id) >> entity
        entity.username == "username"
        entity.displayName == "hallo"
        1 * userDao.update(entity)
    }

    private UserEntity createEntityWithInternalId() {
        def entity = new UserEntity()
        entity.internalId = uId
        entity
    }



    def "should delete simple attributes"() {
        given:
        def meta = new Meta.Builder(null, null).setAttributes(["displayName"] as Set).build()
        def user = new User.Builder().setMeta(meta).build()
        UserEntity entity = createEntityWithInternalId()
        entity.setUsername("username")
        entity.setDisplayName("display it")
        when:
        bean.updateUser(id, user)
        then:
        1 * userDao.getById(id) >> entity
        entity.username == "username"
        entity.displayName == null
        1 * userDao.update(entity)

    }

    def "should ignore when trying to delete required parameters"(){
        given:
        def meta = new Meta.Builder(null, null).setAttributes(["userName"] as Set).build()
        def user = new User.Builder().setMeta(meta).build()
        UserEntity entity = createEntityWithInternalId()
        entity.setUsername("username")
        when:
        bean.updateUser(id, user)
        then:
        1 * userDao.getById(id) >> entity
        entity.username == "username"
    }

    def "should delete and update simple attributes"() {
        given:
        def meta = new Meta.Builder(null, null).setAttributes(["displayName"] as Set).build()

        def user = new User.Builder("Harald").setMeta(meta).build()
        UserEntity entity = createEntityWithInternalId()
        entity.setUsername("hach")
        entity.setDisplayName("display it")
        when:
        bean.updateUser(id, user)
        then:
        1 * userDao.getById(id) >> entity
        entity.username == "Harald"
        entity.displayName == null
        1 * userDao.update(entity)

    }

    def "should ignore update when simple-attribute is in meta"() {
        given:
        def meta = new Meta.Builder(null, null).setAttributes(["displayName"] as Set).build()

        def user = new User.Builder().setDisplayName("don't display that").setMeta(meta).build()
        UserEntity entity = createEntityWithInternalId()
        entity.setUsername("Harald")
        entity.setDisplayName("display it")
        when:
        bean.updateUser(id, user)
        then:
        1 * userDao.getById(id) >> entity
        entity.username == "Harald"
        entity.displayName == null
        1 * userDao.update(entity)
    }

    def "should update parts of an complex attribute"() {
        given:
        def user = new User.Builder().setName(new Name.Builder().setMiddleName("FNORD").build()).build()
        UserEntity entity = createEntityWithInternalId()
        def name = new NameEntity()
        name.setGivenName("Arthur")
        name.setFamilyName("Dent")
        name.setFormatted("Dent Arthur")
        name.honorificPrefix = "a"
        name.honorificSuffix = "b"
        name.setMiddleName('(")(°v°)(")')
        entity.setName(name)
        entity.setUsername("username")

        when:
        bean.updateUser(id, user)
        then:
        1 * userDao.getById(id) >> entity
        entity.name.givenName == "Arthur"
        entity.name.familyName == "Dent"
        entity.name.middleName == "FNORD"
        entity.name.formatted == "Dent Arthur"
        entity.name.honorificPrefix == "a"
        entity.name.honorificSuffix == "b"
    }

    def "should remove parts of an complex attribute"() {
        given:
        def meta = new Meta.Builder(null, null).setAttributes(["name.formatted"] as Set).build()
        def user = new User.Builder().setMeta(meta).build()
        UserEntity entity = createEntityWithInternalId()
        def name = new NameEntity()
        name.setGivenName("Arthur")
        name.setFamilyName("Dent")
        name.setFormatted("Dent Arthur")
        name.honorificPrefix = "a"
        name.honorificSuffix = "b"
        name.setMiddleName('(")(°v°)(")')
        entity.setName(name)
        entity.setUsername("username")

        when:
        bean.updateUser(id, user)
        then:
        1 * userDao.getById(id) >> entity
        entity.name.givenName == "Arthur"
        entity.name.familyName == "Dent"
        entity.name.middleName == '(")(°v°)(")'
        entity.name.formatted == null
        entity.name.honorificPrefix == "a"
        entity.name.honorificSuffix == "b"
    }

    def "should ignore read-only attributes on modify"() {
        given:
        def user = new User.Builder().setGroups(new User.Groups()).build()
        user.getGroups().group.add(new MultiValuedAttribute.Builder().build())
        UserEntity entity = createEntityWithInternalId()
        entity.setUsername("username")

        when:
        bean.updateUser(id, user)
        then:
        1 * userDao.getById(id) >> entity
        entity.groups == [] as Set
    }

    def "should ignore read-only attributes on delete"() {
        given:
        def attributes = ["groups"] as Set
        Meta meta = new Meta.Builder(null, null).setAttributes(attributes).build();
        def user = new User.Builder().setMeta(meta).build()
        UserEntity entity = createEntityWithInternalId()

        entity.setUsername("username")
        entity.groups.add(new GroupEntity())

        when:
        bean.updateUser(id, user)
        then:
        1 * userDao.getById(id) >> entity
        entity.groups.size() == 1
    }



}
