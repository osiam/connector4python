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

package org.osiam.ng.scim.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.context.request.WebRequest
import spock.lang.Specification

class HandleExceptionTest extends Specification {
    def underTest = new HandleException()
    WebRequest request = Mock(WebRequest)

    def "exception result should contain a code and a description"() {
        when:
        def errorResult = new HandleException.Error("hacja", "unso")
        then:
        errorResult.code == "hacja"
        errorResult.description == "unso"
    }

    def "should generate a response entity"() {
        when:
        def result = underTest.handleConflict(new NullPointerException("Dunno"), request)
        then:
        result.getStatusCode() == HttpStatus.CONFLICT
        (result.getBody() as HandleException.Error).code == HttpStatus.CONFLICT.name()
        (result.getBody() as HandleException.Error).description == "Dunno"
    }

    def "should set status to ResourceNotFound when org.osiam.ng.scim.exceptions.ResourceNotFoundException occurs"() {
        when:
        def result = underTest.handleConflict(new ResourceNotFoundException("Dunno"), request)
        then:
        result.getStatusCode() == HttpStatus.NOT_FOUND
        (result.getBody() as HandleException.Error).code == HttpStatus.NOT_FOUND.name()
        (result.getBody() as HandleException.Error).description == "Dunno"
    }

    def "should set status to I_AM_A_TEAPOT when org.osiam.ng.scim.exceptions.SchemaUnknownException occurs"() {
        when:
        def result = underTest.handleConflict(new SchemaUnknownException(), request)
        then:
        result.getStatusCode() == HttpStatus.I_AM_A_TEAPOT
        (result.getBody() as HandleException.Error).code == HttpStatus.I_AM_A_TEAPOT.name()
        (result.getBody() as HandleException.Error).description == "Delivered schema is unknown."
    }


}
