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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.StringEntity;
import org.codehaus.jackson.map.ObjectMapper;
import org.osiam.oauth2.client.exceptions.UserFriendlyException;
import scim.schema.v2.User;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class GetResponseAndCast {


    private final HttpClient httpClient;

    public GetResponseAndCast(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void getResponseAndSetAccessToken(HttpServletRequest req, String access_token, String jsonString, HttpEntityEnclosingRequestBase request) throws IOException, UserFriendlyException {
        HttpResponse response = getHttpResponse(jsonString, request);
        readJsonFromBody(req, response);
        req.setAttribute("access_token", access_token);
    }

    private HttpResponse getHttpResponse(String jsonString, HttpEntityEnclosingRequestBase request) throws IOException {
        request.addHeader("accept", "application/json");
        StringEntity input = new StringEntity(jsonString);
        input.setContentType("application/json");
        request.setEntity(input);
        return httpClient.execute(request);
    }

    private void readJsonFromBody(HttpServletRequest req, HttpResponse post) throws IOException, UserFriendlyException {

//        if (post.getStatusLine().getStatusCode() > 399) {
//            throw new UserFriendlyException(String.valueOf(post.getStatusLine().getStatusCode()));
//        }
        setAttributeAndCastUser(req, post);
    }

    private void setAttributeAndCastUser(HttpServletRequest req, HttpResponse post) throws IOException {
        String response = setUserId(post.getEntity().getContent());
        req.setAttribute("userResponse", response);
        req.setAttribute("LocationHeader", post.getFirstHeader("Location"));

    }

    private String setUserId(InputStream response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String bla = inputStreamToString(response);
        if (response != null) {
            try {
                User user = objectMapper.readValue(bla, User.class);
                CRUDRedirectController.userIds.add(user.getId());
                return objectMapper.writeValueAsString(user);
            } catch (Exception e) {
                return bla;
            }
        }
        return bla;
    }

    private String inputStreamToString(InputStream response) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = response.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return new String(baos.toByteArray());
    }

}