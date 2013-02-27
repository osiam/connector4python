#!/bin/sh
CODE=8cjsm6
curl -X POST -d "client_id=tonr&client_secret=secret&code=$CODE&grant_type=authorization_code&redirect_uri=http://localhost:8080/tonr2/demo.html" http://localhost:8080/authorization-server/oauth/token
