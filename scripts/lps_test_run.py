__author__ = 'jtodea'

import logging
import lps_profiling
import argparse
import lps_test_contract

logger = logging.getLogger(__name__)


parser = argparse.ArgumentParser(description='This is a script to run the' +
                                             'OSIAM lps test suit.')

parser.add_argument("--user_amount", type=int,
                    help='The amount of user to load into OSIAM.')

parser.add_argument("--group_amount", type=int,
                    help='The amount of groups to load into OSIAM.')

parser.add_argument('--group-member', help='A number of groups inside a group',
                    default=0, type=int)

parser.add_argument('--member', help='When enabled it inserts every user in ' +
                    'every group', default=False, type=bool)

parser.add_argument('--server', help='The server host name',
                    default='localhost')

parser.add_argument('--client', help='The client host name.',
                    default='localhost')

parser.add_argument('--serial', help='The number of maximal serial runs.',
                    default=10, type=int)

parser.add_argument('--parallel', help='The number of parallel runs.',
                    default=10, type=int)


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


def execute_sequence(max_serial, max_parallel, test):
    testcases = {}
    execfile(test, testcases)
    logger.addHandler(lps_profiling.create_filehandler("/tmp/", test))
    logger.setLevel(logging.INFO)
    #durchsatz fehlt ..
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
    execute_sequence(args.serial, args.parallel, 'tests.py')
