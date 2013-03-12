package org.osiam.oauth2.user.please.remove.me.asap;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component("userDetailsService")
/**
 * Mainly used for demonstration, it is used to validate the user login, before he grants or denies the client access
 * to a resource.
 */
public class PseudoAuthenticationBean implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(final String username) {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                Collection<GrantedAuthority> blubb = new ArrayList<>();
                blubb.add(new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return "ROLE_USER";
                    }
                });
                return blubb;
            }

            @Override
            public String getPassword() {
                return "koala"; // To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getUsername() {
                return username; // To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isAccountNonExpired() {
                return true; // To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isAccountNonLocked() {
                return true; // To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true; // To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isEnabled() {
                return true; // To change body of implemented methods use File | Settings | File Templates.
            }
        }; // To change body of implemented methods use File | Settings | File Templates.
    }
}
