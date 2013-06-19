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
parser.add_argument('--client_id', help='The client ID',
                    default='23f9452e-00a9-4cec-a086-d171374ffb42')
parser.add_argument('--serial', help='The number of maximal serial runs.',
                    default=5, type=int)
parser.add_argument('--parallel', help='The number of parallel runs.',
                    default=10, type=int)
parser.add_argument("tests", nargs='+', help='Test files to execute.' +
                                             'If given argument is a ' +
                                             'directionary it will try to ' +
                                             'load all python files.')


def create_method(test):
    res = test['resource']
    method_name = test['method']
    if res == user.__class__.__name__:
        return getattr(user, method_name)
    elif res == group.__class__.__name__:
        return getattr(group, method_name)
    raise Exception('res {} is neither {} nor {}'.format(res,
                    user.__class__, group.__class__))


def identify_tests(testcases, serial, parallel):
    complete_duration = []
    print "executing {}".format(testcases)
    for test in testcases['tests']:
        method = create_method(test)
        result = method(serial, parallel)
        result['method'] = method.__name__
        complete_duration.append(result)
    return complete_duration


def write_log_header(testcases):
    logger.addHandler(lps_test_contract.create_filehandler("/tmp/",
                                                           testcases['name']))
#    logger.setLevel(logging.INFO)
    logger.info('# Results of {}'.format(testcases["name"]))
    logger.info('# {}'.format(testcases.get('description')))


def calculate_amount():
    def calc(m):
        result = 1
        for s in xrange(m + 1):
            result = result + s
        return result
    serial = calc(args.serial)
    parallel = calc(args.parallel)
    return serial * parallel


def insert_data(config):
    create = config["create"]
    user_amount = 0
    group_amount = 0
    amount = calculate_amount()

    if create.get('User') == 'per_call':
        user_amount = amount
    if create.get('Group') == 'per_call':
        group_amount = amount
    prefill_osiam.PrefillOsiam(lps_test_contract.scim).prefill(
        user_amount, group_amount, 0)


def check_for_pre_conditions(testcases):
    config = testcases.get("configuration")
    if config is not None:
        insert_data(config)
        amount = calculate_amount()
        user.get_all_user_ids(amount)
        group.get_all_group_ids(amount)


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
            print_result(result, serial, parallel)


def print_result(result, serial, parallel):
    for r in result:
        logger.info("{}x{}-{};{};{};{};".format(serial, parallel, r["method"],
                                                r["min"], r["max"], r["avg"]))

if __name__ == '__main__':
    args = parser.parse_args()
    lps_test_contract.__init__(args.server, args.client, args.client_id)
    user = lps_test_contract.User()
    group = lps_test_contract.Group()
    for t in args.tests:
        execute_sequence(args.serial, args.parallel, t)
