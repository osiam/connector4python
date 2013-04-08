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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Controller
@RequestMapping("/crud")
public class CRUDRedirectController {
    public static Set<String> userIds = new ConcurrentSkipListSet<>();
    static {
        userIds.add("CEF9452E-00A9-4CEC-A086-D171374FFBEF");
    }

    @RequestMapping("/user/put")
    public String redirectToCreateUpdateUser(HttpServletRequest request) {
        request.setAttribute("access_token", request.getParameter("access_token"));
        return "create_update_user";
    }

    @RequestMapping("/user/patch")
    public String redirectToPatchUser(HttpServletRequest request) {
        request.setAttribute("access_token", request.getParameter("access_token"));
        return "patch_user";
    }

    @RequestMapping("/user/get")
    public String redirectToGetUser(HttpServletRequest request) {
        request.setAttribute("access_token", request.getParameter("access_token"));
        request.setAttribute("userIds", userIds);
        return "get_user";
    }


}