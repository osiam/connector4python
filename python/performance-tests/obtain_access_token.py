import requests
import re

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

    def __init__(self, username, password, client, redirect,
                 authorization_server):
        self.username = username
        self.password = password
        self.client = client
        self.redirect = redirect
        self.authorization_server = authorization_server

    @doLog
    def __login__(self):
        """ Logs in as self.username with self.password and holds cookie in
        session"""
        p = {'j_username': self.username, 'j_password': self.password}
        s = requests.session()
        s.post("{}/login.do".format(self.authorization_server), params=p)
        return s

    @doLog
    def __grant_access__(self):
        """ The user from self.__login__ grants access for self.client with
        self.redirect and holds the resulting cookie in the session """
        p = {
            'response_type': 'code',
            'scope': 'GET POST PUT PATCH DELETE',
            'state': 'haha',
            'client_id': self.client,
            'redirect_uri': self.redirect}
        s = self.__login__()
        s.post("{}/oauth/authorize".format(self.authorization_server),
               params=p)
        return s

    @doLog
    def __approve__(self):
        """ The granted access from self.__grant_access__ will get approved """
        p = {
            'user_oauth_approval': 'true',
            'response_type': 'code'}
        s = self.__grant_access__()
        #s.allows_redirect = False
        return s.post("{}/oauth/authorize".format(self.authorization_server),
                      params=p, cookies=s.cookies)

    def get_access_token(self):
        """ returns the access_token or will crash trying """
        b = self.__approve__()
        m = re.search('.*access_token&#34;:&#34;([\w-]+)&#34;.*', b.text)
        return m.group(1)

if __name__ == '__main__':
    a = FakeUser('marissa', 'koala', 'testClient',
                 'http://localhost:5000/oauth2',
                 'http://localhost:8080/authorization-server')
    print 'Access Token: {}'.format(a.get_access_token())
