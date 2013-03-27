OAuth2 Client
=====

The OAuth2 Client project is a sample application to show the OAuth2 Authorization Code Flow. It is based on:

* Servlets

and interacts with the

* Authorization Server

Build and Deployment
===

To build the oauth2-client run

 mvn clean install

in the oauth2-client folder.

If you want to run the oauth2-client bundled with the authorization-server in a embedded tomcat instance run
```sh
 mvn tomcat7:run
```

To deploy the oauth2-client into a running Tomcat copy the "oauth2-client.war" into the webapp folder in your Tomcat installation.

Usage
===

Open the following uri in your browser:
```html
 http://localhost:8080/oauth2-client
```
* Start the flow by clicking the button
* Now you will be redirected to the authentication server where you need to pass the credentials
* After that you will be asked whether you want to authorize the client application to access the requested resources
* In the end a page with the access token and other parameters will be presented or the error if the access was declined

On the last page there are additionally options to get, create and update a resource. Also the errors with code and message
are visible e.g. if a user does not exist or a update was not possible or you want to create a user twice with the same user name.