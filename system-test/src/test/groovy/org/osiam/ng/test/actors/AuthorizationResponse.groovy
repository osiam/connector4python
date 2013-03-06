package org.osiam.ng.test.actors

import java.nio.charset.Charset

import org.apache.http.NameValuePair
import org.apache.http.client.utils.URLEncodedUtils
import org.osiam.ng.test.clients.http.HttpResponse

/**
 * Class representing a response to a authorization request.
 * 
 * @author Michael Kutz, tarent Solutions GmbH, 28.02.2013
 */
class AuthorizationResponse {

    private static final CODE_PARAMETER_NAME = "code"

    private static final STATE_PARAMETER_NAME = "state"

    private final List<NameValuePair> queryParameters

    public AuthorizationResponse(HttpResponse httpResponse) {
        this.queryParameters = URLEncodedUtils.parse(httpResponse.getLocation(), Charset.forName("UTF-8"))
    }

    public String getCode() {
        return getQueryParameterValue(CODE_PARAMETER_NAME)
    }

    public String getState() {
        return getQueryParameterValue(STATE_PARAMETER_NAME)
    }

    private String getQueryParameterValue(String queryParameterName) {
        NameValuePair quertyParameter = queryParameters.find {NameValuePair queryParameter ->
            queryParameter.name == "code"
        }
        return quertyParameter?.value
    }
}
