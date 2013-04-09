package org.osiam.oauth2.client;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Component;


@Component
public class GenerateClient {


    public HttpClient getClient() {
        return new DefaultHttpClient();
    }
}
