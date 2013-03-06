package org.osiam.oauth2.client.service;

import org.osiam.ng.hash.CalculateHash;
import org.osiam.oauth2.client.data.ClientEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;


@Named
public class ClientRegistrationBean{

   @Inject
   private ClientDAU clientDAU;

    public void register(String clientId, String clientSecret, String redirect, ArrayList<String> scopes) {
        ClientEntity entity = new ClientEntity(clientId, hashClientSecret(clientSecret), redirect, scopes);
        clientDAU.create(entity);
    }

    private String hashClientSecret(String clientSecret) {
        String salt = CalculateHash.calculateSalt();
        return CalculateHash.calculateWithSalt(salt, clientSecret);
    }

    public void setClientDAU(ClientDAU clientDAU) {
        this.clientDAU = clientDAU;
    }
}
