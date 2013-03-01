package org.osiam.oauth2.client;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;


@WebServlet(name = "authCode", urlPatterns = {"/accessToken"})
public class AccessTokenServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String code = req.getParameter("code");

        String environment = "http://localhost:8080";
        String tokenUrl = environment + "/authorization-server/oauth/token";

        String clientId = "testClient";
        String clientSecret = "secret";
        String combined = clientId+":"+clientSecret;
        String redirectUri = "http://localhost:8080/oauth2-client/accessToken";

        HttpClient httpclient = new HttpClient();



        PostMethod post = new PostMethod(tokenUrl);
        String encoding = String.valueOf(Base64.encodeBase64(combined.getBytes()));

        System.out.println(encoding);
        post.addRequestHeader("Authorization", "Basic " + encoding);

        post.addParameter("code", code);
        post.addParameter("grant_type", "authorization_code");
        post.addParameter("redirect_uri", redirectUri);

        httpclient.executeMethod(post);

        String accessToken;
        String expiresIn;
        try {
            JSONObject authResponse = new JSONObject(
            new JSONTokener(new InputStreamReader(post.getResponseBodyAsStream())));

            accessToken = authResponse.getString("access_token");
            expiresIn = authResponse.getString("expires_in");

        } catch (JSONException e) {
            e.printStackTrace();
            throw new ServletException(e);
        } finally {
            post.releaseConnection();
        }

        req.setAttribute("client_id", clientId);
        req.setAttribute("client_secret", clientSecret);
        req.setAttribute("redirect_uri", redirectUri);
        req.setAttribute("code", code);
        req.setAttribute("access_token", accessToken);
        req.setAttribute("expires_in", expiresIn);

        req.getRequestDispatcher("/parameter.jsp").forward(req, resp);
    }
}
