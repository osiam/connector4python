package org.osiam.oauth2.client

import scim.schema.v2.MultiValuedAttribute
import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 4/8/13
 * Time: 7:37 PM
 * To change this template use File | Settings | File Templates.
 */
class JsonStringGeneratorTest extends Specification {
    def "should not set name when no first nor lastname got submitted"() {
        when:
        def result = JsonStringGenerator.getJsonString(
                "schema",
                "user_name",
                null,
                null,
                "displayname",
                "nickname",
                "profileurl",
                "title",
                "usertype",
                "preferredlanguage",
                "locale",
                "timezone",
                //timezone
                "password"
        )

        then:
        result == '{"schemas":["schema"],"userName":"user_name","displayName":"displayname","nickName":"nickname","profileUrl":"profileurl","title":"title","userType":"usertype","preferredLanguage":"preferredlanguage","locale":"locale","timezone":"timezone","password":"password","emails":{},"phoneNumbers":{},"ims":{},"photos":{},"groups":{},"entitlements":{},"roles":{},"x509Certificates":{}}'
    }

    def "should return scim.schema.v2.Constants schema when no schema got submitted"() {
        when:
        def result = JsonStringGenerator.getJsonString(
                null,
                "user_name",
                null,
                null,
                "displayname",
                "nickname",
                "profileurl",
                "title",
                "usertype",
                "preferredlanguage",
                "locale",
                "timezone",
                //timezone
                "password"
                ,
        )

        then:
        result == '{"schemas":["urn:scim:schemas:core:1.0"],"userName":"user_name","displayName":"displayname","nickName":"nickname","profileUrl":"profileurl","title":"title","userType":"usertype","preferredLanguage":"preferredlanguage","locale":"locale","timezone":"timezone","password":"password","emails":{},"phoneNumbers":{},"ims":{},"photos":{},"groups":{},"entitlements":{},"roles":{},"x509Certificates":{}}'
    }

    def "should return scim.schema.v2.Constants schema when empty schema got submitted"() {
        when:
        def result = JsonStringGenerator.getJsonString(
                "",
                "user_name",
                null,
                null,
                "displayname",
                "nickname",
                "profileurl",
                "title",
                "usertype",
                "preferredlanguage",
                "locale",
                "timezone",
                //timezone
                "password"
                ,
        )

        then:
        result == '{"schemas":["urn:scim:schemas:core:1.0"],"userName":"user_name","displayName":"displayname","nickName":"nickname","profileUrl":"profileurl","title":"title","userType":"usertype","preferredLanguage":"preferredlanguage","locale":"locale","timezone":"timezone","password":"password","emails":{},"phoneNumbers":{},"ims":{},"photos":{},"groups":{},"entitlements":{},"roles":{},"x509Certificates":{}}'
    }



    def "should generate valid JSON String"() {
        when:
        def result = JsonStringGenerator.getJsonString(
                "schema",
                "user_name",
                "firstname",
                "lastname",
                "displayname",
                "nickname",
                "profileurl",
                "title",
                "usertype",
                "preferredlanguage",
                "locale",
                "timezone",
                //timezone
                "password"
        )
        then:
        result ==
                '{"schemas":["schema"],"userName":"user_name","name":{"formatted":"firstname lastname","familyName":"lastname","givenName":"firstname"},"displayName":"displayname","nickName":"nickname","profileUrl":"profileurl","title":"title","userType":"usertype","preferredLanguage":"preferredlanguage","locale":"locale","timezone":"timezone","password":"password","emails":{},"phoneNumbers":{},"ims":{},"photos":{},"groups":{},"entitlements":{},"roles":{},"x509Certificates":{}}'
    }

    def "should not generate lists when empty"() {
        when:
        def result = JsonStringGenerator.getJsonStringPatch(
                "schema",
                "user_name",
                "firstname",
                "lastname",
                "displayname",
                "nickname",
                "profileurl",
                "title",
                "usertype",
                "preferredlanguage",
                "locale",
                "timezone",
                //timezone
                "password",
                null
        )
        then:
        result ==
                '{"schemas":["schema"],"userName":"user_name","name":{"formatted":"firstname lastname","familyName":"lastname","givenName":"firstname"},"displayName":"displayname","nickName":"nickname","profileUrl":"profileurl","title":"title","userType":"usertype","preferredLanguage":"preferredlanguage","locale":"locale","timezone":"timezone","password":"password","groups":{}}'
    }

    def "should generate lists when not empty"() {
        when:
        CRUDListController.KnownMultiValueAttributeLists.EMAIL.set.values().add(new MultiValuedAttribute.Builder().setValue("ha").build())
        CRUDListController.KnownMultiValueAttributeLists.ENTITLEMENT.set.values().add(new MultiValuedAttribute.Builder().build())
        CRUDListController.KnownMultiValueAttributeLists.GROUP.set.values().add(new MultiValuedAttribute.Builder().setValue("ha").build())
        CRUDListController.KnownMultiValueAttributeLists.IM.set.values().add(new MultiValuedAttribute.Builder().setValue("ha").build())
        CRUDListController.KnownMultiValueAttributeLists.PHONE.set.values().add(new MultiValuedAttribute.Builder().setValue("ha").build())
        CRUDListController.KnownMultiValueAttributeLists.PHOTO.set.values().add(new MultiValuedAttribute.Builder().setValue("ha").build())
        CRUDListController.KnownMultiValueAttributeLists.ROLE.set.values().add(new MultiValuedAttribute.Builder().setValue("ha").build())
        CRUDListController.KnownMultiValueAttributeLists.X509.set.values().add(new MultiValuedAttribute.Builder().setValue("ha").build())
        def result = JsonStringGenerator.getJsonStringPatch(
                "schema",
                "user_name",
                "firstname",
                "lastname",
                "displayname",
                "nickname",
                "profileurl",
                "title",
                "usertype",
                "preferredlanguage",
                "locale",
                "timezone",
                //timezone
                "password",
                null
        )
        then:
        result =='{"schemas":["schema"],"userName":"user_name","name":{"formatted":"firstname lastname","familyName":"lastname","givenName":"firstname"},"displayName":"displayname","nickName":"nickname","profileUrl":"profileurl","title":"title","userType":"usertype","preferredLanguage":"preferredlanguage","locale":"locale","timezone":"timezone","password":"password","emails":{"email":[{"value":"ha"}]},"phoneNumbers":{"phoneNumber":[{"value":"ha"}]},"ims":{"im":[{"value":"ha"}]},"photos":{"photo":[{"value":"ha"}]},"groups":{"group":[{"value":"ha"}]},"entitlements":{"entitlement":[{}]},"roles":{"role":[{"value":"ha"}]},"x509Certificates":{"x509Certificate":[{"value":"ha"}]}}'

    }


    def "should generate meta"() {
        when:
        def result = JsonStringGenerator.getJsonStringPatch(
                "schema",
                "user_name",
                "firstname",
                "lastname",
                "displayname",
                "nickname",
                "profileurl",
                "title",
                "usertype",
                "preferredlanguage",
                "locale",
                "timezone",
                //timezone
                "password",
                ["nickname", "password", "locale"] as Set
        )
        then:
        result ==~ /\{\"meta\":\{\"created\":\d+,\"lastModified\":\d+,\"attributes\":\[\"nickname\",\"password\",\"locale\"\]\}.*/
    }
}
