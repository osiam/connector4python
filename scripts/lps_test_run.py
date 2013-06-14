#! /usr/bin/env python

__author__ = 'jtodea'

import argparse
import lps_test_contract

parser = argparse.ArgumentParser(description='This script interpret the test' +
                                             'case definition and runs all' +
                                             'defined test cases.')

parser.add_argument('--server', help='The server host name',
                    default='localhost')

parser.add_argument('--client', help='The client host name.',
                    default='localhost')

parser.add_argument('--test_case_definition', help='The script which defines the' +
                                                   'test cases to run.',
                    default='/home/jtodea/git/osiam/scripts/lps_test_case_definition.py')



# Parse client_id and secret from file
#TODO

# Test run
def start_test(test):
    resource = test['resource'].title()
    method = test['method']
    serial = test['serial']
    parallel = test['parallel']
    exec 'lps_test_contract.{0}().{1}({2}, {3})'.format(resource, method, serial, parallel)


def identify_tests(testcases):
    for test in testcases['tests']:
        start_test(test)


def load_testcases(path_to_file):
    testcases = {}
    execfile(path_to_file, testcases)
    identify_tests(testcases)


if __name__ == '__main__':
    args = parser.parse_args()
    lps_test_contract.__init__(args.server, args.client, '23f9452e-00a9-4cec-a086-d171374ffb42')
    print 'Starting test run.'
    load_testcases(args.test_case_definition)
    print 'Test run finished.'