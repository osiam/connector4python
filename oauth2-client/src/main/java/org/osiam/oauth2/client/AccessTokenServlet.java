package org.osiam.oauth2.client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet(name = "authCode", urlPatterns = {"/accessToken"})
public class AccessTokenServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            String code = req.getParameter("code");

            String environment = "http://localhost:8080";
            String clientId = "testClient";
            String redirectUri = "http://localhost:8080/oauth2-client/accessToken";
            String url = environment + "/authorization-server/oauth/token?client_id=" + clientId + "&client_secret=secret&code=" +
                    code + "&grant_type=authorization_code&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8");

            resp.sendRedirect(url);

    }

}
