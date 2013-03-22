package org.osiam.oauth2.client;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.PostMethod;
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
    public String doGet(HttpServletRequest req, @RequestParam String externalId,
                    @RequestParam String name, @RequestParam String password, @RequestParam String access_token)
                            throws ServletException, IOException {

        String userData;
        try {
            userData = new JSONObject().put("externalId", externalId).put("username", name).put("password", password).toString();
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

        NameValuePair[] nameValuePairs = {new NameValuePair("user", userData)};

        String environment = req.getScheme() + "://" + req.getServerName() + ":8080";
        String url = environment + "/authorization-server/User/" + "?access_token=" + access_token;

        PostMethod post = new PostMethod();
        post.setURI(new URI(url, false));
        post.setRequestBody(nameValuePairs);

        httpClient.executeMethod(post);

        try {
            JSONObject authResponse = new JSONObject(
                    new JSONTokener(new InputStreamReader(post.getResponseBodyAsStream(), CHARSET)));
            req.setAttribute("userResponse", authResponse.toString());
            req.setAttribute("LocationHeader", post.getRequestHeader("Location"));
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            post.releaseConnection();
        }

        return "user";
    }
}