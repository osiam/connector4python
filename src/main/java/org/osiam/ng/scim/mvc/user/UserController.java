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

package org.osiam.ng.scim.mvc.user;

import org.codehaus.jackson.map.ObjectMapper;
import org.osiam.ng.scim.dao.SCIMUserProvisioning;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriTemplate;
import scim.schema.v2.User;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;

/**
 * This Controller is used to manage User
 * <p/>
 * http://tools.ietf.org/html/draft-ietf-scim-core-schema-00#section-6
 * <p/>
 * it is based on the SCIM 2.0 API Specification:
 * <p/>
 * http://tools.ietf.org/html/draft-ietf-scim-api-00#section-3
 *
 * @Author phil
 */
@Controller
@RequestMapping(value = "/User")
public class UserController {

    @Inject
    private SCIMUserProvisioning scimUserProvisioning;


    public void setScimUserProvisioning(SCIMUserProvisioning scimUserProvisioning) {
        this.scimUserProvisioning = scimUserProvisioning;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@PathVariable final String id) {
        return scimUserProvisioning.getById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public User createUser(HttpServletRequest request, HttpServletResponse response) {
        User user = generateUser(request);
        User createdUser = scimUserProvisioning.createUser(user);
        String requestUrl = request.getRequestURL().toString();
        URI uri = new UriTemplate("{requestUrl}/{externalId}").expand(requestUrl, createdUser.getExternalId());
        response.setHeader("Location", uri.toASCIIString());
        return createdUser;
    }

    private User generateUser(HttpServletRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        StringBuffer jb = new StringBuffer();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        try {
            return objectMapper.readValue(jb.toString(), User.class);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

    }
}