package org.osiam.oauth2.client;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.osiam.oauth2.client.exceptions.UserFriendlyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 26.03.13
 * Time: 09:32
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class UpdateResourceController {


    private HttpClient httpClient;


    public UpdateResourceController() {
        this.httpClient = new HttpClient();
    }

    @RequestMapping("/updateResource")
    public String updateResource(HttpServletRequest req,
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

                                 @RequestParam String access_token,
                                 @RequestParam String idForUpdate) throws ServletException, IOException, UserFriendlyException {

        String jsonString = AddResourceController.getJsonString(
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
        String url = environment + "/authorization-server/User/" + idForUpdate + "?access_token=" + access_token;

        PutMethod put = executePutMethod(requestEntity, url);

        readJsonFromBody(req, put);
        req.setAttribute("access_token", access_token);

        return "user";
    }

    private void readJsonFromBody(HttpServletRequest req, PutMethod put) throws IOException, UserFriendlyException {
        if (put.getStatusCode() > 399) {
            throw new UserFriendlyException(String.valueOf(put.getStatusCode()));
        }
        try {
            String response = put.getResponseBodyAsString();
            req.setAttribute("userResponse", response);
            req.setAttribute("LocationHeader", put.getResponseHeader("Location"));
        } finally {
            put.releaseConnection();
        }
    }

    private PutMethod executePutMethod(StringRequestEntity requestEntity, String url) throws IOException {
        PutMethod put = new PutMethod();
        put.setURI(new URI(url, false));
        put.setRequestEntity(requestEntity);

        httpClient.executeMethod(put);
        return put;
    }
}