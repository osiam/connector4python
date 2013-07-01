#! /usr/bin/env python
from obtain_access_token import FakeUser
from osiam import connector
import logging
import argparse
from multiprocessing import Process
import uuid

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

parser = argparse.ArgumentParser(description='This is a script to load a' +
                                 'certain amount of User and Groups into a' +
                                 ' OSIAM System')
parser.add_argument("user_amount", type=int,
                    help='The amount of user to load into OSIAM.')
parser.add_argument("group_amount", type=int,
                    help='The amount of groups to load into OSIAM')
parser.add_argument('-u', '--username', help='A valid username to gain access',
                    default='marissa')
parser.add_argument('-r', '--redirect', help='A OSIAM known redirect uri.',
                    default='http://localhost:5000/oauth2')
parser.add_argument('-o', '--osiam', help='The uri to OSIAM.',
                    default='http://localhost:8080/osiam-server')
parser.add_argument('-p', '--password', help='The password of the user.',
                    default='koala')
parser.add_argument('-c', '--client', help='The name of the client.',
                    default='example-client')
parser.add_argument('--group-member', help='A number of groups inside a group',
                    default=0, type=int)
parser.add_argument('--member', help='When enabled it inserts every user in ' +
                    'every group', default=False, type=bool)


class PrefillOsiam:

    def __init__(self, scim):
        self.scim = scim
        self.member = []

    def build_user(self, username):
        email = [{'value': username + "@xxx.xxx",
                  'primary': True}]
        return connector.SCIMUser(
            userName=username,
            displayName='displayname',
            nickName='nickname',
            profileUrl='Profileurl',
            title='title',
            userType='usertype',
            preferredLanguage='preferredlanguage',
            locale='locale',
            timezone='timezone',
            active=True,
            password='password',
            emails=email)

    def build_group(self, display_name, member=None):
        return connector.SCIMGroup(displayName=display_name, members=member)

    def create_multi_value_attribute(self, val):
        return {'value': val.get('id')}

    def create_user(self):
        user = self.scim.create_user(user=self.build_user('FNORD{}'.format(
            uuid.uuid4())))
        self.member.append(self.create_multi_value_attribute(user))

    def create_group_without_member(self):
        g = connector.SCIMGroup(
            displayName='group_member{}'.format(uuid.uuid4()))
        group = self.scim.create_group(g)
        self.member.append(self.create_multi_value_attribute(group))

    def create_group(self):
        self.scim.create_group(
            self.build_group('Prefect{}'.format(uuid.uuid4()),
                             self.member))

    def prefill(self, user_amount, group_member, group_amount):
        def start_process_group(amount, function):
            """Work aroundi: Our database connection is normally
            configured to allow 10 connections. Therefor we just send <= 10
            requests parallel."""
            def start_process(f):
                p = Process(target=f)
                procs.append(p)
                p.start()

            while True:
                procs = []
                if (amount >= 10):
                    block = 10
                else:
                    block = amount
                amount = amount - block
                for x in range(block):
                    start_process(function)
                for p in procs:
                    p.join()
                if amount <= 0:
                    break

        start_process_group(user_amount, self.create_user)
        start_process_group(group_member, self.create_group_without_member)
        start_process_group(group_amount, self.create_group)

if __name__ == '__main__':
    args = parser.parse_args()
    logger.info('{} will be used to gain access'.format(args.username))
    logger.info('Inserting {} amount of User'.format(args.user_amount))
    logger.info('Inserting {} amount of Groups'.format(args.group_amount))
    fakeUser = FakeUser(args.username, args.password, args.client,
                        args.redirect, args.osiam)
    access_token = fakeUser.get_access_token()
    scim = connector.SCIM(authorization_server=args.osiam,
                          access_token=access_token)
    PrefillOsiam(scim).prefill(args.user_amount, args.group_member,
                               args.group_amount)
