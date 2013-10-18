import requests
from requests.auth import HTTPBasicAuth
import json
__log__ = True


def doLog(func):
    def wrapped(*args, **kwargs):
        result = func(*args, **kwargs)
        if __log__:
            print 'called {} result:{}'.format(func.__name__, result)
        return result

    return wrapped


class FakeUser():

    """ This class will fake the user interaction to get an access_token.
    It needs the username and password for login and the name of client and
    its redirect uri for approval"""

    def __init__(self, username, password, client,
                 authorization_server, client_secret='secret'):
        self.username = username
        self.password = password
        self.client = client
        self.client_secret = client_secret
        self.authorization_server = authorization_server

    def get_access_token(self):
        """ returns the access_token or will crash trying """
        param = {
            'scope': 'GET POST PUT PATCH DELETE',
            'grant_type': 'password',
            'username': self.username,
            'password': self.password,
        }
        r = requests.post('{}/oauth/token'.format(self.authorization_server),
                          auth=HTTPBasicAuth(self.client, self.client_secret),
                          params=param, verify=False)
        return json.loads(r.content).get("access_token")


if __name__ == '__main__':
    a = FakeUser('marissa', 'koala', 'example-client',
                 'http://localhost:8080/osiam-auth-server', 'secret')
    print 'Access Token: {}'.format(a.get_access_token())
