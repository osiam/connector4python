#osiam

OSIAM - The Open Source Identity and Access Management

See osiam-server/README.md for instructions on how to build, configure and use osiam-server.

#project structure

This project gets build with maven, the module structure is

* osiam-server -- is the main project. It is a SCIM based, web identity data store.
* python-connector -- is a request based osiam connector for python.
* example-client -- is a python 2.7, flask, python-connector based example
  client for OSIAM.

# Requirements

* PostgreSQL 
* Java 1.7
* Tomcat (or Jetty, ...)
* maven

# Bugtracker

Bug reporting tool and known issues can be found here:

[OSIAM bugtracker](https://github.com/osiam/osiam/issues)

# Continuos Integration

[![Build Status](https://travis-ci.org/osiam/osiam.png)](https://travis-ci.org/osiam/osiam)
