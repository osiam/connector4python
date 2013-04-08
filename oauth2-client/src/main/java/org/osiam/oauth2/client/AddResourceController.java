package org.osiam.oauth2.client;


import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.osiam.oauth2.client.exceptions.UserFriendlyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import scim.schema.v2.Constants;
import scim.schema.v2.Meta;
import scim.schema.v2.Name;
import scim.schema.v2.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.osiam.oauth2.client.CRUDListController.KnownMultiValueAttributeLists.*;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 22.03.13
 * Time: 11:21
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class AddResourceController {

    private GetResponseAndCast getResponseAndCast;


    public AddResourceController() {
    }

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
        Meta meta = null;
        if (metaAttribues != null && !metaAttribues.isEmpty()) {
            meta = new Meta.Builder().setAttributes(metaAttribues).build();
        }


        User user = getPreFilledBuilder(user_name, firstname, lastname, displayname, nickname, profileurl, title,
                usertype, preferredlanguage, locale, timezone, password)
                .setEmails(EMAIL.<User.Emails>getSet().getEmail().isEmpty() ? null : EMAIL.<User.Emails>getSet())
                .setPhoneNumbers(PHONE.<User.PhoneNumbers>getSet().getPhoneNumber().isEmpty() ? null : PHONE.<User.PhoneNumbers>getSet())
                .setIms(IM.<User.Ims>getSet().getIm().isEmpty() ? null : IM.<User.Ims>getSet())
                .setPhotos(PHOTO.<User.Photos>getSet().getPhoto().isEmpty() ? null : PHOTO.<User.Photos>getSet())
                .setAddresses(getAddresses())
                .setGroups(GROUP.<User.Groups>getSet())
                .setEntitlements(ENTITLEMENT.<User.Entitlements>getSet().getEntitlement().isEmpty() ? null : ENTITLEMENT.<User.Entitlements>getSet())
                .setRoles(ROLE.<User.Roles>getSet().getRole().isEmpty() ? null : ROLE.<User.Roles>getSet())
                .setX509Certificates(X509.<User.X509Certificates>getSet().getX509Certificate().isEmpty() ? null : X509.<User.X509Certificates>getSet())
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


        User user = getPreFilledBuilder(user_name, firstname, lastname, displayname, nickname, profileurl, title,
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

    private static User.Builder getPreFilledBuilder(String user_name, String firstname, String lastname, String displayname, String nickname, String profileurl, String title, String usertype, String preferredlanguage, String locale, String timezone, String password) {
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

    private static Set<String> getSchemas(String schema) {
        if (schema == null)
            return Constants.CORE_SCHEMAS;
        String[] schemas = schema.split(",");
        if (schemas.length > 0 && schemas[0] != "")
            return new HashSet<>(Arrays.asList(schemas));
        else
            return Constants.CORE_SCHEMAS;
    }

    private static Name getName(String firstname, String lastname) {
        if (firstname == null && lastname == null)
            return null;
        return new Name.Builder()
                .setFamilyName(lastname)
                .setGivenName(firstname)
                .setFormatted(firstname + " " + lastname)
                .build();
    }

    private static User.Addresses getAddresses() {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    @RequestMapping("/createResource")
    public String createResource(HttpServletRequest req,
                                 @RequestParam String schema,
                                 @RequestParam String user_name,
                                 @RequestParam String firstname,
                                 @RequestParam String lastname,
                                 @RequestParam String displayname,
                                 @RequestParam String nickname,
                                 @RequestParam String profileurl,
                                 @RequestParam String title,
                                 @RequestParam String usertype,
                                 @RequestParam String preferredlanguage,
                                 @RequestParam String locale,
                                 @RequestParam String timezone,
                                 //@RequestParam String timezone
                                 @RequestParam String password,
                                 @RequestParam String access_token)
            throws ServletException, IOException, UserFriendlyException {

        String jsonString = getJsonString(
                schema,
                user_name,
                firstname,
                lastname,
                displayname,
                nickname,
                profileurl,
                title,
                usertype,
                preferredlanguage,
                locale,
                timezone,
                //timezone
                password
        );

        getResponseAndCast = new GetResponseAndCast(new DefaultHttpClient());
        String environment = req.getScheme() + "://" + req.getServerName() + ":8080";
        String url = environment + "/authorization-server/User/" + "?access_token=" + access_token;
        HttpPost request = new HttpPost(url);
        getResponseAndCast.getResponseAndSetAccessToken(req, access_token, jsonString, request);

        return "user";
    }


}