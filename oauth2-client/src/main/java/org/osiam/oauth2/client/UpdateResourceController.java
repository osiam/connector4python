package org.osiam.oauth2.client;


import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPut;
import org.osiam.oauth2.client.exceptions.UserFriendlyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 26.03.13
 * Time: 09:32
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class UpdateResourceController {

    @Autowired
    private
    GetResponseAndCast
            getResponeAndCast;

    @RequestMapping("/updateResource")
    public String updateResource(HttpServletRequest req,
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
                                 @RequestParam String access_token,
                                 @RequestParam String idForUpdate) throws ServletException, IOException, UserFriendlyException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {

        String
                jsonString =
                JsonStringGenerator.getJsonString(
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
                        password
                );
        Constructor<HttpPut>
                constructor =
                HttpPut.class.getConstructor(String.class);
        return createEnvAndInvokeHttpCall(getResponeAndCast, req, access_token, idForUpdate, jsonString, constructor);
    }

    static String createEnvAndInvokeHttpCall(GetResponseAndCast getResponeAndCast, HttpServletRequest req, String access_token, String idForUpdate, String jsonString, Constructor<? extends HttpEntityEnclosingRequestBase> constructor) throws InstantiationException, IllegalAccessException, InvocationTargetException, IOException, UserFriendlyException {
        String
                environment =
                req.getScheme() +
                        "://" +
                        req.getServerName() +
                        ":8080";
        String
                url =
                environment +
                        "/authorization-server/User/" +
                        idForUpdate +
                        "?access_token=" +
                        access_token;
        HttpEntityEnclosingRequestBase
                httpPut =
                constructor.newInstance(url);
        getResponeAndCast.getResponseAndSetAccessToken(req, access_token, jsonString, httpPut);
        return "user";
    }
}