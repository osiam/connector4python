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

package org.osiam.ng.resourceserver.dao

import org.osiam.ng.resourceserver.entities.UserEntity
import scim.schema.v2.Address
import scim.schema.v2.Meta
import scim.schema.v2.MultiValuedAttribute
import scim.schema.v2.Name
import scim.schema.v2.User
import spock.lang.Specification

class SetUserListFieldsTest extends Specification {
    def "should set entity lists"() {
        given:
        def any = new HashSet()
        any.add("ha")
        def addresses = new User.Addresses()
        addresses.address.add(new Address.Builder().setDisplay("haha").build())
        def emails = new User.Emails()
        emails.email.add(new MultiValuedAttribute.Builder().build())
        emails.email.add(new MultiValuedAttribute.Builder().build())

        def entitlements = new User.Entitlements()
        entitlements.entitlement.add(new MultiValuedAttribute.Builder().build())
        entitlements.entitlement.add(new MultiValuedAttribute.Builder().build())

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
        def underTest = new SetUserListFields(entity)
        when:
        underTest.updateListFields(addresses, SetUserFields.UserLists.ADDRESSES)
        underTest.updateListFields(emails, SetUserFields.UserLists.EMAILS)
        underTest.updateListFields(entitlements, SetUserFields.UserLists.ENTITLEMENTS)
        underTest.updateListFields(ims, SetUserFields.UserLists.IMS)
        underTest.updateListFields(numbers, SetUserFields.UserLists.PHONENUMBERS)
        underTest.updateListFields(photos, SetUserFields.UserLists.PHOTOS)
        underTest.updateListFields(roles, SetUserFields.UserLists.ROLES)
        underTest.updateListFields(certificates, SetUserFields.UserLists.X509)


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

    def "should not set null but empty"() {
        given:

        def entity = new UserEntity()
        def underTest = new SetUserListFields(entity)
        when:
        underTest.updateListFields(null, SetUserFields.UserLists.ADDRESSES)
        underTest.updateListFields(null, SetUserFields.UserLists.EMAILS)
        underTest.updateListFields(null, SetUserFields.UserLists.ENTITLEMENTS)
        underTest.updateListFields(null, SetUserFields.UserLists.IMS)
        underTest.updateListFields(null, SetUserFields.UserLists.PHONENUMBERS)
        underTest.updateListFields(null, SetUserFields.UserLists.PHOTOS)
        underTest.updateListFields(null, SetUserFields.UserLists.ROLES)
        underTest.updateListFields(null, SetUserFields.UserLists.X509)


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
}
