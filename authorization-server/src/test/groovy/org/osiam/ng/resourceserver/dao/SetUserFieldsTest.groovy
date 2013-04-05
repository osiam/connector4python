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
import scim.schema.v2.Meta
import scim.schema.v2.Name
import scim.schema.v2.User
import spock.lang.Specification

class SetUserFieldsTest extends Specification {

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
        def underTest = new SetUserFields(scimUser, entity, SetUserFields.Mode.POST)
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
        def underTest = new SetUserFields(user, entity, SetUserFields.Mode.PATCH)
        when:
        underTest.setFields()
        then:
        entity.username == "Harald"
        entity.displayName == null
    }

    private UserEntity createEntityWithInternalId() {
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
        def underTest = new SetUserFields(user, entity, SetUserFields.Mode.PATCH)
        when:
        underTest.setFields()
        then:
        entity.username == "hach"
    }



}
