package org.osiam.oauth2.client;

import com.sun.org.apache.bcel.internal.classfile.ConstantString;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.osiam.oauth2.client.exceptions.UserFriendlyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import scim.schema.v2.Constants;
import scim.schema.v2.Name;
import scim.schema.v2.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 22.03.13
 * Time: 11:21
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class AddResourceController {

    private HttpClient httpClient;

    private static final Charset CHARSET = Charset.forName("UTF-8");


    public AddResourceController() {
        this.httpClient = new HttpClient();
    }

    @RequestMapping("/crud/createResource")
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
                                 @RequestParam String emails,
                                 @RequestParam String phonenumbers,
                                 @RequestParam String ims,
                                 @RequestParam String photos,
                                 @RequestParam String addresses,
                                 @RequestParam String groups,
                                 @RequestParam String entitlements,
                                 @RequestParam String roles,
                                 @RequestParam String x509,
                                 @RequestParam String any,
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
                password,
                emails,
                phonenumbers,
                ims,
                photos,
                addresses,
                groups,
                entitlements,
                roles,
                x509,
                any
        );
        StringRequestEntity requestEntity = new StringRequestEntity(jsonString,
                "application/json", "UTF-8");

        String environment = req.getScheme() + "://" + req.getServerName() + ":8080";
        String url = environment + "/authorization-server/User/" + "?access_token=" + access_token;

        PostMethod post = executePostMethod(requestEntity, url);

        readJsonFromBody(req, post);

        return "user";
    }

    private void readJsonFromBody(HttpServletRequest req, PostMethod post) throws IOException, UserFriendlyException {

        if (post.getStatusCode() > 399) {
            throw new UserFriendlyException(String.valueOf(post.getStatusCode()));
        }

        try {
            req.setAttribute("userResponse", post.getResponseBodyAsString());
            req.setAttribute("LocationHeader", post.getResponseHeader("Location"));
        } finally {
            post.releaseConnection();
        }
    }

    private PostMethod executePostMethod(StringRequestEntity requestEntity, String url) throws IOException {
        PostMethod post = new PostMethod();
        post.setURI(new URI(url, false));
        post.setRequestEntity(requestEntity);

        httpClient.executeMethod(post);
        return post;
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
            String password,
            String emails,
            String phonenumbers,
            String ims,
            String photos,
            String addresses,
            String groups,
            String entitlements,
            String roles,
            String x509,
            String any
    ) throws IOException {


        User user = new User.Builder(user_name)
                .setPassword(password)
                .setName(getName(firstname, lastname))
                .setDisplayName(displayname)
                .setNickName(nickname)
                .setProfileUrl(profileurl)
                .setTitle(title)
                .setUserType(usertype)
                .setPreferredLanguage(preferredlanguage)
                .setLocale(locale)
                .setTimezone(timezone)
                .setEmails(getEmails(emails))
                .setPhoneNumbers(getPhoneNumbers(phonenumbers))
                .setIms(getIms(ims))
                .setPhotos(getPhotos(photos))
                .setAddresses(getAddresses(addresses))
                .setGroups(getGroups(groups))
                .setEntitlements(getEntitlements(entitlements))
                .setRoles(getRoles(roles))
                .setX509Certificates(getX509(x509))
                .setAny(getAny(any))
                .setSchemas(getSchemas(schema))

                .build();
        return new ObjectMapper().writeValueAsString(user);
    }

    private static Set<String> getSchemas(String schema) {
        String[] schemas = schema.split(",");
        if (schemas.length > 0)
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

    private static Set<Object> getAny(String any) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    private static User.X509Certificates getX509(String x509) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    private static User.Roles getRoles(String roles) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    private static User.Entitlements getEntitlements(String entitlements) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    private static User.Groups getGroups(String groups) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    private static User.Addresses getAddresses(String addresses) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    private static User.Photos getPhotos(String photos) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    private static User.Ims getIms(String ims) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    private static User.PhoneNumbers getPhoneNumbers(String phonenumbers) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    private static User.Emails getEmails(String emails) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }
}