#!/bin/sh
curl --user tonr:secret -X POST -d "code=$@&grant_type=authorization_code&redirect_uri=http://localhost:8080/tonr2/demo.html" http://localhost:8080/authorization-server/oauth/token
echo -e "\n"
