/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License, version 3 
 * as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.osiam.ng.test.clients.http

import org.apache.http.HttpRequest
import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.params.HttpClientParams
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.message.BasicNameValuePair

/**
 * <p>
 * This class wraps a {@link HttpClient} to simply provide highly abstracted methods for component or system test of
 * HTTP interfaces (for instance to verify SAML usage). It provides protected methods for generic post and get calls to
 * its sub classes.
 * </p>
 * 
 * <p>
 * It is thought as a super class for specialized clients that provide one method per link from the service document.
 * </p>
 *
 * @author Michael Kutz <m.kutz@tarent.de>, 02.10.2012
 */
class HttpClient {

    protected DefaultHttpClient httpClient = new DefaultHttpClient()

    HttpClient() {
        //HttpClientParams.setRedirecting(httpClient.getParams(), true)
    }

    /**
     * Executes an HTTP GET method to the given <code>url</code>.
     * 
     * @param url the URL to call.
     * @return the resulting {@link HttpResponse}.
     */
    HttpResponse get(String url) {
        return execute(new HttpGet(url))
    }

    /**
     * Executes an HTTP POST method to the given <code>url</code> with the given parameters.
     * 
     * @param url the URL to call.
     * @param parameters the parameters to send.
     * @return the resulting {@link HttpResponse}.
     */
    HttpResponse post(String url, Map<String, String> parameters) {
        HttpPost request = new HttpPost(url)

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(parameters.size())
        parameters.each {
            nameValuePairs.add(new BasicNameValuePair(it.key, it.value))
        }
        request.entity = new UrlEncodedFormEntity(nameValuePairs)

        return execute(request)
    }

    /**
     * Executes the given <code>request</code>.
     * 
     * @param request the request to be executed.
     * @return the resulting {@link HttpResponse}.
     */
    private HttpResponse execute(HttpRequest request) {
        return new HttpResponse(httpClient.execute(request))
    }
}
