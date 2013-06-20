#! /usr/bin/env python

__author__ = 'jtodea, phil'

from obtain_access_token import FakeUser
from osiam import connector
import argparse
import group
import logging
import measuring
import prefill_osiam
import user
import utils
from multiprocessing import Process

# from pudb import set_trace; set_trace()


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
parser.add_argument('--iterations', help='The number of repeating runs.',
                    default=5, type=int)
parser.add_argument('-p', '--parallel', help='The number of parallel runs.',
                    default=10, type=int)
parser.add_argument('-t', '--timeout', help='If this timeout is reached a ' +
                    'request is considered as unsuccessful.', default=500,
                    type=int)
parser.add_argument('-l', '--log-directory', help='The directory to ' +
                    'store the log output.', default='/tmp')
parser.add_argument("tests", nargs='+', help='Test files to execute.')

scim = None


def create_method(test):
    """ creates a method based on the test configuration.
    Either User or Group."""
    res = test['resource']
    method_name = test['method']
    if res == "User":
        return getattr(user, method_name)
    elif res == "Group":
        return getattr(group, method_name)
    raise Exception('res {} is neither User nor Group'.format(res))


def execute_testcase(testcases, serial, parallel):
    """ Executes the defined testscases of a test sequence."""
    complete_duration = []
    print "executing {}".format(testcases)
    for test in testcases['tests']:
        method = create_method(test)
        result = method(serial, parallel)
        result['method'] = method.__name__
        complete_duration.append(result)
    return complete_duration


def write_log_header(testcases):
    logger.handlers = []
    logger.addHandler(measuring.create_filehandler(
        args.log_directory,
        testcases['name']))
    logger.info('# Results of {}'.format(testcases["name"]))
    logger.info('# {}'.format(testcases.get('description')))


def calculate_auto_generated_resource_amount():
    """Calculates the amount of auto generated resources. Auto generated
    resources are defined in the configuration part by per_call"""
    def calc(m):
        result = 1
        for s in xrange(m + 1):
            result = result + s
        return result
    serial = calc(args.iterations)
    parallel = calc(args.parallel)
    return serial * parallel


def insert_data(config):
    """ Loads the configuration block and insert the needed user, groups
    into OSIAM"""
    def get_amount(key):
        result = 0
        obj = create.get(key)
        if obj == 'per_call':
            result = calculate_auto_generated_resource_amount()
        elif obj is not None:
            result = obj
        return result

    create = config["create"]
    user_amount = get_amount('User')
    group_amount = get_amount('Group')
    logger.info('# This test is based on: {} users {} groups'.
                format(user_amount, group_amount))
    prefill_osiam.PrefillOsiam(scim).prefill(user_amount, group_amount, 0)


def determine_configuration(testcases):
    """ Loads the configuration block, inserts_data and get needed ids """
    config = testcases.get("configuration")
    if config is not None:
        insert_data(config)
        amount = calculate_auto_generated_resource_amount()
        user.get_all_user_ids(amount)
        group.get_all_group_ids(amount)


def execute_sequence(max_serial, max_parallel, test):
    """ Identify the testcases of a sequence, executes them and prints the
    log files. """
    testcases = {}
    execfile(test, testcases)
    write_log_header(testcases)
    determine_configuration(testcases)
    # durchsatz fehlt ..
    logger.info('serial*parallel;min;max;avg;timeout;error')
    print "executing sequence {}".format(test)
    for i in range(max_serial):
        for j in range(max_parallel):
            serial = i + 1
            parallel = j + 1
            result = execute_testcase(testcases, serial, parallel)
            print_result(result, serial, parallel)


def print_result(result, serial, parallel):
    for r in result:
        logger.info("{}x{}-{};{};{};{};{}%;{}%".format(serial, parallel,
                                                       r["method"],
                    r["min"],
                    r["max"],
                    r["avg"],
                    r["timeout"],
                    r["error"]))
        if r["timeout"] >= 50:
            logger.info("# {}% of requests reached the timeout of {}ms.".
                        format(r["timeout"], args.timeout))
            delete_all(scim.search_with_get_on_groups, scim.delete_group)
            delete_all(scim.search_with_get_on_users, scim.delete_user)
            exit(1)


def init_scim(server, client, client_id, username='marissa', password='koala',
              timeout=500):
    """ Getting access token and initializes profiling """
    fakeUser = FakeUser(username, password, client_id,
                        'http://' + client + ':5000/oauth2',
                        'http://' + server + ':8080/osiam-server')

    access_token = fakeUser.get_access_token()
    global scim, max_response_time
    scim = connector.SCIM('http://{}:8080/osiam-server'.format(server),
                          access_token)
    measuring.max_response_time = timeout
    user.scim = scim
    group.scim = scim


def chunks(l, n):
        """ Yield successive n-sized chunks from l.
            """
        for i in xrange(0, len(l), n):
            yield l[i:i + n]


def delete_all(search_function, delete_function):
    all_ids = utils.get_all_ids(search_function)
    to_delete = list(chunks(all_ids, 10))
    for l in to_delete:
        procs = []
        for id in l:
            p = Process(target=delete_function, args=(id,))
            procs.append(p)
            p.start()
            print "{} deleted.".format(id)
        for p in procs:
            p.join()

if __name__ == '__main__':

    args = parser.parse_args()
    init_scim(args.server, args.client, args.client_id, timeout=args.timeout)
    for t in args.tests:
        execute_sequence(args.iterations, args.parallel, t)
    delete_all(scim.search_with_get_on_groups, scim.delete_group)
    delete_all(scim.search_with_get_on_users, scim.delete_user)
