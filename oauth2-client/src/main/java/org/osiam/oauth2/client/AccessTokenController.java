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

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/accessToken")
public class AccessTokenController {

    private static final String PARAM_CODE = "code";
    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final String CLIENT_ID = "testClient";
    private static final String CLIENT_SECRET = "secret";
    private final Logger logger = Logger.getLogger(AccessTokenController.class.getName());
    @Autowired
    private GenerateClient generateClient;


    public AccessTokenController() {

    }

    @RequestMapping(value = "access_denied", params = {"error", "error_description"})
    public String accessDenied(@RequestParam String error, @RequestParam String error_description) {
        logger.info(error + " is " + error_description);
        return "error";

    }

    @RequestMapping(params = PARAM_CODE)
    public String doGet(HttpServletRequest req) throws ServletException, IOException {
        String code = req.getParameter(PARAM_CODE);
        String environment = req.getScheme() + "://" + req.getServerName() + ":8080";
        String tokenUrl = environment + "/authorization-server/oauth/token";
        String combined = CLIENT_ID + ":" + CLIENT_SECRET;
        String redirectUri = req.getScheme() + "://" + req.getServerName() + ":8080" + "/oauth2-client/accessToken";
        sendAuthCode(code, tokenUrl, combined, redirectUri, req);
        addAttributesToHttpRequest(req, CLIENT_ID, CLIENT_SECRET);
        return "parameter";
    }

    private void sendAuthCode(String code, String tokenUrl, String combined, String redirectUri, HttpServletRequest req)
            throws IOException {
        HttpPost post = new HttpPost(tokenUrl);

        addPostMethodParameter(post, code, combined, redirectUri);
        HttpClient defaultHttpClient = generateClient.getClient();
        HttpResponse response = defaultHttpClient.execute(post);
        addJsonResponseToHttpRequest(response, req);
        req.setAttribute(PARAM_CODE, code);
        req.setAttribute("redirect_uri", redirectUri);
    }

    private void addAttributesToHttpRequest(HttpServletRequest req, String clientId, String clientSecret) {
        req.setAttribute("client_id", clientId);
        req.setAttribute("client_secret", clientSecret);
    }

    private void addJsonResponseToHttpRequest(HttpResponse post, HttpServletRequest req) throws IOException {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(post.getEntity().getContent(), CHARSET);
            JSONObject authResponse = new JSONObject(new JSONTokener(inputStreamReader));
            req.setAttribute("access_token", authResponse.get("access_token"));
            req.setAttribute("response", authResponse.toString());
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private void addPostMethodParameter(HttpEntityEnclosingRequestBase post, String code, String combined,
                                        String redirectUri) throws UnsupportedEncodingException {
        String encoding = new String(Base64.encodeBase64(combined.getBytes(CHARSET)), CHARSET);
        post.addHeader("Authorization", "Basic " + encoding);
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair(PARAM_CODE,code));
        nameValuePairs.add(new BasicNameValuePair("grant_type", "authorization_code"));
        nameValuePairs.add(new BasicNameValuePair("redirect_uri", redirectUri));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    }
}
