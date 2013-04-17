"""This file is "SDK" for OSIAM NG it should enable you to use OSIAM NG easily"""
import requests
import sys



__author__ = 'phil'

log = True

def doLog(func):
    def wrapped(*args, **kwargs):
        result = func(*args, **kwargs)
        if log:
            print 'called {} result:\n{}'.format(func.__name__, result)
        return result
    return wrapped

class SCIMUser:
    def __init__(self):
        return ''


class SCIM:
    def __init__(self, authorization_server, access_token):
        self.authorization_server = authorization_server
        self.access_token = access_token

    @doLog
    def get_user(self, uuid):
        headers = {'Authorization': "Bearer {}".format(self.access_token)}
        r = requests.get('{}/User/{}'.format(self.authorization_server, uuid), headers=headers)
        return r.text

    @doLog
    def create_user(self, user):
        headers = {'Authorization': "Bearer {}".format(self.access_token)}
        r = requests.post('{}/'.format(self.authorization_server), headers=headers)
        return r.text



if __name__ == '__main__':
    from obtain_access_token import FakeUser
    import json
    a = FakeUser('marissa', 'koala', 'testClient', 'http://localhost:5000/oauth2')
    token = a.get_access_token()
    uuid = 'cef9452e-00a9-4cec-a086-d171374ffbef'
    user = SCIM('http://localhost:8080/authorization-server', token).get_user(uuid)
    try:
        print json.loads(user)['bla']
    except sys.Exception as inst:
        print sys.type(inst)
