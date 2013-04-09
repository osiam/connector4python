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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.osiam.oauth2.client.UpdateResourceController.createEnvAndInvokeHttpCall;

@Controller
public class PatchResourceController {

    @Autowired
    private
    GetResponseAndCast
            getResponseAndCast;


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
                                 @RequestParam String delete) throws ServletException, IOException, UserFriendlyException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Set<String>
                attributesToDelete =
                generateAttributesToDelete(delete);

        String
                jsonString =
                JsonStringGenerator.getJsonStringPatch(
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
        Constructor<HttpPatch>
                constructor =
                HttpPatch.class.getConstructor(String.class);
        return createEnvAndInvokeHttpCall(getResponseAndCast, req, access_token, idForUpdate, jsonString, constructor);
    }

    private Set<String> generateAttributesToDelete(String delete) {
        Set<String>
                attributesToDelete =
                null;
        if (delete !=
                null &&
                !delete.isEmpty()) {
            attributesToDelete =
                    new HashSet<>(Arrays.asList(delete.split(",")));
        }
        return attributesToDelete;
    }
}