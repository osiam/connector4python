__author__ = 'jtodea'

from osiam import connector
import lps_profiling
from obtain_access_token import FakeUser
from multiprocessing import Process
import random

scim = None

def build_user(count):
    return connector.SCIMUser(
        userName='user_name{0}'.format(count + random.random()),
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


@lps_profiling.do_log
def create_user_10_times_in_a_row():
    for count in range(10):
        scim.create_user(build_user(count))
    return

@lps_profiling.do_log
def create_user_2_times_in_parallel():
    p1 = Process(target=scim.create_user(build_user(100)))
    p2 = Process(target=scim.create_user(build_user(101)))
    p1.start()
    p2.start()
    p1.terminate()
    p2.terminate()
    return


if __name__ == '__main__':
    fakeUser = FakeUser('marissa', 'koala', '23f9452e-00a9-4cec-a086-d171374ffb42',
                 'http://localhost:5000/oauth2',
                 'http://localhost:8080/osiam-server')

    access_token = fakeUser.get_access_token()
    scim = connector.SCIM(authorization_server='http://localhost:8080/osiam-server',
                      access_token=access_token)

    lps_profiling.__init__('/home/jtodea/git/osiam/scripts', 'lps_user_testplan')
    print 'run tests'
    create_user_10_times_in_a_row()
    create_user_2_times_in_parallel()
    print 'the end'