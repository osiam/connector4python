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

package org.osiam.oauth2.client;

import org.codehaus.jackson.map.ObjectMapper;
import scim.schema.v2.Constants;
import scim.schema.v2.Meta;
import scim.schema.v2.Name;
import scim.schema.v2.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.osiam.oauth2.client.CRUDListController.KnownMultiValueAttributeLists.*;

public class JsonStringGenerator {
    public static String getJsonStringPatch(
            String schema,
            String user_name,
            String firstname,
            String lastname,
            String displayname,
            String nickname,
            String profileurl,
            String title,
            String usertype,
            String preferredlanguage,
            String locale,
            String timezone,
            String password,
            Set<String> metaAttribues) throws IOException {
        Meta
                meta =
                null;
        if (metaAttribues !=
                null &&
                !metaAttribues.isEmpty()) {
            meta =
                    new Meta.Builder().setAttributes(metaAttribues).build();
        }


        User
                user =
                getPreFilledBuilder(user_name, firstname, lastname, displayname, nickname, profileurl, title,
                        usertype, preferredlanguage, locale, timezone, password)
                        .setEmails(EMAIL.<User.Emails>getSet().getEmail().isEmpty() ?
                                null :
                                EMAIL.<User.Emails>getSet())
                        .setPhoneNumbers(PHONE.<User.PhoneNumbers>getSet().getPhoneNumber().isEmpty() ?
                                null :
                                PHONE.<User.PhoneNumbers>getSet())
                        .setIms(IM.<User.Ims>getSet().getIm().isEmpty() ?
                                null :
                                IM.<User.Ims>getSet())
                        .setPhotos(PHOTO.<User.Photos>getSet().getPhoto().isEmpty() ?
                                null :
                                PHOTO.<User.Photos>getSet())
                        .setAddresses(getAddresses())
                        .setGroups(GROUP.<User.Groups>getSet())
                        .setEntitlements(ENTITLEMENT.<User.Entitlements>getSet().getEntitlement().isEmpty() ?
                                null :
                                ENTITLEMENT.<User.Entitlements>getSet())
                        .setRoles(ROLE.<User.Roles>getSet().getRole().isEmpty() ?
                                null :
                                ROLE.<User.Roles>getSet())
                        .setX509Certificates(X509.<User.X509Certificates>getSet().getX509Certificate().isEmpty() ?
                                null :
                                X509.<User.X509Certificates>getSet())
                        .setAny(null)
                        .setSchemas(getSchemas(schema))
                        .setMeta(meta)
                        .build();
        return new ObjectMapper().writeValueAsString(user);
    }

    public static String getJsonString(
            String schema,
            String user_name,
            String firstname,
            String lastname,
            String displayname,
            String nickname,
            String profileurl,
            String title,
            String usertype,
            String preferredlanguage,
            String locale,
            String timezone,
            //String timezone
            String password
    ) throws IOException {


        User
                user =
                getPreFilledBuilder(user_name, firstname, lastname, displayname, nickname, profileurl, title,
                        usertype, preferredlanguage, locale, timezone, password)
                        .setEmails(EMAIL.<User.Emails>getSet())
                        .setPhoneNumbers(PHONE.<User.PhoneNumbers>getSet())
                        .setIms(IM.<User.Ims>getSet())
                        .setPhotos(PHOTO.<User.Photos>getSet())
                        .setAddresses(getAddresses())
                        .setGroups(GROUP.<User.Groups>getSet())
                        .setEntitlements(ENTITLEMENT.<User.Entitlements>getSet())
                        .setRoles(ROLE.<User.Roles>getSet())
                        .setX509Certificates(X509.<User.X509Certificates>getSet())
                        .setAny(null)
                        .setSchemas(getSchemas(schema))

                        .build();
        return new ObjectMapper().writeValueAsString(user);
    }

    static User.Builder getPreFilledBuilder(String user_name, String firstname, String lastname, String displayname, String nickname, String profileurl, String title, String usertype, String preferredlanguage, String locale, String timezone, String password) {
        return new User.Builder(user_name)
                .setPassword(password)
                .setName(getName(firstname, lastname))
                .setDisplayName(displayname)
                .setNickName(nickname)
                .setProfileUrl(profileurl)
                .setTitle(title)
                .setUserType(usertype)
                .setPreferredLanguage(preferredlanguage)
                .setLocale(locale)
                .setTimezone(timezone);
    }

    static Set<String> getSchemas(String schema) {
        if (schema ==
                null) {
            return Constants.CORE_SCHEMAS;
        }
        String[]
                schemas =
                schema.split(",");
        if (schemas.length >
                0 &&
                schemas[0] !=
                        "") {
            return new HashSet<>(Arrays.asList(schemas));
        } else {
            return Constants.CORE_SCHEMAS;
        }
    }

    static Name getName(String firstName, String lastName) {
        if (firstName ==
                null &&
                lastName ==
                        null) {
            return null;
        }
        return new Name.Builder()
                .setFamilyName(lastName)
                .setGivenName(firstName)
                .setFormatted(firstName +
                        " " +
                        lastName)
                .build();
    }

    static User.Addresses getAddresses() {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }
}