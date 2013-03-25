package org.osiam.oauth2.client;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
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

    @RequestMapping("/createResource")
    public String createResource(HttpServletRequest req, @RequestParam String externalId,
                    @RequestParam String name, @RequestParam String password, @RequestParam String access_token)
                            throws ServletException, IOException {

        StringRequestEntity requestEntity = new StringRequestEntity(getJsonString(externalId, name, password),
                "application/json", "UTF-8");

        String environment = req.getScheme() + "://" + req.getServerName() + ":8080";
        String url = environment + "/authorization-server/User/" + "?access_token=" + access_token;

        PostMethod post = executePostMethod(requestEntity, url);

        readJsonFromBody(req, post);

        return "user";
    }

    private void readJsonFromBody(HttpServletRequest req, PostMethod post) throws IOException {
        try {
            JSONObject authResponse = new JSONObject(
                    new JSONTokener(new InputStreamReader(post.getResponseBodyAsStream(), CHARSET)));
            req.setAttribute("userResponse", authResponse.toString());
            req.setAttribute("LocationHeader", post.getResponseHeader("Location"));
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage(), e);
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

    private String getJsonString(String externalId, String name, String password) {
        try {
            return new JSONObject().put("externalId", externalId).put("userName", name).put("password", password).toString();
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}