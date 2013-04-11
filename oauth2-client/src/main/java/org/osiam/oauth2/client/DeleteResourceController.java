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


import org.apache.http.client.methods.HttpDelete;
import org.osiam.oauth2.client.exceptions.UserFriendlyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.osiam.oauth2.client.UpdateResourceController.createUrl;

@Controller
public class DeleteResourceController {

    @Autowired
    private GetResponseAndCast getResponseAndCast;

    @RequestMapping(value = "/resource/delete/{id}")
    public String delete(HttpServletRequest req, @PathVariable String id, @RequestParam String access_token)
            throws ServletException, IOException, UserFriendlyException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        HttpDelete httpDelete = new HttpDelete(createUrl(req, access_token, id));
        CRUDRedirectController.userIds.remove(id);
        CRUDRedirectController.invalidUserIds.add(id);
        req.setAttribute("invalidUserIds", CRUDRedirectController.invalidUserIds);
        getResponseAndCast.getResponseAndSetAccessToken(req, access_token, null, httpDelete);
        return "user";
    }

}