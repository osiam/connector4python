#osiam

OSIAM - The Open Source Identity and Access Management

#project structure

This project gets build with maven, the module structure is

* osiam-server -- is the main project it is SCIM based, web identity data store.
* scim-schema -- is a scim api for building other java rojects.
* python-connector -- is a requests based osiam connector for python.
* example-client -- is a python 2.7, flask, python-connector based example
  client for OSIAM.
* scripts -- contains a couple of script which we use ...
* documents -- contains documents, mostly in LaTeX

# Requirements

* PostgreSQL 
* Java 1.7
* Tomcat (or Jetty, ...)
* maven


See osiam-server/README.md for instrutcions to build, configure, use the
osiam-server.



