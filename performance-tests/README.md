# OSIAM performance tests

The tests are written in a self defined DSL, which looks like this:

```python
 name = 'sequence_10k'
 description = 'The results of this test will show how long the system needed to reach the abort criteria.'
 configuration = {'create': {'User': 10000, 'Group': 1000}}

 tests = [{'resource': 'User', 'method': 'search'},
          {'resource': 'User', 'method': 'update'},
          {'resource': 'User', 'method': 'replace'},
          {'resource': 'User', 'method': 'delete'},
          {'resource': 'Group', 'method': 'search'}]
```

## name
The name is the test name

## description
This a optional test description

## configuration
The configuration is used to prefill the database with the given amount, like in the example above.
In this case 10k users and 1k groups are created prior to the test run.

Alternatively the configuration can look like this:
```python
configuration = {'create': {'Group': 'per_call'}}
```
per_call means that the needed amount is calculated depending on the iterations and parallel executions

## tests
The tests section is a list of tests or a single test that will be executed.
The resource determines whether it is a user or group test
The method determines which method will be executed from the user or group tests