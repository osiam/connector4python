#osiam

OSIAM - The Open Source Identity and Access Management

See osiam-server/README.md for instrutcions on how to build, configure and use osiam-server.

#project structure

This project gets build with maven, the module structure is

* osiam-server -- is the main project. It is a SCIM based, web identity data store.
* scim-schema -- is a scim api for building other java projects.
* python-connector -- is a request based osiam connector for python.
* example-client -- is a python 2.7, flask, python-connector based example
  client for OSIAM.
* scripts -- contains a couple of scripts which we use ...
* documents -- contains documents, mostly in LaTeX

# Requirements

* PostgreSQL 
* Java 1.7
* Tomcat (or Jetty, ...)
* maven

# Bugtracker

Bug reporting tool and known issues can be found here:

[OSIAM bugtracker](https://github.com/osiam/osiam/issues)