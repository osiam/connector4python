#! /usr/bin/env python

__author__ = 'jtodea, phil'

import logging
import argparse
import lps_test_contract
import prefill_osiam

logger = logging.getLogger(__name__)

parser = argparse.ArgumentParser(description='This script interpret the test' +
                                             'case definition and runs all' +
                                             'defined test cases.')
parser.add_argument('--server', help='The server host name',
                    default='localhost')
parser.add_argument('--client', help='The client host name.',
                    default='localhost')
parser.add_argument('--serial', help='The number of maximal serial runs.',
                    default=10, type=int)
parser.add_argument('--parallel', help='The number of parallel runs.',
                    default=10, type=int)
parser.add_argument("tests", nargs='+', help='Test files to execute.' +
                                             'If given argument is a ' +
                                             'directionary it will try to ' +
                                             'load all python files.')


def start_test(test, serial, parallel):
    res = test['resource']
    method_name = test['method']
    print "running {} of class {}".format(method_name, res)
    # getting class
    class_ = getattr(lps_test_contract, res)
    print "got class: {}".format(class_.__name__)
    # getting method
    method = getattr(class_(), method_name)
    return method(serial, parallel)


def identify_tests(testcases, serial, parallel):
    complete_duration = []
    print "executing {}".format(testcases)
    for test in testcases['tests']:
        complete_duration.append(start_test(test, serial, parallel))
    return {'min': min(complete_duration), 'max': max(complete_duration),
            'avg': sum(complete_duration) / len(complete_duration)}


def write_log_header(testcases):
    logger.addHandler(lps_test_contract.create_filehandler("/tmp/",
                                                           testcases['name']))
    logger.setLevel(logging.INFO)
    logger.info('# Results of {}'.format(testcases["name"]))
    try:
        logger.info('# {}'.format(testcases['description']))
    except Exception:
        pass


def insert_data(config):
    create = config["create"]
    user_amount = 0
    group_amount = 0
    if create.get('User') == 'per_call':
        user_amount = args.serial * args.parallel
    if create.get('Group') == 'per_call':
        group_amount = args.serial * args.parallel
    global scim
    prefill_osiam.prefill(user_amount, group_amount, 0)


def check_for_pre_conditions(testcases):
    try:
        config = testcases["configuration"]
        insert_data(config)
    except Exception:
        pass


def execute_sequence(max_serial, max_parallel, test):
    testcases = {}
    execfile(test, testcases)
    write_log_header(testcases)
    check_for_pre_conditions(testcases)
    # durchsatz fehlt ..
    logger.info('serial*parallel;min;max;avg')
    print "executing sequence {}".format(test)
    for i in range(max_serial):
        for j in range(max_parallel):
            serial = i + 1
            parallel = j + 1
            result = identify_tests(testcases, serial, parallel)
            logger.info("{}x{};{};{};{};".format(serial, parallel,
                                                 result["min"],
                                                 result["max"], result["avg"]))


if __name__ == '__main__':
#    load_testcases('tests.py')
    args = parser.parse_args()
    lps_test_contract.__init__(args.server, args.client,
                               '23f9452e-00a9-4cec-a086-d171374ffb42')
    for t in args.tests:
        execute_sequence(args.serial, args.parallel, t)
