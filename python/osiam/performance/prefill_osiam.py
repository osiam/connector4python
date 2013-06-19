#! /usr/bin/env python
from obtain_access_token import FakeUser
from osiam import connector
import logging
import argparse
from threading import Thread
import threading

logging.basicConfig(level=logging.DEBUG)
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
                    default='23f9452e-00a9-4cec-a086-d171374ffb42')
parser.add_argument('--group-member', help='A number of groups inside a group',
                    default=0, type=int)
parser.add_argument('--member', help='When enabled it inserts every user in ' +
                    'every group', default=False, type=bool)


class PrefillOsiam:

    def __init__(self, scim):
        self.scim = scim
        self.member = []

    def build_user(self, username):
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
            password='password')

    def build_group(self, display_name, member=None):
        return connector.SCIMGroup(displayName=display_name, members=member)

    def create_multi_value_attribute(self, val):
        return {'value': val['id']}

    def create_user(self, x):
        user = self.scim.create_user(user=self.build_user('FNORD{}'.format(x)))
        self.member.append(self.create_multi_value_attribute(user))

    def create_group_without_member(self, x):
        g = connector.SCIMGroup(displayName='group_member{}'.format(x))
        group = self.scim.create_group(g)
        self.member.append(self.create_multi_value_attribute(group))

    def create_group(self, x):
        self.scim.create_group(self.build_group('Prefect{}'.format(x),
                                                self.member))

    def prefill(self, user_amount, group_member, group_amount):
        for x in range(0, user_amount):
            Thread(target=self.create_user, args=(x,)).start()
        for x in range(0, group_member):
            Thread(target=self.create_group_without_member, args=(x,)).start()
        for x in range(0, group_amount):
            Thread(target=self.create_group, args=(x,)).start()
        for thread in threading.enumerate():
                if thread is not threading.currentThread():
                            thread.join()


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
