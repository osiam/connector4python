package org.osiam.oauth2.client.dao;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import java.util.*;

public class ClientDetailsLoaderBean implements ClientDetailsService{


    @Override
    public ClientDetails loadClientByClientId(final String clientId) throws ClientRegistrationException {
        //TODO implement DAO to get real client ...
        return new ClientDetails() {
            @Override
            public String getClientId() {
                return clientId;
            }

            @Override
            public Set<String> getResourceIds() {
                Set<String> resources = new HashSet<>();
                resources.add("oauth2res");
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
                Set<String> result = new HashSet<>();
                result.add("ROLE_USER");
                result.add("READ");
                return result;
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
                blubb.add(new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return "CLIENT_ROLE";
                    }
                });
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
