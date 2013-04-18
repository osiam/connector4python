# coding=utf-8
"""This file is "SDK" for OSIAM NG it should enable you to use OSIAM NG easily"""

import json

import requests


__author__ = 'phil'

log = True


class SCIMMultiValuedAttribute:
    def __init__(self,
                 value=None,
                 display=None,
                 primary=False,
                 type=None,
                 operation=None):
        self.value = value
        self.display = display
        self.primary = primary
        self.type = type
        self.operation = operation


class SCIMAddress(SCIMMultiValuedAttribute):
    def __init__(self, display=None, primary=False, type=None, operation=None, formatted=None, streetAddress=None,
                 locality=None, region=None, postalCode=None, country=None):
        SCIMMultiValuedAttribute.__init__(self, display=display, primary=primary, type=type, operation=operation)
        self.formatted = formatted
        self.streetAddress = streetAddress
        self.locality = locality
        self.region = region
        self.postalCode = postalCode
        self.country = country


class SCIMName:
    def __init__(self,
                 formatted=None,
                 familyName=None,
                 givenName=None,
                 middleName=None,
                 honorificPrefix=None,
                 honorificSuffix=None
    ):
        self.formatted = formatted
        self.familyName = familyName
        self.givenName = givenName
        self.middleName = middleName
        self.honorificPrefix = honorificPrefix
        self.honorificSuffix = honorificSuffix


class SCIMUser:
    def __init__(self, id=None, schemas=None, userName=None, name=None, displayName=None, nickName=None,
                 profileUrl=None, title=None, userType=None, preferredLanguage=None, locale=None, timezone=None,
                 active=None, password=None, emails=None, phoneNumbers=None, ims=None, photos=None, addresses=None,
                 groups=None, entitlements=None, roles=None, x509Certificates=None, any=None, meta=None,
                 externalId=None):
        if not schemas: schemas = ['urn:scim:schemas:core:1.0']
        self.userName = userName
        self.name = name
        self.displayName = displayName
        self.nickName = nickName
        self.profileUrl = profileUrl
        self.title = title
        self.userType = userType
        self.preferredLanguage = preferredLanguage
        self.locale = locale
        self.timezone = timezone
        self.active = active
        self.password = password
        self.emails = emails
        self.phoneNumbers = phoneNumbers
        self.ims = ims
        self.photos = photos
        self.addresses = addresses
        self.groups = groups
        self.entitlements = entitlements
        self.roles = roles
        self.x509Certificates = x509Certificates
        self.any = any
        self.schemas = schemas
        self.id = id
        self.meta = meta
        self.externalId = externalId


def doLog(func):
    def wrapped(*args, **kwargs):
        result = func(*args, **kwargs)
        if log:
            print 'called {0} result:\n{1}'.format(func.__name__, result)
        return result

    return wrapped


def convert_to_builtin_type(obj):
    return obj.__dict__


class SCIMError(object):
    def __init__(self, error_code, description=None):
        self.error_code = error_code
        self.description = description


class SCIMGroup(object):
    def __init__(self, displayName=None, members=None, externalId=None, id=None, meta=None, schemas=None):
        if not schemas: schemas = ['urn:scim:schemas:core:1.0']
        self.displayName = displayName
        self.members = members
        self.externalId = externalId
        self.id = id
        self.meta = meta
        self.schemas = schemas

class SCIM:
    def __init__(self, authorization_server, access_token):
        self.authorization_server = authorization_server
        self.headers = {'Authorization': "Bearer {0}".format(access_token),
                        'content-type': 'application/json'}

    def __json_dict_to_object__(self, user):
        args = dict((key.encode('ascii'), value) for key, value in user.items())
        if user.get('userName') is not None:
            return SCIMUser(**args)
        elif user.get('error_code') is not None:
            return SCIMError(**args)
        else:
            return SCIMGroup(**args)

    def __single_data_operation__(self, func, id, data, type):
        data = json.dumps(data, default=convert_to_builtin_type)
        return func('{}/{}/{}'.format(self.authorization_server, type, id),
                    headers=self.headers, data=data)


    @doLog
    def get_user(self, uuid):
        r = requests.get('{0}/User/{1}'.format(self.authorization_server, uuid), headers=self.headers)
        r_text = r.text
        o = json.loads(r_text)
        return self.__json_dict_to_object__(o)

    @doLog
    def create_user(self, user):
        r = requests.post('{0}/User'.format(self.authorization_server),
                          headers=self.headers,
                          data=json.dumps(user, default=convert_to_builtin_type))
        return self.__json_dict_to_object__(json.loads(r.text))

    def __single_user_data_operation__(self, func, id, user):
        return self.__single_data_operation__(func, id, user, "User")

    @doLog
    def replace_user(self, id, user):
        operation = self.__single_user_data_operation__(requests.put, id, user)
        return self.__json_dict_to_object__(json.loads(operation.content))

    @doLog
    def update_user(self, id, user):
        operation = self.__single_user_data_operation__(requests.patch, id, user)
        return self.__json_dict_to_object__(json.loads(operation.content))

    @doLog
    def delete_user(self, id):
        return requests.delete('{0}/User/{1}'.format(self.authorization_server, id), headers=self.headers)

    @doLog
    def get_group(self, uuid):
        r = requests.get('{0}/Group/{1}'.format(self.authorization_server, uuid), headers=self.headers)
        return self.__json_dict_to_object__(json.loads(r.text))

    @doLog
    def create_group(self, user):
        r = requests.post('{0}/Group'.format(self.authorization_server),
                          headers=self.headers,
                          data=json.dumps(user, default=convert_to_builtin_type))
        return self.__json_dict_to_object__(json.loads(r.text))

    def __single_group_data_operation__(self, func, id, user):
        return self.__single_data_operation__(func, id, user, "Group")

    @doLog
    def replace_group(self, id, user):
        operation = self.__single_group_data_operation__(requests.put, id, user)
        return self.__json_dict_to_object__(json.loads(operation.content))

    @doLog
    def update_group(self, id, user):
        operation = self.__single_group_data_operation__(requests.patch, id, user)
        return self.__json_dict_to_object__(json.loads(operation.content))

    @doLog
    def delete_group(self, id):
        return requests.delete('{0}/Group/{1}'.format(self.authorization_server, id), headers=self.headers)


if __name__ == '__main__':
    from obtain_access_token import FakeUser
    import json

    a = FakeUser('marissa', 'koala', 'testClient', 'http://localhost:5000/oauth2')
    token = a.get_access_token()
    uuid = 'cef9452e-00a9-4cec-a086-d171374ffbef'
    scim = SCIM('http://localhost:8080/authorization-server', token)
    user = scim.get_user(uuid)
    emails = [SCIMMultiValuedAttribute(value='xxx@xxx.de', primary=False)]
    u = SCIMUser(userName='hui', password='wtf', name=SCIMName(formatted=' W T F'), emails=emails)
    u = scim.create_user(u)
    SCIMAddress(primary=True, streetAddress="Elmstreet 123")
    addresses = [SCIMAddress(primary=True, streetAddress="Elmstreet 123"),
                 SCIMAddress(primary=False, streetAddress="Natnat 23")]

    emails = [SCIMMultiValuedAttribute(value='xxx@xxx.de', operation="delete")]
    aha = SCIMUser(id=u.id, userName='achja, replace ..',
                   displayName='doch nicht wtf',
                   addresses=addresses,
                   emails=emails
    )
    r = scim.update_user(u.id, aha)
    group = SCIMGroup(displayName='Test', members=[SCIMMultiValuedAttribute(value='cef9452e-00a9-4cec-a086-d171374ffbef')])
    group = scim.create_group(group)
    print group
    group = scim.update_group(group.id, SCIMGroup(members=[SCIMMultiValuedAttribute(value='cef9452e-00a9-4cec-a086-d171374ffbef', operation='delete')]))

    scim.delete_group(group.id)



    print r.displayName
    scim.delete_user(u.id)



    # print json.dumps(u, default=convert_to_builtin_type)
    # try:
    #     print json.loads(user)['bla']
    # except Exception as inst:
    #     print type(inst)
