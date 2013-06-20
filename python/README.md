# OSIAM performance test scripts

The OSIAM performance module contains the following scripts:

## runner.py
The runner.py is the main entry point for test execution and has the following parameter:
'--server' : The server host name, default=localhost
'--client' : The client host name, default=localhost
'--client_id' : The client ID, default=23f9452e-00a9-4cec-a086-d171374ffb42
'--iterations' : The number of repeating runs, default=5
'-p', '--parallel' : The number of parallel runs, default=10
'-t', '--timeout' : If this timeout is reached a request is considered as unsuccessful, default=500
'-l', '--log-directory' : The directory to store the log output, default=/tmp
"tests" : Test files to execute

### log file
The log file looks like this:
```csv
serial*parallel;min;max;avg;timeout;error
1x1-create;069;069;069;0%;0%
```
The first line shows the header:
serial*parallel: describes the method name and how many iteration(serial) and how often parallel the test was executed.
min, max, avg, timeout, error: are described in the [measuring.py](https://github.com/osiam/osiam/tree/master/python#measuring.py) section

The second line shows the output values.

## obtain_access_token.py
The obtain_access_token.py gets an valid access token,
based on the default user and client configuration, to be able to run the tests.

## prefill_osiam.py
The prefill_osiam.py is used to prefill the database.
The following parameters are available:
'user_amount' : The amount of user to load into OSIAM
'group_amount' : The amount of groups to load into OSIAM
'-u', '--username' : A valid username to gain access, default='marissa'
'-r', '--redirect' : A OSIAM known redirect uri, default=http://localhost:5000/oauth2
'-o', '--osiam' : The uri to OSIAM, default=http://localhost:8080/osiam-server
'-p', '--password' : The password of the user, default=koala
'-c', '--client' : The name of the client, default=23f9452e-00a9-4cec-a086-d171374ffb42
'--group-member' : A number of groups inside a group, default=0
'--member' : When enabled it inserts every user in every group, default=False

##measuring.py
The measuring.py calculates the following parameters for logging.
'min': minimum duration
'max': maximum duration
'avg': average duration
'timeout': percentage of timeouts, based on the given max response time
'error': the error amount of failed requests

## user.py
The user.py wraps the scim user functionality for measurements.

## group.py
The group.py wraps the scim group functionality for measurements.

## utils.py
The utils.py defines some methods to get user and group amount in the database do be able to cleanup
and a method to get a valid filter for search operations.


# OSIAM python connector

The python connector enables easy access to OSIAM backend

## Install
The way to install python-connector is
```sh
python setup.py install
```

## Help
for help try
```sh
python setup.py --help
```