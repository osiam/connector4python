/*
 * Copyright 2013
 *     tarent AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.osiam.ng.resourceserver;

import org.springframework.hateoas.ResourceSupport;

/**
 * This is a rest implementation of @link{Attribute} it enhances a attribute with a link.
 */
public class RestAttribute extends ResourceSupport {
    private Attribute attribute;

    public RestAttribute(String name, Object value) {
        this.attribute = new Attribute(name, value);
    }

    public String getKey() {
        return attribute.getKey();
    }

    public Object getValue() {
        return attribute.getValue();
    }


}
