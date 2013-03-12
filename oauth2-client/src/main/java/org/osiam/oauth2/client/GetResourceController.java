package org.osiam.oauth2.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GetResourceController {

    @RequestMapping("/resource")
    public String redirectToResourceServer(HttpServletRequest req, @RequestParam String access_token)
            throws ServletException, IOException {
        String environment = req.getScheme() + "://" + req.getServerName() + ":8080";
        String url = environment + "/authorization-server/secured/attributes?access_token=" + access_token;
        return "redirect:" + url;
    }
}
