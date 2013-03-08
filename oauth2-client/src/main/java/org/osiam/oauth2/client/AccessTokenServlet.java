package org.osiam.oauth2.client;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
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
import java.util.logging.Logger;

@Controller
@RequestMapping("/accessToken")
public class AccessTokenServlet {
    private final Logger logger = Logger.getLogger(AccessTokenServlet.class.getName());

    private HttpClient httpClient;

    private static final String CLIENT_ID = "testClient";
    private static final String CLIENT_SECRET = "secret";

    public AccessTokenServlet() {
        this.httpClient = new HttpClient();
    }

    @RequestMapping(params = {"error", "error_description"})
    public String access_denied(@RequestParam String error, @RequestParam String error_description) {
        logger.info(error + " is " + error_description);
        return "error";

    }

    @RequestMapping(params = "code")
    public String doGet(HttpServletRequest req) throws ServletException, IOException {
        String code = req.getParameter("code");
        String environment = req.getScheme() + "://" + req.getServerName() + ":8080";
        String tokenUrl = environment + "/authorization-server/oauth/token";
        String combined = CLIENT_ID + ":" + CLIENT_SECRET;
        String redirectUri = req.getScheme() + "://" + req.getServerName() + ":8080" + "/oauth2-client/accessToken";
        sendAuthCodeToAuthorizationServerWhenCodeIsSent(code, tokenUrl, combined, redirectUri, req);
        addAttributesToHttpRequest(req, CLIENT_ID, CLIENT_SECRET);
        return "parameter";
    }

    private void sendAuthCodeToAuthorizationServerWhenCodeIsSent(String code, String tokenUrl, String combined, String redirectUri, HttpServletRequest req) throws IOException {
        if (code != null) {
            sendAuthCode(code, tokenUrl, combined, redirectUri, req);
        }
    }

    private void sendAuthCode(String code, String tokenUrl, String combined, String redirectUri, HttpServletRequest req) throws IOException {
        PostMethod post = new PostMethod();
        addPostMethodParameter(post, code, tokenUrl, combined, redirectUri);
        httpClient.executeMethod(post);
        addJsonResponseToHttpRequest(post, req);
        req.setAttribute("code", code);
        req.setAttribute("redirect_uri", redirectUri);
    }

    private void addAttributesToHttpRequest(HttpServletRequest req, String clientId, String clientSecret) {
        req.setAttribute("client_id", clientId);
        req.setAttribute("client_secret", clientSecret);
    }

    private void addJsonResponseToHttpRequest(PostMethod post, HttpServletRequest req) throws IOException {
        try {
            JSONObject authResponse = new JSONObject(
                    new JSONTokener(new InputStreamReader(post.getResponseBodyAsStream())));
            req.setAttribute("response", authResponse.toString());
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            post.releaseConnection();
        }
    }

    private void addPostMethodParameter(PostMethod post, String code, String tokenUrl, String combined, String redirectUri) throws URIException {
        post.setURI(new URI(tokenUrl, false));
        String encoding = new String(Base64.encodeBase64(combined.getBytes()));
        post.addRequestHeader("Authorization", "Basic " + encoding);
        post.addParameter("code", code);
        post.addParameter("grant_type", "authorization_code");
        post.addParameter("redirect_uri", redirectUri);
    }
}
