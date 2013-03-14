osiam
=====

OSIAM - The Open Source Identity and Access Management

initialize project
==

OSIAM has the folowing submodules:

- scim // A SCIM 2.0 implementation

to initialize them you have to call

```sh
 git submodule init
 git submodule update
```

project structure
=================

This project gets build with maven, the module structure is

* authorization-server -- will be an OAuth2 Authorization Server 
* oauth2-client -- will be an OAuth2 Client sample application
* parent -- the parent pom is used in every subproject
* test-support -- a pom project to bundle test dependencies
* system-tests -- automated acceptance test project

