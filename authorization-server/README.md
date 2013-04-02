# OSIAM NG Authorization Server

This is the combined authorization and resource server of the OSIAM NG project.

Right now only PostgreSQL is supported as a database.

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

To create the database scheme you have to execute init.sql. 

This SQL-Script will create you all the needed tables as well as create a demo user called Marissa and a password 'koala'.

The database configuration is done via

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

All resource rest calls are implemented by the scim project.

https://github.com/osiam-dev/scim

All scim calls are secured by oauth2, so have to send an access_token in order to get access, e.q.:

```sh
curl -H "Authorization: Bearer {YOUR_ACCESS_TOKEN}" http://localhost:8080/authorization-server/User/{id}
```

### User Services

See https://github.com/osiam-dev/scim/blob/master/README.md#crud-user


