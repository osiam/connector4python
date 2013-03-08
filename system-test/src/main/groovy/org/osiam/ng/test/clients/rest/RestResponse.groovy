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
package org.osiam.ng.test.clients.rest

import com.sun.jersey.api.client.ClientResponse



/**
 * Simplified response class for REST calls. This class wraps a {@link HttpResponseDecorator},
 * see {@link http://groovy.codehaus.org/modules/http-builder/doc/rest.html} for more details.
 *
 * @author Michael Kutz <m.kutz@tarent.de>, 26.06.2012
 */
class RestResponse<T> {

    /**
     * The wrapped {@link ClientResponse}.
     */
    @Delegate private final ClientResponse response

    /**
     * The response body.
     */
    private T responseBody = null

    def RestResponse(ClientResponse response) {
        this.response = response
    }

    /**
     * @return the response body. See {@link ClientResponse#getEntity(T)}.
     */
    public T getBody() {
        if (responseBody == null) {
            responseBody = response.getEntity(T)
        }
        return responseBody
    }

    /**
     * @return the value of the location header. This should be set especially for POST call responses.
     */
    public String getLocation() {
        return response.headers.location.first
    }

    /**
     * Asserts that the {@link #response}'s status code equals the <code>expected</code> and returns a boolean to make
     * this method work in Spock's <code>then:</code> blocks.
     * 
     *  @param expectedStatus the expected HTTP status code.
     *  @return true if the {@link #response}'s status code equals the <code>expected</code>.
     */
    public boolean assertStatus(int expected) {
        assert response.status == expected
        return response.status == expected
    }
}
