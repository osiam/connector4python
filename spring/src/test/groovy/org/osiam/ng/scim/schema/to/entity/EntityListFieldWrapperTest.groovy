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

import org.osiam.ng.resourceserver.entities.EmailEntity
import org.osiam.ng.resourceserver.entities.UserEntity
import org.osiam.ng.scim.entities.AddressEntity
import org.osiam.ng.scim.entities.EmailEntity
import org.osiam.ng.scim.entities.EntitlementsEntity
import org.osiam.ng.scim.entities.ImEntity
import org.osiam.ng.scim.entities.PhoneNumberEntity
import org.osiam.ng.scim.entities.PhotoEntity
import org.osiam.ng.scim.entities.RolesEntity
import org.osiam.ng.scim.entities.UserEntity
import org.osiam.ng.scim.entities.X509CertificateEntity
import scim.schema.v2.Address
import scim.schema.v2.MultiValuedAttribute
import scim.schema.v2.User
import spock.lang.Specification

class EntityListFieldWrapperTest extends Specification {
    def mode = GenericSCIMToEntityWrapper.Mode.POST
    def "should set entity lists"() {
        given:
        def any = new HashSet()
        any.add("ha")
        def addresses = new User.Addresses()
        addresses.address.add(new Address.Builder().setDisplay("haha").build())
        def emails = new User.Emails()
        emails.email.add(new MultiValuedAttribute.Builder().setPrimary(true).build())
        emails.email.add(new MultiValuedAttribute.Builder().build())

        def entitlements = new User.Entitlements()
        entitlements.entitlement.add(new MultiValuedAttribute.Builder().build())
        entitlements.entitlement.add(new MultiValuedAttribute.Builder().setValue("ha").build())

        def ims = new User.Ims()
        ims.im.add(new MultiValuedAttribute.Builder().build())

        def numbers = new User.PhoneNumbers()
        numbers.phoneNumber.add(new MultiValuedAttribute.Builder().build())

        def photos = new User.Photos()
        photos.photo.add(new MultiValuedAttribute.Builder().build())

        def roles = new User.Roles()
        roles.role.add(new MultiValuedAttribute.Builder().build())
        def certificates = new User.X509Certificates()
        certificates.x509Certificate.add(new MultiValuedAttribute.Builder().build())
        def scimUser = new User.Builder("test").setActive(true)
                .setAddresses(addresses)
                .setEmails(emails)
                .setEntitlements(entitlements)
                .setIms(ims)
                .setPhoneNumbers(numbers)
                .setPhotos(photos)
                .setRoles(roles)
                .setX509Certificates(certificates)
                .build()

        def entity = new UserEntity()
        initializeSetsOfEntity(entity)

        def underTest = new EntityListFieldWrapper(entity, mode)
        def aha = new GetFieldsOfInputAndTarget().getFieldsAsNormalizedMap(UserEntity)


        when:
        underTest.set(addresses, new SCIMEntities.Entity(AddressEntity, false), aha.get("addresses"))
        underTest.set(emails, new SCIMEntities.Entity(EmailEntity, true), aha.get("emails"))
        underTest.set(entitlements, new SCIMEntities.Entity(EntitlementsEntity, true),aha.get("entitlements"))
        underTest.set(ims, new SCIMEntities.Entity(ImEntity, true),aha.get("ims"))
        underTest.set(numbers, new SCIMEntities.Entity(PhoneNumberEntity, true),aha.get("phonenumbers"))
        underTest.set(photos, new SCIMEntities.Entity(PhotoEntity, true),aha.get("photos"))
        underTest.set(roles, new SCIMEntities.Entity(RolesEntity, true),aha.get("roles"))
        underTest.set(certificates, new SCIMEntities.Entity(X509CertificateEntity, true),aha.get("x509certificates"))

        then:
        scimUser.x509Certificates.x509Certificate.size() == entity.x509Certificates.size()
        scimUser.roles.role.size() == entity.roles.size()
        scimUser.entitlements.entitlement.size() == entity.entitlements.size()
        scimUser.ims.im.size() == entity.ims.size()
        scimUser.emails.email.size() == entity.emails.size()
        scimUser.addresses.address.size() == entity.addresses.size()
        scimUser.phoneNumbers.phoneNumber.size() == entity.phoneNumbers.size()
        scimUser.photos.photo.size() == entity.photos.size()
    }

    def "should fail silently if a user is trying to delete an unknown field"(){
        given:

        def entity = new UserEntity()
        initializeSetsOfEntity(entity)
        entity.getEmails().add(new EmailEntity(value: "there"))

        def underTest = new EntityListFieldWrapper(entity, GenericSCIMToEntityWrapper.Mode.PATCH)
        def aha = new GetFieldsOfInputAndTarget().getFieldsAsNormalizedMap(UserEntity)

        def emails = new User.Emails()
        emails.email.add(new MultiValuedAttribute.Builder().setOperation("delete").setValue("notThere").build())
        emails.email.add(new MultiValuedAttribute.Builder().setOperation("delete").setValue("there").build())
        when:
        underTest.set(emails, new SCIMEntities.Entity(EmailEntity, true), aha.get("emails"))
        then:
        entity.getEmails().size() == 0



    }

    private void initializeSetsOfEntity(UserEntity entity) {
        entity.getX509Certificates()
        entity.getAddresses()
        entity.getEmails()
        entity.getEntitlements()
        entity.getIms()
        entity.getPhoneNumbers()
        entity.getPhotos()
        entity.getRoles()
    }

    def "should not set null but empty"() {
        given:

        def entity = new UserEntity()
        def underTest = new EntityListFieldWrapper(entity, mode)
        def aha = new GetFieldsOfInputAndTarget().getFieldsAsNormalizedMap(UserEntity)
        initializeSetsOfEntity(entity)
        when:
        underTest.set(null, new SCIMEntities.Entity(AddressEntity, false), aha.get("addresses"))
        underTest.set(null, new SCIMEntities.Entity(EmailEntity, true), aha.get("emails"))
        underTest.set(null, new SCIMEntities.Entity(EntitlementsEntity, true),aha.get("entitlements"))
        underTest.set(null, new SCIMEntities.Entity(ImEntity, true),aha.get("ims"))
        underTest.set(null, new SCIMEntities.Entity(PhoneNumberEntity, true),aha.get("phonenumbers"))
        underTest.set(null, new SCIMEntities.Entity(PhotoEntity, true),aha.get("photos"))
        underTest.set(null, new SCIMEntities.Entity(RolesEntity, true),aha.get("roles"))
        underTest.set(null, new SCIMEntities.Entity(X509CertificateEntity, true),aha.get("x509certificates"))



        then:
        entity.x509Certificates.size() == 0
        entity.roles.size() == 0
        entity.entitlements.size() == 0
        entity.ims.size() == 0
        entity.emails.size() == 0
        entity.addresses.size() == 0
        entity.phoneNumbers.size() == 0
        entity.photos.size() == 0
    }

    def "should replace attributes of a multi-value-attribute list"() {
        def emails = new User.Emails()
        emails.email.add(new MultiValuedAttribute.Builder().setValue("email").setPrimary(true).setType("home").build())
        emails.email.add(new MultiValuedAttribute.Builder().setValue("email3").setPrimary(true).setType("home").build())

        def aha = new GetFieldsOfInputAndTarget().getFieldsAsNormalizedMap(UserEntity)
        def entity = GenericSCIMToEntityWrapperTest.createEntityWithInternalId()
        GenericSCIMToEntityWrapperTest.addListsToEntity(entity)
        entity.getEmails().add(new EmailEntity(value: "email", type: "work", primary: false))
        entity.getEmails().add(new EmailEntity(value: "email2", type: "work", primary: false))
        def underTest = new EntityListFieldWrapper(entity, GenericSCIMToEntityWrapper.Mode.PATCH)
        when:
        underTest.set(emails, new SCIMEntities.Entity(EmailEntity, true), aha.get("emails"))
        then:

        entity.getEmails().size() == 3

        for (EmailEntity e : entity.getEmails()) {
            if (e.getValue() == "email" || e.getValue() == "email3") {
                e.primary == true
                e.type == "home"
            } else {
                e.primary == false
                e.type == "work"
            }
        }
    }

    def "should wrap InstantiationException to IllegalStateException"(){
        given:
        def attr = Mock(SCIMEntities.Entity)
        attr.isMultiValue() >> {throw new InstantiationException("moep")}
        def underTest = new EntityListFieldWrapper(null, GenericSCIMToEntityWrapper.Mode.PATCH)
        when:
        underTest.wrapExceptions("test", attr, null)
        then:
        def e = thrown(IllegalStateException)
        e.message == "java.lang.InstantiationException: moep"
    }

    def "should wrap IllegalAccessException to IllegalStateException"(){
        given:
        def attr = Mock(SCIMEntities.Entity)
        attr.isMultiValue() >> {throw new IllegalAccessException("moep")}
        def underTest = new EntityListFieldWrapper(null, GenericSCIMToEntityWrapper.Mode.PATCH)
        when:
        underTest.wrapExceptions("test", attr, null)
        then:
        def e = thrown(IllegalStateException)
        e.message == "java.lang.IllegalAccessException: moep"
    }


    // | IllegalAccessException


}
