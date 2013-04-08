/*
 * Copyright (C) 2013 tarent AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.osiam.oauth2.client;


import org.apache.http.client.methods.HttpPatch;
import org.apache.http.impl.client.DefaultHttpClient;
import org.osiam.oauth2.client.exceptions.UserFriendlyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 26.03.13
 * Time: 09:32
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class PatchResourceController {


    @RequestMapping("/patchResource")
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
                                 @RequestParam String password,
                                 @RequestParam String access_token,
                                 @RequestParam String idForUpdate,
                                 @RequestParam String delete) throws ServletException, IOException, UserFriendlyException {

        Set<String> attributesToDelete = null;
        if (delete != null && !delete.isEmpty()) {
            attributesToDelete = new HashSet<>(Arrays.asList(delete.split(",")));
        }

        String jsonString = AddResourceController.getJsonStringPatch(
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
                password,
                attributesToDelete
        );

        String environment = req.getScheme() + "://" + req.getServerName() + ":8080";
        String url = environment + "/authorization-server/User/" + idForUpdate + "?access_token=" + access_token;
        HttpPatch httpPatch = new HttpPatch(url);
        new GetResponseAndCast(new DefaultHttpClient()).getResponseAndSetAccessToken(req, access_token, jsonString, httpPatch);
        return "user";
    }
}