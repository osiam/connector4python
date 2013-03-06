package org.osiam.oauth2.client;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * Created with IntelliJ IDEA.
 * User: phil
 * Date: 06.03.13
 * Time: 13:18
 * To change this template use File | Settings | File Templates.
 */
public class HTTPConnectionWraper {
    static HTTPConnectionWraper instance = new HTTPConnectionWraper();

    public static HttpClient createClient() {
        return instance._createHttpClient();
    }

    private HttpClient _createHttpClient() {
        return new HttpClient();
    }

    public static PostMethod createPost() {
        return instance._createPost();
    }

    private PostMethod _createPost() {
        return new PostMethod();
    }
}
