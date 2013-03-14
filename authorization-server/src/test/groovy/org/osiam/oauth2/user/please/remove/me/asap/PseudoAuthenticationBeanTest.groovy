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

package org.osiam.oauth2.user.please.remove.me.asap

import spock.lang.Specification

class PseudoAuthenticationBeanTest extends Specification {
    def underTest = new PseudoAuthenticationBean()

    def "should return an activated account"(){
        when:
           def result = underTest.loadUserByUsername("blubb")
        then:
        result.accountNonExpired
        result.accountNonLocked
        result.credentialsNonExpired
        result.enabled
    }

    def "should dummy data of account"(){
        when:
        def result = underTest.loadUserByUsername("blubb")
        then:
        result.password == "koala"
        result.username == "blubb"
        result.getAuthorities().size() == 1
        result.getAuthorities().iterator().next().authority == "ROLE_USER"
    }

}
