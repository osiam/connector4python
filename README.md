#OSIAM Connector for Python

This repository contains the OSIAM Connector for Python and some auxiliary modules that depend on the connector. For more information and documentation visit the [repo's Wiki](https://github.com/osiam/connector4python/wiki).

For more information on OSIAM have a look into the [OSIAM server repository's README.md](https://github.com/osiam/server/README.md) or visit OSIAM's homepage at [www.osiam.org](https://www.osiam.org).

#Project structure

The module structure is

* python 
    * osiam -- is the main project, the request based OSIAM connector for Python itself -> [Wiki](https://github.com/osiam/connector4python/wiki#osiam-connector-for-python)
        * performance -- our framework for running stresstests agains OSIAM, it also makes use of the connector -> [Wiki](https://github.com/osiam/connector4python/wiki#performance-test-framework-and-tests-for-osiam)
* example-client -- an example web application (Python 2.7, flask) using the API of the connector -> [Wiki](https://github.com/osiam/connector4python/wiki#sample-client-for-osiam)
* performance-tests -- are the test cases written in a small self defined DSL which are executed by the performance framework -> [Wiki](https://github.com/osiam/connector4python/wiki#performance-test-framework-and-tests-for-osiam)

# Requirements

* Python 2.7
* An up and running OSIAM instance

# Issue tracker for the connector

Bug reporting tracker and known issues can be found here:

[OSIAM Connector for Python bugtracker](https://github.com/osiam/connector4python/issues)
