package org.osiam.oauth2.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;

@Controller
public class AuthCodeServlet {

    private static final long serialVersionUID = 403290981215464050L;


    @RequestMapping("/authcode")
    public String redirectTogetAuthCode(HttpServletRequest req) throws ServletException, IOException {
        String environment = req.getScheme() + "://" + req.getServerName() + ":8080";
        String clientId = "testClient";
        String redirectUri = req.getScheme() + "://" + req.getServerName() + ":8080/oauth2-client/accessToken";
        String url = environment + "/authorization-server/oauth/authorize?response_type=code&scope=GET&state=haha&" +
                "client_id=" + clientId + "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8");
        return "redirect:" + url;
    }
}
