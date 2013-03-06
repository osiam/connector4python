package org.osiam.oauth2.client;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
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
public class AccessTokenServlet extends HttpServlet {

    private static final long serialVersionUID = -403250971215465050L;


    private static final String CLIENT_ID = "testClient";
    private static final String CLIENT_SECRET = "secret";


    public final void init(ServletConfig config) throws ServletException {
        super.init(config);

        /* Servlets cannot be instantiated with Spring container, they are instantiated by servlet container.
           Therefore we cannot declare the servlet as a spring bean in our context.xml file and add a reference
           to inject the desired bean. With the following we enable autowiring in the servlet context.*/
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
    }

    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String environment = req.getScheme() + "://" + req.getServerName() + ":8080";
        String tokenUrl = environment + "/authorization-server/oauth/token";
        String combined = CLIENT_ID +":"+ CLIENT_SECRET;
        String redirectUri = req.getScheme() + "://" + req.getServerName() + ":8080" + "/oauth2-client/accessToken";
        sendAuthCodeToAuthorizationServerWhenCodeIsSent(code, tokenUrl, combined, redirectUri, req, HTTPConnectionWraper.createPost());
        addAttributesToHttpRequest(req, CLIENT_ID, CLIENT_SECRET);
        req.getRequestDispatcher("/parameter.jsp").forward(req, resp);
    }

    private void sendAuthCodeToAuthorizationServerWhenCodeIsSent(String code, String tokenUrl, String combined, String redirectUri, HttpServletRequest req, PostMethod post) throws IOException {
        if (code != null){
            sendAuthCode(code, tokenUrl, combined, redirectUri, req, post);
        }
    }

    private void sendAuthCode(String code, String tokenUrl, String combined, String redirectUri, HttpServletRequest req, PostMethod post) throws IOException {

        addPostMethodParameter(code, tokenUrl, combined, redirectUri, post);
        HTTPConnectionWraper.createClient().executeMethod(post);
        addJsonResponseToHttpRequest(req, post);
        req.setAttribute("code", code);
        req.setAttribute("redirect_uri", redirectUri);
    }

    private void addAttributesToHttpRequest(HttpServletRequest req, String clientId, String clientSecret) {
        req.setAttribute("client_id", clientId);
        req.setAttribute("client_secret", clientSecret);
    }

    private void addJsonResponseToHttpRequest(HttpServletRequest req, HttpMethod post) throws IOException {
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

    private void addPostMethodParameter(String code, String tokenUrl, String combined, String redirectUri, PostMethod post) throws URIException {
        post.setURI(new URI(tokenUrl));
        String encoding = new String(Base64.encodeBase64(combined.getBytes()));
        post.addRequestHeader("Authorization", "Basic " + encoding);
        post.addParameter("code", code);
        post.addParameter("grant_type", "authorization_code");
        post.addParameter("redirect_uri", redirectUri);
    }
}
