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

import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.ClientResponse
import com.sun.jersey.api.client.PartialRequestBuilder
import com.sun.jersey.api.client.config.ClientConfig
import com.sun.jersey.api.client.config.DefaultClientConfig
import com.sun.jersey.api.json.JSONConfiguration

/**
 * <p>
 * This class wraps a {@link Client} to simply provide highly abstracted methods for component or system test of RESTful
 * services. It provides protected methods for generic post, put, get and delete calls to its sub classes.
 * </p>
 * 
 * <p>
 * It is thought as a super class for specialized clients that provide one method per link from the service document.
 * </p>
 * 
 * @author Michael Kutz <m.kutz@tarent.de>, 28.06.2012
 */
class RestClient {

    /**
     * Default JSON media type.
     */
    public static final def JSON_MIME_TYPE = "application/json"

    /**
     * A wrapped {@link Client} to perform the REST calls.
     */
    protected Client client

    /**
     * Base URL for all payment calls. If there is a service document it will be located there and used to construct
     * all methods of the instance.
     */
    protected String baseUri

    /**
     * All links found in the service document (if any) at the {@link #baseUri}.
     */
    protected Map<String, String> links = [:]

    /**
     * Headers that will be added in any call (see {@link #method(String, String, Object)}).
     */
    protected Map<String, String> headers = [:]

    public RestClient() {
        this("")
    }

    /**
     * Constructor.
     * 
     * @param baseUri the URI of the REST service.
     */
    public RestClient(String baseUri) {
        final ClientConfig clientConfig = new DefaultClientConfig()
        clientConfig.features.put(JSONConfiguration.FEATURE_POJO_MAPPING, true)
        this.client = Client.create(clientConfig)

        this.baseUri = baseUri
    }

    /**
     * Copy constructor for builder features (see {@link #withHeader(String, String)} for instance).
     * 
     * @param other another {@link RestClient} that should be copied.
     */
    protected RestClient(final RestClient other) {
        this.client = other.client
        this.baseUri = other.uri
        this.links = other.links
        this.headers = other.headers
    }

    /**
     * Creates a new {@link RestClient} instance with the given <code>name</code>/<code>value</code> pair added to
     * {@link #headers}. Since the original instance is not modified, it can be reused without clearing the
     * {@link #headers}.
     * 
     * @param name the header's name.
     * @param value the value to assign to the header.
     * @return a new {@link RestClient} instance with the defined header set to the defined <code>value</code>.
     */
    public RestClient withHeader(String name, String value) {
        final RestClient newRestClient = new RestClient(this)
        newRestClient.headers.put(name, value)
        return newRestClient
    }

    /**
     * Returns a new {@link RestClient} instance with the <code>contentType</code> set at the <code>Content-Type</code>
     * header.
     * 
     * @param contentType the content type name.
     * @return a new {@link RestClient}
     */
    public RestClient withContentType(String contentType) {
        return withHeader("Content-Type", contentType)
    }

    /**
     * Determines a links list form the service document found at the {@link #baseUri}.
     * 
     * @return the links and patterns contained from the service document (see {@link #getServiceDocument()}.
     */
    protected Map<String, String> getLinks() {
        if (links.isEmpty()) {
            serviceDocument?.links.each {
                links[it.rel] = it.href != null ? it.href : it.template
            }
        }
        return links
    }

    /**
     * Performs a POST call at the given <code>uri</code> with the given <code>body</code>.
     * 
     * @param uri the URI to be called.
     * @param body an object of type <code>T</code> that should be put. The object will be transformed to a JSON string
     *           using Jersey's POJO mapping feature.
     */
    protected <T> RestResponse<T> post(final String uri, final T body) {
        return call("POST", uri, body)
    }

    /**
     * Performs a PUT call at the given <code>uri</code> with the given <code>body</code>.
     * 
     * @param uri the URI to be called.
     * @param body an object of type <code>T</code> that should be put. The object will be transformed to a JSON string
     *           using Jersey's POJO mapping feature.
     */
    protected <T> RestResponse<T> put(final String uri, final T body) {
        return call("PUT", uri, body)
    }

    /**
     * Performs a GET call to the {@link #baseUri} to get the service document.
     * 
     * @return a {@link RestResponse} representing the service document.
     */
    protected RestResponse<?> getServiceDocument() {
        return get(baseUri)
    }

    /**
     * Performs a GET call at the given <code>uri</code>.
     * 
     * @param uri the URI to be called.
     */
    protected <T> RestResponse<T> get(final String uri) {
        return call("GET", uri)
    }

    /**
     * Performs a DELETE call at the given <code>uri</code>.
     * 
     * @param uri the URI to be called.
     */
    protected <T> RestResponse<T> delete(final String uri) {
        return call("DELETE", uri)
    }

    /**
     * Executes a call with the given method with mandatory headers.
     * 
     * @param method to be executed (<code>POST</code>, <code>PUT</code>, <code>GET</code> or <code>DELETE</code>).
     * @param uri the URI to be called.
     * @param body an object of type <code>T</code> that should be posted or put. The object will be transformed to a
     *           JSON string using Jersey's POJO mapping feature.
     */
    private <T> RestResponse<T> call(final String method, final String uri, final T body = null) {
        PartialRequestBuilder resourceBuilder = client.resource(uri).requestBuilder
        headers.each {
            resourceBuilder = resourceBuilder.header(it.key, it.value)
        }

        return new RestResponse<T>(resourceBuilder.method(method, ClientResponse, body))
    }
}
