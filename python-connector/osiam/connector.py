# coding=utf-8

import json
import requests
import collections
import logging

__author__ = 'phil'


# logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)


def doLog(func):
    def wrapped(*args, **kwargs):
        result = func(*args, **kwargs)
        logger.debug('called {0} params: {1} result:\n{2}'.format(
            func.__name__, args, result))
        return result
    return wrapped


SCIMMultiValuedAttributeT = collections.namedtuple('SCIMMultiValuedAttribute',
                                                   ('value', 'display',
                                                    'primary', 'type',
                                                    'operation'))


def SCIMMultiValuedAttribute(value=None, display=None, primary=False,
                             type=None, operation=None):
        return SCIMMultiValuedAttributeT(value, display, primary, type,
                                         operation)


SCIMAddressT = collections.namedtuple('SCIMAddress',
                                      ('display', 'primary', 'type',
                                       'operation', 'formatted', 'locality',
                                       'region', 'postalCode', 'country',
                                       'streetAddress'))


def SCIMAddress(
    display=None, primary=None, type=None, operation=None, formatted=None,
        locality=None, region=None, postalCode=None, country=None,
        streetAddress=None):
    return SCIMAddressT(display, primary, type, operation, formatted,
                        locality, region, postalCode, country, streetAddress)


SCIMNameT = collections.namedtuple('SCIMName', (
    'formatted', 'familyName', 'givenName', 'middleName', 'honorificPrefix',
    'honorificSuffix'))


def SCIMName(formatted=None, familyName=None, givenName=None, middleName=None,
             honorificPrefix=None, honorificSuffix=None):
    return SCIMNameT(formatted, familyName, givenName, middleName,
                     honorificPrefix, honorificSuffix)


SCIMUserT = collections.namedtuple('SCIMUser', (
    'id', 'schemas', 'userName', 'name',
    'displayName', 'nickName',
    'profileUrl', 'title', 'userType',
    'preferredLanguage', 'locale', 'timezone',
    'active', 'password', 'emails', 'phoneNumbers',
    'ims', 'photos', 'addresses',
    'groups', 'entitlements', 'roles',
    'x509Certificates', 'any', 'meta', 'externalId'))


def SCIMUser(id=None, schemas=None, userName=None, name=None, displayName=None,
             nickName=None, profileUrl=None, title=None, userType=None,
             preferredLanguage=None, locale=None, timezone=None, active=None,
             password=None, emails=None, phoneNumbers=None, ims=None,
             photos=None, addresses=None, groups=None, entitlements=None,
             roles=None, x509Certificates=None, any=None, meta=None,
             externalId=None):
        if not schemas:
            schemas = ['urn:scim:schemas:core:1.0']
        return SCIMUserT(id, schemas, userName, name, displayName, nickName,
                         profileUrl, title, userType, preferredLanguage,
                         locale, timezone, active, password, emails,
                         phoneNumbers, ims, photos, addresses, groups,
                         entitlements, roles, x509Certificates, any, meta,
                         externalId)

SCIMErrorT = collections.namedtuple('SCIMError', ('error_code', 'description'))


def SCIMError(error_code, description=None):
    return SCIMErrorT(error_code, description)


SCIMGroupT = collections.namedtuple('SCIMGroup', ('displayName', 'members',
                                                  'externalId', 'id', 'meta',
                                                  'schemas'))


def SCIMGroup(displayName=None, members=None, externalId=None, id=None,
              meta=None, schemas=None):
    if not schemas:
        schemas = ['urn:scim:schemas:core:1.0']
    return SCIMGroupT(displayName, members, externalId, id, meta, schemas)


class SCIM:

    def __init__(self, authorization_server, access_token):
        self.authorization_server = authorization_server
        self.headers = {'Authorization': "Bearer {0}".format(access_token),
                        'content-type': 'application/json'}

    def __json_dict_to_object__(self, user):
        return user
#        if user.get('userName') is not None:
#            return SCIMUser(user)
#        elif user.get('error_code') is not None:
#            return SCIMError(user)
#        else:
#            return SCIMGroup(user)

    def __single_data_operation__(self, func, id, data, type):
        data = json.dumps(data.__dict__)
        return func('{0}/{1}/{2}'.format(self.authorization_server, type, id),
                    headers=self.headers, data=data)

    @doLog
    def get_user(self, uuid):
        r = requests.get('{0}/User/{1}'.format(
            self.authorization_server, uuid), headers=self.headers)
        r_text = r.text
        o = json.loads(r_text)
        return self.__json_dict_to_object__(o)

    @doLog
    def create_user(self, user):
        data = json.dumps(user.__dict__)
        r = requests.post('{0}/User'.format(self.authorization_server),
                          headers=self.headers,
                          data=data)
        return self.__json_dict_to_object__(json.loads(r.text))

    def __single_user_data_operation__(self, func, id, user):
        return self.__single_data_operation__(func, id, user, "User")

    @doLog
    def replace_user(self, id, user):
        operation = self.__single_user_data_operation__(requests.put, id, user)
        return self.__json_dict_to_object__(json.loads(operation.content))

    @doLog
    def update_user(self, id, user):
        operation = self.__single_user_data_operation__(
            requests.patch, id, user)
        return self.__json_dict_to_object__(json.loads(operation.content))

    @doLog
    def delete_user(self, id):
        return requests.delete('{0}/User/{1}'.
                               format(self.authorization_server, id),
                               headers=self.headers)

    @doLog
    def get_group(self, uuid):
        r = requests.get('{0}/Group/{1}'.format(
            self.authorization_server, uuid), headers=self.headers)
        return self.__json_dict_to_object__(json.loads(r.text))

    @doLog
    def create_group(self, user):
        r = requests.post('{0}/Group'.format(self.authorization_server),
                          headers=self.headers,
                          data=json.dumps(user.__dict__))
        return self.__json_dict_to_object__(json.loads(r.text))

    def __single_group_data_operation__(self, func, id, user):
        return self.__single_data_operation__(func, id, user, "Group")

    @doLog
    def replace_group(self, id, user):
        operation = self.__single_group_data_operation__(
            requests.put, id, user)
        return self.__json_dict_to_object__(json.loads(operation.content))

    @doLog
    def update_group(self, id, user):
        operation = self.__single_group_data_operation__(requests.patch, id,
                                                         user)
        return self.__json_dict_to_object__(json.loads(operation.content))

    @doLog
    def delete_group(self, id):
        return requests.delete('{0}/Group/{1}'.format(
            self.authorization_server, id), headers=self.headers)

    @doLog
    def search_with_get_on_users(self, params):
        r = requests.get('{0}/User/?{1}'.format(
            self.authorization_server, params), headers=self.headers)
        return json.loads(r.text)

    @doLog
    def search_with_post_on_users(self, data):
        r = requests.post('{0}/User/.search'.format(
            self.authorization_server), headers=self.headers, params=data)
        return json.loads(r.text)

    @doLog
    def search_with_get_on_groups(self, params):
        r = requests.get('{0}/Group/?{1}'.format(
            self.authorization_server, params), headers=self.headers)
        return json.loads(r.text)

    @doLog
    def search_with_post_on_groups(self, data):
        r = requests.post('{0}/Group/.search'.format(
            self.authorization_server), headers=self.headers, params=data)
        return json.loads(r.text)

    @doLog
    def search_with_get_on_root(self, params):
        r = requests.get('{0}/?{1}'.format(
            self.authorization_server, params), headers=self.headers)
        return json.loads(r.text)

    @doLog
    def search_with_post_on_root(self, data):
        r = requests.post('{0}/.search'.format(
            self.authorization_server), headers=self.headers, params=data)
        return json.loads(r.text)
