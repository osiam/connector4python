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


import org.springframework.stereotype.Service;

@Service
public class FilterParser {

    public FilterChain parse(String p) {
        if (SingularFilterChain.SINGULAR_CHAIN_PATTERN.matcher(p).matches())
            return new SingularFilterChain(p);
        else if (CombinedFilterChain.COMBINED_FILTER_CHAIN.matcher(p).matches())
            return new CombinedFilterChain(p);
        throw new IllegalArgumentException(p + " is not a valid filter.");
    }
}
