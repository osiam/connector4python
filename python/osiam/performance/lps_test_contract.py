__author__ = 'jtodea'

from obtain_access_token import FakeUser
from osiam import connector
import measuring
import user
import group

scim = None


def __init__(server, client, client_id, username='marissa', password='koala',
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
