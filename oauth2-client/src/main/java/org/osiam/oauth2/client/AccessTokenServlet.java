package org.osiam.oauth2.client;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;

@WebServlet(name = "accessToken", urlPatterns = {"/accessToken"})
public class AccessTokenServlet extends HttpServlet{

    private HttpClient httpClient;
    private PostMethod post;
    private final String clientId = "testClient";
    private final String clientSecret = "secret";


    @Autowired
    public void setPost(PostMethod post) {
        this.post = post;
    }

    @Autowired
    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        /* Servlets cannot be instantiated with Spring container, they are instantiated by servlet container.
           Therefore we cannot declare the servlet as a spring bean in our context.xml file and add a reference
           to inject the desired bean. With the following we enable autowiring in the servlet context.*/
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String code = req.getParameter("code");

        String environment = req.getScheme() + "://" + req.getServerName() + ":8080";
        String tokenUrl = environment + "/authorization-server/oauth/token";

        String combined = clientId+":"+clientSecret;
        String redirectUri = req.getScheme() + "://" + req.getServerName() + ":8080" + "/oauth2-client/accessToken";

        sendAuthCodeToAuthorizationServerWhenCodeIsSent(code, tokenUrl, combined, redirectUri, req);
        addAttributesToHttpRequest(req, code, clientId, clientSecret, redirectUri);

        req.getRequestDispatcher("/parameter.jsp").forward(req, resp);
    }

    private void sendAuthCodeToAuthorizationServerWhenCodeIsSent(String code, String tokenUrl, String combined, String redirectUri, HttpServletRequest req) throws IOException {
        if (code != null){
            sendAuthCode(code, tokenUrl, combined, redirectUri, req);
        }
    }

    private void sendAuthCode(String code, String tokenUrl, String combined, String redirectUri, HttpServletRequest req) throws IOException {
        addPostMethodParameter(code, tokenUrl, combined, redirectUri);
        httpClient.executeMethod(post);
        addJsonResponseToHttpRequest(req);
        req.setAttribute("code", code);
    }

    private void addAttributesToHttpRequest(HttpServletRequest req, String code, String clientId, String clientSecret, String redirectUri) {
        req.setAttribute("client_id", clientId);
        req.setAttribute("client_secret", clientSecret);
        req.setAttribute("redirect_uri", redirectUri);

    }

    private void addJsonResponseToHttpRequest(HttpServletRequest req) throws IOException {
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

    private void addPostMethodParameter(String code, String tokenUrl, String combined, String redirectUri) throws URIException {
        post.setURI(new URI(tokenUrl));
        String encoding = new String(Base64.encodeBase64(combined.getBytes()));
        post.addRequestHeader("Authorization", "Basic " + encoding);
        post.addParameter("code", code);
        post.addParameter("grant_type", "authorization_code");
        post.addParameter("redirect_uri", redirectUri);
    }
}
