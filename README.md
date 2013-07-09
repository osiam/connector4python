#OSIAM Connector for Python

This project contains the OSIAM Connector for Python and some auxiliary modules that depend on the connector.

For more information on OSIAM have a look into the [OSIAM server repository's README.md](https://github.com/osiam/server/README.md) or visit OSIAM's homepage at [https//osiam.org](https://osiam.org).

#project structure

This project gets build with maven, the module structure is

* python -- is the main project, the request based OSIAM connector for Python itself.
* example-client -- an example web application (Python 2.7, flask) using the API of the connector.
* performance-tests -- our framework for running stresstests agains OSIAM, it also makes use of the connector.

# Requirements

* Python 2.7
* An up and running OSIAM instance
* ...

# Issue tracker for the connector

Bug reporting tracker and known issues can be found here:

[OSIAM Connector for Python bugtracker](https://github.com/osiam/connector4python/issues)
