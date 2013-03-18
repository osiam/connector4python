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
