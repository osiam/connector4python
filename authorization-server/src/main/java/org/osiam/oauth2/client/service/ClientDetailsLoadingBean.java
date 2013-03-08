package org.osiam.oauth2.client.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * This class is used by clientAuthenticationManager in Spring to authenticate a client when trading an auth_code to an
 * access_token.
 */
@Component("clientDetails")
public class ClientDetailsLoadingBean implements ClientDetailsService {

    @Override
    public ClientDetails loadClientByClientId(final String clientId) {
        //TODO implement DAO to get real client ...
        return new ClientDetails() {

            @Override
            public String getClientId() {
                return clientId;
            }

            @Override
            public Set<String> getResourceIds() {
                Set<String> resources = new HashSet<>();
//                resources.add("oauth2res");
                return resources;
            }

            @Override
            public boolean isSecretRequired() {
                return true;
            }

            @Override
            public String getClientSecret() {
                return "secret";
            }

            @Override
            public boolean isScoped() {
                return true;
            }

            @Override
            public Set<String> getScope() {
                Set<String> scopes = new HashSet<>();
                scopes.add("GET");
//                scopes.add("DYNAMIC");
                return scopes;
            }

            @Override
            public Set<String> getAuthorizedGrantTypes() {
                Set<String> grants = new HashSet<>();
                grants.add("authorization_code");
                grants.add("implicit");
                grants.add("refresh-token");
                return grants;
            }

            @Override
            public Set<String> getRegisteredRedirectUri() {

                return new HashSet<>(Arrays.asList("http://localhost:8080/oauth2-client/accessToken"));
            }

            @Override
            public Collection<GrantedAuthority> getAuthorities() {
                Collection<GrantedAuthority> blubb = new ArrayList<>();
                return blubb;

            }

            @Override
            public Integer getAccessTokenValiditySeconds() {
                return 1337;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public Integer getRefreshTokenValiditySeconds() {
                return 1337;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public Map<String, Object> getAdditionalInformation() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };
    }


}
