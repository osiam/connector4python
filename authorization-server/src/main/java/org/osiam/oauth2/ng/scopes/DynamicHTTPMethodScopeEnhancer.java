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

