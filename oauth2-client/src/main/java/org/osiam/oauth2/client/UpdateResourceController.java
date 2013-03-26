package org.osiam.oauth2.client;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.osiam.oauth2.client.exceptions.UserFriendlyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 26.03.13
 * Time: 09:32
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class UpdateResourceController {


    private HttpClient httpClient;

    private static final Charset CHARSET = Charset.forName("UTF-8");


    public UpdateResourceController() {
        this.httpClient = new HttpClient();
    }

    @RequestMapping("/updateResource")
    public String updateResource(HttpServletRequest req, @RequestParam String externalId,
        @RequestParam String name, @RequestParam String password, @RequestParam String access_token,
            @RequestParam String idForUpdate) throws ServletException, IOException, UserFriendlyException {

        StringRequestEntity requestEntity = new StringRequestEntity(getJsonString(externalId, name, password),
                "application/json", "UTF-8");

        String environment = req.getScheme() + "://" + req.getServerName() + ":8080";
        String url = environment + "/authorization-server/User/"+ idForUpdate  + "?access_token=" + access_token;

        PutMethod put = executePutMethod(requestEntity, url);

        readJsonFromBody(req, put);

        return "user";
    }

    private void readJsonFromBody(HttpServletRequest req, PutMethod put) throws IOException, UserFriendlyException {
        try {
            JSONObject authResponse = new JSONObject(
                    new JSONTokener(new InputStreamReader(put.getResponseBodyAsStream(), CHARSET)));
            req.setAttribute("userResponse", authResponse.toString());
            req.setAttribute("LocationHeader", put.getResponseHeader("Location"));
        } catch (JSONException e) {
            if (put.getStatusCode() == 404) {
                throw new UserFriendlyException("404");
            }
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            put.releaseConnection();
        }
    }

    private PutMethod executePutMethod(StringRequestEntity requestEntity, String url) throws IOException {
        PutMethod put = new PutMethod();
        put.setURI(new URI(url, false));
        put.setRequestEntity(requestEntity);

        httpClient.executeMethod(put);
        return put;
    }

    private String getJsonString(String externalId, String name, String password) {
        try {
            return new JSONObject().put("externalId", externalId).put("userName", name).put("password", password).toString();
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}