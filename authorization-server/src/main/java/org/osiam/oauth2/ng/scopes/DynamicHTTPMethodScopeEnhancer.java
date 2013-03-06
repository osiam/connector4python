package org.osiam.oauth2.ng.scopes;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.vote.ScopeVoter;
import org.springframework.security.web.FilterInvocation;

import java.util.*;

public class DynamicHTTPMethodScopeEnhancer implements AccessDecisionVoter<Object> {

    private final ConfigAttribute dynamic = new SecurityConfig("SCOPE_DYNAMIC");
    private final ScopeVoter basedOnScopeVoter;

    public DynamicHTTPMethodScopeEnhancer(ScopeVoter basedOnScopeVoter) {
        this.basedOnScopeVoter = basedOnScopeVoter;
    }


    @Override
    public boolean supports(ConfigAttribute attribute) {
        return basedOnScopeVoter.supports(attribute);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return basedOnScopeVoter.supports(clazz);
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        Set<ConfigAttribute> dynamicConfigs = ifScopeDynamicAddMethodScope(object, attributes);
        return basedOnScopeVoter.vote(authentication, object, dynamicConfigs);
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

