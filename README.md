# OSIAM NG Authorization Server

This is the combined authorization and resource server of the OSIAM NG project.


## Build and Deployment

To build the authorization-server run
```sh
mvn clean install
```

in the authorization-server folder.

If you want to run the authorization-server in a embedded tomcat instance run
```sh
 mvn tomcat7:run
```

To deploy the authorization-server into a running Tomcat copy the "authorization-server.war" into the webapp folder in your Tomcat installation.


## Authorization Server

The Authorization Server project handles the authorization requests. It is based on:

* Spring Security OAuth2 1.0.0.RC3

and provides

* [OAuth2 Authorization Code Flow](http://tools.ietf.org/html/rfc6749#section-4.1)


### Configuration

Right now every username with the password "koala" gets accepted.

The client credentials are as well hardcoded:
 * client_id=tonr
 * client_secret=secret
 * redirect_uri=http://localhost:8080/oauth2-client/accessToken

This will change very soon.


### Usage

To get an authorization code call:

<http://localhost:8080/authorization-server/oauth/authorize?client_id=tonr&response_type=code&redirect_uri=http://localhost:8080/oauth2-client/accessToken>

To get an access_token call:

```sh
curl --user tonr:secret -X POST -d "code=$CODE&grant_type=authorization_code&redirect_uri=http://localhost:8080/oauth2-client/accessToken" \
 http://localhost:8080/authorization-server/oauth/token
```

The client authentication is done via [Basic Access Authentication](http://tools.ietf.org/html/rfc2617).


## Resource Server

To get an overview of all known resources call:

<http://localhost:8080/authorization-server>


### Getting attributes

Attributes are secured, so, you need to provide a valid access token.

With 

<http://localhost:8080/authorization-server/secured/attributes?access_token=$YOUR_ACCESS_TOKEN>

or 

```sh
curl -H "Authorization: Bearer {YOUR_ACCESS_TOKEN}" http://localhost:8080/authorization-server/secured/attributes
```

you will get a list of accessible attributes.

If you want to get a single attribute, you need to extend attributes with an id:

<http://localhost:8080/authorization-server/secured/attributes/{id}?access_token=$YOUR_ACCESS_TOKEN>

or 

```sh
curl -H "Authorization: Bearer {YOUR_ACCESS_TOKEN}" http://localhost:8080/authorization-server/secured/attributes/{id}
```
