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

package org.osiam.oauth2.client.service

import spock.lang.Specification

class ClientDetailsLoadingBeanTest extends Specification {
    def underTest = new ClientDetailsLoadingBean()

    def "should return one fake client"(){
        when:
        def result = underTest.loadClientByClientId("client!")
        then:
        result.clientId == "client!"
        result.isScoped() == true
        result.isSecretRequired() == true
        result.getAccessTokenValiditySeconds() == 1337
        result.getRefreshTokenValiditySeconds() == 1337

        result.getScope().contains("GET")
        result.getScope().size() == 1
        result.getResourceIds().size() == 0
        result.getAuthorizedGrantTypes().size() == 3
        result.getRegisteredRedirectUri().size() > 1
        !result.getAuthorities()
        !result.getAdditionalInformation()
        result.getClientSecret() == "secret"


    }
}
