/*
 * Copyright (C) 2013 tarent AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.osiam.ng.scim.schema.to.entity

import org.osiam.ng.scim.entities.AddressEntity
import org.osiam.ng.scim.entities.EmailEntity
import org.osiam.ng.scim.entities.EntitlementsEntity
import org.osiam.ng.scim.entities.ImEntity
import org.osiam.ng.scim.entities.NameEntity
import org.osiam.ng.scim.entities.PhoneNumberEntity
import org.osiam.ng.scim.entities.PhotoEntity
import org.osiam.ng.scim.entities.RolesEntity
import org.osiam.ng.scim.entities.UserEntity
import org.osiam.ng.scim.entities.X509CertificateEntity
import scim.schema.v2.Meta
import scim.schema.v2.MultiValuedAttribute
import scim.schema.v2.Name
import scim.schema.v2.User
import spock.lang.Specification

class FakeSCIMEntities implements SCIMEntities {
    public static final FakeSCIMEntities ENTITIES = new FakeSCIMEntities();
    def fromString = new HashMap<>();


    private FakeSCIMEntities() {
        putEntity("emails", EmailEntity.class, true);
        putEntity("ims", ImEntity.class, true);
        putEntity("phonenumbers", PhoneNumberEntity.class, true);
        putEntity("photos", PhotoEntity.class, true);
        putEntity("entitlements", EntitlementsEntity.class, true);
        putEntity("roles", RolesEntity.class, true);
        putEntity("x509certificates", X509CertificateEntity.class, true);
        putEntity("addresses", AddressEntity.class, false);
    }

    def putEntity(String key, Class<?> clazz, boolean multiValue) {
        fromString.put(key, new SCIMEntities.Entity(clazz, multiValue));
    }

    @Override
    public SCIMEntities.Entity fromString(String key) {
        return fromString.get(key);
    }
}

class GenericSCIMToEntityWrapperTest extends Specification {

    def userTarget = GenericSCIMToEntityWrapper.For.USER

    def "should set an entity to given user attributes"() {
        given:
        def any = new HashSet()
        any.add("ha")
        def scimUser = new User.Builder("test").setActive(true)
                .setAddresses(new User.Addresses())
                .setAny(any)
                .setDisplayName("display")
                .setEmails(new User.Emails())
                .setEntitlements(new User.Entitlements())
                .setGroups(new User.Groups())
                .setIms(new User.Ims())
                .setLocale("locale")
                .setName(new Name.Builder().build())
                .setNickName("nickname")
                .setPassword("password")
                .setPhoneNumbers(new User.PhoneNumbers())
                .setPhotos(new User.Photos())
                .setPreferredLanguage("prefereedLanguage")
                .setProfileUrl("profileUrl")
                .setRoles(new User.Roles())
                .setTimezone("time")
                .setTitle("title")
                .setUserType("userType")
                .setX509Certificates(new User.X509Certificates())
                .setExternalId("externalid")
                .setId("id")
                .setMeta(new Meta.Builder().build())
                .build()

        def entity = new UserEntity()

        def underTest = new GenericSCIMToEntityWrapper(userTarget, scimUser, entity, GenericSCIMToEntityWrapper.Mode.PUT, FakeSCIMEntities.ENTITIES)
        when:
        underTest.setFields()


        then:
        scimUser.active == entity.active
        scimUser.any == entity.any
        scimUser.displayName == entity.displayName
        scimUser.locale == entity.locale
        scimUser.name.familyName == entity.name.familyName
        scimUser.nickName == entity.nickName
        scimUser.password == entity.password
        scimUser.preferredLanguage == entity.preferredLanguage
        scimUser.profileUrl == entity.profileUrl
        scimUser.timezone == entity.timezone
        scimUser.title == entity.title
        scimUser.userType == entity.userType
        scimUser.userName == entity.username
        scimUser.externalId == entity.externalId

        scimUser.x509Certificates.x509Certificate.size() == entity.x509Certificates.size()
        scimUser.roles.role.size() == entity.roles.size()
        scimUser.entitlements.entitlement.size() == entity.entitlements.size()
        !entity.groups
        scimUser.ims.im.size() == entity.ims.size()
        scimUser.emails.email.size() == entity.emails.size()
        scimUser.addresses.address.size() == entity.addresses.size()
        scimUser.phoneNumbers.phoneNumber.size() == entity.phoneNumbers.size()
        scimUser.photos.photo.size() == entity.photos.size()
    }

    def "should delete meta parameter when in PATCh mode"(){
        given:
        def meta = new Meta.Builder(null, null).setAttributes(["displayName"] as Set).build()

        def user = new User.Builder("Harald").setMeta(meta).build()

        UserEntity entity = createEntityWithInternalId()
        entity.setUsername("hach")
        entity.setDisplayName("display it")
        def underTest = new GenericSCIMToEntityWrapper(userTarget, user, entity, GenericSCIMToEntityWrapper.Mode.PATCH, FakeSCIMEntities.ENTITIES)
        when:
        underTest.setFields()
        then:
        entity.username == "Harald"
        entity.displayName == null
    }

    static UserEntity createEntityWithInternalId() {
        def entity = new UserEntity()
        entity.internalId = UUID.randomUUID()
        entity
    }


    def "should not delete meta parameter when they're required"(){
        given:
        def meta = new Meta.Builder(null, null).setAttributes(["userName"] as Set).build()

        def user = new User.Builder().setMeta(meta).build()

        UserEntity entity = createEntityWithInternalId()
        entity.setUsername("hach")
        entity.setDisplayName("display it")
        def underTest = new GenericSCIMToEntityWrapper(userTarget, user, entity, GenericSCIMToEntityWrapper.Mode.PATCH, FakeSCIMEntities.ENTITIES)
        when:
        underTest.setFields()
        then:
        entity.username == "hach"
    }

    def "should delete single attribute of a multi-value-attribute list in PATCH"() {
        def emails = new User.Emails()
        emails.email.add(new MultiValuedAttribute.Builder().setValue("email").setOperation("delete").build())

        def entitlements = new User.Entitlements()
        entitlements.entitlement.add(new MultiValuedAttribute.Builder().setValue("entitlement").setOperation("delete").build())

        def ims = new User.Ims()
        ims.im.add(new MultiValuedAttribute.Builder().setValue("im").setOperation("delete").build())

        def numbers = new User.PhoneNumbers()
        numbers.phoneNumber.add(new MultiValuedAttribute.Builder().setValue("phonenumber").setOperation("delete").build())

        def photos = new User.Photos()
        photos.photo.add(new MultiValuedAttribute.Builder().setValue("photo").setOperation("delete").build())

        def roles = new User.Roles()
        roles.role.add(new MultiValuedAttribute.Builder().setValue("role").setOperation("delete").build())
        def certificates = new User.X509Certificates()
        certificates.x509Certificate.add(new MultiValuedAttribute.Builder().setValue("x509").setOperation("delete").build())
        def user = new User.Builder("test").setActive(true)
                .setEmails(emails)
                .setEntitlements(entitlements)
                .setIms(ims)
                .setPhoneNumbers(numbers)
                .setPhotos(photos)
                .setRoles(roles)
                .setX509Certificates(certificates)
                .build()

        def entity = createEntityWithInternalId()
        addListsToEntity(entity)
        entity.getEmails().add(new EmailEntity(value: "email2", type: "work", primary: false))
        def underTest = new GenericSCIMToEntityWrapper(userTarget, user, entity, GenericSCIMToEntityWrapper.Mode.PATCH, FakeSCIMEntities.ENTITIES)

        when:
        underTest.setFields()
        then:
        entity.getX509Certificates().empty
        !entity.getAddresses().empty
        entity.getEmails().size() == 1
        entity.getEntitlements().empty
        entity.getIms().empty
        entity.getPhoneNumbers().empty
        entity.getPhotos().empty
        entity.getRoles().empty
    }

    static addListsToEntity(UserEntity entity) {
        entity.getX509Certificates().add(new X509CertificateEntity(value: "x509"))
        entity.getAddresses().add(new AddressEntity())
        entity.getEmails().add(new EmailEntity(value: "email", type: "work", primary: false))
        entity.getEntitlements().add(new EntitlementsEntity(value: "entitlement"))
        entity.getIms().add(new ImEntity(value: "im", type: "work"))
        entity.getPhoneNumbers().add(new PhoneNumberEntity(value: "phonenumber", type: "work"))
        entity.getPhotos().add(new PhotoEntity(value: "photo", type: "work"))
        entity.getRoles().add(new RolesEntity(value: "role"))
    }

    def "should update parts of an complex attribute in PATCH mode"() {
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
        def underTest = new GenericSCIMToEntityWrapper(userTarget, user, entity, GenericSCIMToEntityWrapper.Mode.PATCH, FakeSCIMEntities.ENTITIES)

        when:
        underTest.setFields()
        then:
        entity.name.givenName == "Arthur"
        entity.name.familyName == "Dent"
        entity.name.middleName == "FNORD"
        entity.name.formatted == "Dent Arthur"
        entity.name.honorificPrefix == "a"
        entity.name.honorificSuffix == "b"
    }

    def "should remove parts of an complex attribute PATCH mode"() {
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
        def underTest = new GenericSCIMToEntityWrapper(userTarget, user, entity, GenericSCIMToEntityWrapper.Mode.PATCH, FakeSCIMEntities.ENTITIES)
        when:
        underTest.setFields()
        then:
        entity.name.givenName == "Arthur"
        entity.name.familyName == "Dent"
        entity.name.middleName == '(")(°v°)(")'
        entity.name.formatted == null
        entity.name.honorificPrefix == "a"
        entity.name.honorificSuffix == "b"
    }

    def "should ignore update in PATCH when the complex attribute is read only"() {
        given:
        def meta = new Meta.Builder(null, null).setAttributes(["id.test"] as Set).build()
        def user = new User.Builder().setMeta(meta).build()
        UserEntity entity = createEntityWithInternalId()
        entity.setUsername("haha")
        def underTest = new GenericSCIMToEntityWrapper(userTarget, user, entity, GenericSCIMToEntityWrapper.Mode.PATCH, FakeSCIMEntities.ENTITIES)

        when:
        underTest.setFields()
        then:
        //should be thrown if it would continue a read only field, because UUID has no field test ...
        notThrown(NullPointerException)

    }






}
