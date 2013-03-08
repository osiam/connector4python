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

import static org.apache.http.HttpHeaders.*
import groovy.json.JsonSlurper
import groovy.util.slurpersupport.GPathResult

import org.ccil.cowan.tagsoup.Parser


/**
 * Simplified response class for HTTP calls. This class wraps a {@link org.apache.http.HttpResponse}.
 *
 * @author Michael Kutz <m.kutz@tarent.de>, 02.10.2012
 */
class HttpResponse {

    /**
     * The wrapped org.apache.http.HttpResponse object.
     */
    @Delegate private final org.apache.http.HttpResponse response

    HttpResponse(org.apache.http.HttpResponse response) {
        this.response = response
    }

    /**
     * @return the {@link #response}'s status code.
     */
    int getStatus() {
        return response.getStatusLine().statusCode
    }

    /**
     * Returns the HTTP {@link #response}'s entity content (see {@link org.apache.http.HttpResponse#getEntity()} and
     * {@link org.apache.http.HttpEntity#getContent()}) and returns it as a {@link GPathResult}. Since {@link Parser}
     * is utilized this is also robust for not well-formed HTML. 
     * 
     * @return the {@link #response}'s body as a {@link GPathResult}.
     */
    public GPathResult getXmlBody() {
        XmlSlurper slurper = new XmlSlurper(new Parser())
        return slurper.parse(body)
    }

    /**
     * Returns the HTTP {@link #response}'s entity content (see {@link org.apache.http.HttpResponse#getEntity()} and
     * {@link org.apache.http.HttpEntity#getContent()}) and returns it as a {@link Map} structure.
     * 
     * @return the {@link #response}'s body as a {@link Map} structure.
     */
    public getJsonBody() {
        JsonSlurper slurper = new JsonSlurper()
        return slurper.parse(body)
    }

    private Reader getBody() {
        return new InputStreamReader(response.entity.content)
    }

    /**
     * @return the {@link #response}'s location header value.
     */
    String getLocation() {
        return getHeaderValue(LOCATION)
    }

    /**
     * @return the {@link #response}'s content type header value.
     */
    String getContentType() {
        return getHeaderValue(CONTENT_TYPE)
    }

    /**
     * @return the value of the first header named <code>headerName</code> as a String. If the header is not present
     *      <code>null</code> is returned. 
     */
    private String getHeaderValue(String headerName) {
        return response.getFirstHeader(headerName)?.value
    }
}
