package org.osiam.oauth2.client.data;

import java.util.ArrayList;


public class ClientEntity {

    private String clientId;
    private String hashedSecret;
    private String redirect;
    private ArrayList<String> scopes;

    public ClientEntity(){};

    public ClientEntity(String clientId, String hashedSecret, String redirect, ArrayList<String> scopes) {
        this.clientId = clientId;
        this.hashedSecret = hashedSecret;
        this.redirect = redirect;
        this.scopes = new ArrayList<>(scopes);
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getHashedSecret() {
        return hashedSecret;
    }

    public void setHashedSecret(String hashedSecret) {
        this.hashedSecret = hashedSecret;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public ArrayList<String> getScopes() {
        return scopes;
    }

    public void setScopes(ArrayList<String> scopes) {
        this.scopes = scopes;
    }
}
