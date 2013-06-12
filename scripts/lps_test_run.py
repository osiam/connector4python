__author__ = 'jtodea'

import argparse
import lps_test_contract

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

parser.add_argument('--serial', help='The number of serial runs.',
                    default=10, type=int)

parser.add_argument('--parallel', help='The number of parallel runs.',
                    default=5, type=int)


# Parse client_id and secret from file
#TODO

# Prefill osiam db
#TODO

# Test run
#TODO

if __name__ == '__main__':
    args = parser.parse_args()
    lps_test_contract.__init__(args.server, args.client, '23f9452e-00a9-4cec-a086-d171374ffb42')
    print 'run tests'
    #lps_test_contract.User().all(args.serial, args.parallel)
    #lps_test_contract.Group().all(args.serial, args.parallel)
    lps_test_contract.all(args.serial, args.parallel)
    print 'the end'