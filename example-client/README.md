# OSIAM NG example-client 

This is the OSIAM NG example-client project, it is a sample client for OSIAM NG
written in Python.

## Requirements

In order to build and use the example-client you need:
* Python 2.7,
* flask,
* requests,
* nose,
* mock

## Build and Start

Before you can start the demo server, you need to install the osiam-connector
via 

```sh
python setup.py install
```

since this connector is unstable right now it highly recommended to use
virtualenv for the installation process.

When you have installed the osiam-connector you can start the demo server with:

``sh
 python server/client-server.py $url/authorization-server $redirect_uri/oauth2
 
```

