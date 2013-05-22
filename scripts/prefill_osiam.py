from obtain_access_token import FakeUser
from osiam import connector
import logging
import argparse

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
                    default='testClient')


def build_user(username):
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


def build_group(display_name):
    return connector.SCIMGroup(displayName=display_name)


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
    for x in range(0, args.user_amount):
        scim.create_user(user=build_user('FNORD{}'.format(x)))
    for x in range(0, args.group_amount):
        scim.create_group(user=build_group('Prefect{}'.format(x)))
