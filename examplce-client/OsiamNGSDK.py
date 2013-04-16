"""This file is "SDK" for OSIAM NG it should enable you to use OSIAM NG easily"""
import requests
import sys
__author__ = 'phil'

def debug(func):
    def wrapped(*args, **kwargs):
        result = func(*args, **kwargs)
        if __debug__:
            print 'called {} result:\n{}'.format(func.__name__, result)
        return result
    return wrapped



authorization_server = 'http://localhost:8080/authorization-server'
access_token = ''

@debug
def get_user(uuid):
    headers = {'Authorization': "Bearer {}".format(access_token)}
    r = requests.get(authorization_server + '/User/{}'.format(uuid), headers = headers)
    return r.text

if __name__ == '__main__':
    print 'Gimme access_token:'
    access_token = sys.stdin.readline()
    print 'access_token is {}'.format(access_token)
    print 'get user:'
    print get_user(sys.stdin.readline())




