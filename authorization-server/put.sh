#!/bin/sh
curl -i -H "Accept: application/json" -H "Content-type: application/json" -H "Authorization: Bearer $@" -X PUT localhost:8080/authorization-server/User/marissa -d '{"externalId":"marissa","userName":"Arthur Dent","password":""}' && 
echo -e "\n"
