package org.osiam.oauth2.client.service

import org.osiam.ng.hash.CalculateHash
import spock.lang.Specification

/**
 * Tests to verify the behaviour on registration.
 */
class ClientRegistrationBeanTest extends Specification {
    def underTest = new ClientRegistrationBean()
    def clientDAU = Mock(ClientDAU)

    def setup(){
        underTest.setClientDAU(clientDAU)
        CalculateHash.instance = Mock(CalculateHash)
    }
    def "a client should send at least client_id, client_secret, redirect_uri, scopes on registration"(){
        given:
        def clientId = "id"
        def clientSecret = "secret"
        def redirectUri = "uri"
        def scopes = [ "s1", "s2"]

        when:
          underTest.register(clientId, clientSecret, redirectUri, scopes)
        then:
        1 * clientDAU.create(_)

    }

    def "the secret of a client should be hashed and salted before saving it into a database"(){
        given:
        def clientId = "id"
        def clientSecret = "secret"
        def redirectUri = "uri"
        def scopes = [ "s1", "s2"]

        when:
        underTest.register(clientId, clientSecret, redirectUri, scopes)
        then:
        1 * CalculateHash.instance._calculateSalt() >> "salt"
        1 * CalculateHash.instance.calculateHash(("saltsecret"))
        1 * clientDAU.create(_)

    }



}
