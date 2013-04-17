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
    schemas = ['urn:scim:schemas:core:1.0']
    userName = None
    name = None #type
    displayName = None
    nickName = None
    profileUrl = None
    title = None
    userType = None
    preferredLanguage = None
    locale = None
    timezone = None
    active = None
    password = None
    emails = None #dirty list
    phoneNumbers = None #dirty list
    ims = None #dirty list
    photos = None #dirty list
    addresses = None #list of type
    groups = None #dirty list
    entitlements = None #dirty list
    roles = None #dirty list
    x509Certificates = None #dirty list
    any = None#simple list


#    def __init__(self,
#                     userName,
#                     name, #type
#                     displayName,
#                     nickName,
#                     profileUrl,
#                     title,
#                     userType,
#                     preferredLanguage,
#                     locale,
#                     timezone,
#                     active,
#                     password,
#                     emails, #dirty list
#                     phoneNumbers, #dirty list
#                     ims, #dirty list
#                     photos, #dirty list
#                     addresses, #list of type
#                     groups, #dirty list
#                     entitlements, #dirty list
#                     roles, #dirty list
#                     x509Certificates, #dirty list
#                     any #simple list
#        ):
#            self.userName = userName
#            self.name = name
#            self.displayName = displayName
#            self.nickName = nickName
#            self.profileUrl = profileUrl
#            self.title = title
#            self.userType = userType
#            self.preferredLanguage = preferredLanguage
#            self.locale = locale
#            self.timezone = timezone
#            self.active = active
#            self.password = password
#            self.emails = emails
#            self.phoneNumbers = phoneNumbers
#            self.ims = ims
#            self.photos = photos
#            self.addresses = addresses
#            self.groups = groups
#            self.entitlements = entitlements
#            self.roles = roles
#            self.x509Certificates = x509Certificates
#            self.any = any


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
    u = SCIMUser()
    u.userName = 'test'
    print json.dumps(u)
    try:
        print json.loads(user)['bla']
    except sys.Exception as inst:
        print sys.type(inst)
