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

package org.osiam.oauth2.ng.scopes;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import java.util.*;

/**
 * This AccessDecisionVoter is based upon an other AccessDecisionVoter given in the constructor.
 * <p/>
 * The only purpose of this class is to enhance attributes with a SCOPE_HTTP-Method in vote when SCOPE_DYNAMIC is set.
 *
 * @author phil
 */
public class DynamicHTTPMethodScopeEnhancer implements AccessDecisionVoter<Object> {

    private final ConfigAttribute dynamic = new SecurityConfig("SCOPE_DYNAMIC");
    private final AccessDecisionVoter<Object> basedOnVoter;

    public DynamicHTTPMethodScopeEnhancer(final AccessDecisionVoter<Object> basedOnVoter) {
        this.basedOnVoter = basedOnVoter;
    }


    @Override
    public boolean supports(final ConfigAttribute attribute) {
        return basedOnVoter.supports(attribute);
    }

    @Override
    public boolean supports(final Class<?> clazz) {
        return basedOnVoter.supports(clazz);
    }

    @Override
    public int vote(final Authentication authentication,
                    final Object object,
                    final Collection<ConfigAttribute> attributes) {
        Set<ConfigAttribute> dynamicConfigs = ifScopeDynamicAddMethodScope(object, attributes);
        return basedOnVoter.vote(authentication, object, dynamicConfigs);
    }

    private Set<ConfigAttribute> ifScopeDynamicAddMethodScope(final Object object,
                                                              final Collection<ConfigAttribute> attributes) {
        Set<ConfigAttribute> dynamicConfigs = new HashSet<>(attributes);
        if (object instanceof FilterInvocation && dynamicConfigs.remove(dynamic)) {
            addMethodScope(object, dynamicConfigs);
        }
        return dynamicConfigs;
    }

    private void addMethodScope(final Object object,
                                final Set<ConfigAttribute> dynamicConfigs) {
        final FilterInvocation f = (FilterInvocation) object;
        dynamicConfigs.add(new SecurityConfig("SCOPE_" + f.getRequest().getMethod().toUpperCase()));
    }
}

