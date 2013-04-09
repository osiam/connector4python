package org.osiam.oauth2.client;


import org.apache.http.client.methods.HttpPost;
import org.osiam.oauth2.client.exceptions.UserFriendlyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 22.03.13
 * Time: 11:21
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class AddResourceController {

    @Autowired
    private GetResponseAndCast getResponseAndCast;



    public AddResourceController() {
    }

    @RequestMapping("/createResource")
    public String createResource(HttpServletRequest req,
                                 @RequestParam String schema,
                                 @RequestParam String user_name,
                                 @RequestParam String firstname,
                                 @RequestParam String lastname,
                                 @RequestParam String displayname,
                                 @RequestParam String nickname,
                                 @RequestParam String profileurl,
                                 @RequestParam String title,
                                 @RequestParam String usertype,
                                 @RequestParam String preferredlanguage,
                                 @RequestParam String locale,
                                 @RequestParam String timezone,
                                 //@RequestParam String timezone
                                 @RequestParam String password,
                                 @RequestParam String access_token)
            throws ServletException, IOException, UserFriendlyException {

        String jsonString = JsonStringGenerator.getJsonString(
                schema,
                user_name,
                firstname,
                lastname,
                displayname,
                nickname,
                profileurl,
                title,
                usertype,
                preferredlanguage,
                locale,
                timezone,
                password);
        String environment = req.getScheme() + "://" + req.getServerName() + ":8080";
        String url = environment + "/authorization-server/User/" + "?access_token=" + access_token;
        HttpPost request = new HttpPost(url);
        getResponseAndCast.getResponseAndSetAccessToken(req, access_token, jsonString, request);

        return "user";
    }


}