System-Test
=====

The System Test project includes automated acceptance tests. Based on:

* Geb (with selenium driver)
* Groovy
* Spock

Precondition
==

A tomcat instance with the deployed authorization-server must be started previously.

More details [here](https://github.com/osiam-dev/osiam/tree/master/authorization-server "Authorization Server") 

Usage
===

To execute the system tests run:
```sh
 mvn clean install -Psystem-test -Plocal
```
* The profile **system-test** will enable the execution
* The profile **local** will set the hostname to localhost