package org.osiam.oauth2.client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet(name = "authCode", urlPatterns = {"/authcode"})
public class AuthCodeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String environment = "http://localhost:8080";
        String clientId = "testClient";
        String redirectUri = "http://localhost:8080/oauth2-client/accessToken";
        String url = environment + "/authorization-server/oauth/authorize?response_type=code&client_id=" +
                clientId + "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8");

        resp.sendRedirect(url);
    }
}
