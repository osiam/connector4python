# OSIAM NG SCIM 

This will be a SCIM 2.0 implementation, see:

* http://tools.ietf.org/html/draft-ietf-scim-core-schema-00
* http://tools.ietf.org/html/draft-ietf-scim-api-00
* http://tools.ietf.org/id/draft-hunt-scim-targeting-00.txt

for further details.

It is based on:

* Spring 3.1.3.RELEASE

Right now only:
* getting a single user
* putting a new user
* posting a new user
is implemented.
## Build 

To build the SCIM implementation run
```sh
mvn clean install
```

## Usage

You need to include this project in your project, e.q. via maven:
```xml
        <dependency>
            <groupId>org.osiam.ng</groupId>
            <artifactId>scim</artifactId>
            <version>0.1-SNAPSHOT</version>
        </dependency>
```

after that you either need to enable 
```xml
    <context:component-scan base-package="org.osiam"/>
    <context:annotation-config/>
```
or create your own bean definition of:
```Java
UserController
```
and inject 
```Java
SCIMUserProvisioning
```
via xml in your project.

### UserProvisioning
We didn't want to enforce a specific database nor a database scheme, therefor we just created the Interface 
```Java
SCIMUserProvisioning
```
which must be implemented in your application, there may will be some default provisioning beans in the future;
however, there is a reference implementation of it in:

* https://github.com/osiam-dev/osiam/blob/master/authorization-server/src/main/java/org/osiam/ng/resourceserver/dao/ScimUserProvisioningBean.java

## CRUD User

## CRUD Groups


