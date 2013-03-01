Authorization Server
=====

The Authorization Server project handles the authorization requests. It is based on:

* Spring Security OAuth2 1.0.0.RC3

and provides

* OAuth2 Authorization Code Flow

Build and Deployment
===

To build the authorization-server run

 mvn clean install

in the authorization-server folder.

If you want to run the authorization-server in a embedded tomcat instance run
 mvn tomcat7:run

To deploy the authorization-server into a running Tomcat copy the
 authorization-server.war
into the webapp folder in your Tomcat installation.

Configuration
==

Right now every username with the password "koala" gets accepted.

The client credentials are as well hardcoded:
 * client_id=tonr
 * client_secret=secret
 * redirect_uri=http://localhost:8080/oauth2-client/accessToken

This will change very soon.

Usage
===

To get an authorization code call:

 http://localhost:8080/authorization-server/oauth/authorize?client_id=tonr&response_type=code&redirect_uri=http://localhost:8080/oauth2-client/accessToken

To get an access_token call:

 curl --user tonr:secret -X POST -d "code=code-from-rediect-uri-above&grant_type=authorization_code&redirect_uri=http://localhost:8080/oauth2-client/accessToken" \
 http://localhost:8080/authorization-server/oauth/token

The client authentication is done via Basic Access Authentication.

