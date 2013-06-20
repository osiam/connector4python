__author__ = 'jtodea'

from obtain_access_token import FakeUser
from osiam import connector
import measuring
import uuid

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


def get_filter(id):
    """ Defining the filter for search accordingly the id"""
    if id == 'get':
        return 'filter=displayName%20pr&count=100'
    if id == 'post':
        return '{\'filter\':\'displayName%20pr\', \'count\':\'100\'}'


def create_dynamic_user(data=None):
    return connector.SCIMUser(
        userName='user_name{0}'.format(uuid.uuid4()),
        displayName='displayName',
        nickName='nickname',
        profileUrl='ProfileUrl',
        title='title',
        userType='userType',
        preferredLanguage='preferredLanguage',
        locale='locale',
        timezone='timezone',
        active=True,
        password='password')


def create_dynamic_group(data=None):
        return connector.SCIMGroup(
            displayName='display_name{0}'.format(uuid.uuid4()))


class User():

    """ This class is responsible for all user test cases """
    user_ids = []

    def get_all_user_ids(self, amount):
        print "trying to get all user id."
        userResult = scim.search_with_get_on_users('count={}'.format(amount))
        len = userResult['totalResults']
        itemsPerPage = userResult['itemsPerPage']
        if len > itemsPerPage:
            len = itemsPerPage
        for count in range(len):
            self.user_ids.append(userResult['Resources'][count]['id'])
        print "got user ids:\n{}".format(self.user_ids)

    def create(self, s, p):
        """ Creating user n times parallel and serial"""
        f = self.__create_user__
        return measuring.exec_function(f, s, p, create_dynamic_user, None)

    def read(self, s, p):
        """ Reading user n times parallel and serial"""
        f = self.__get_user__
        return measuring.exec_function(
            f, s, p, measuring.default_generate_data,
            create_dynamic_user())

    def replace(self, s, p):
        """ Replacing user n times parallel and serial"""
        f = self.__replace_user__
        return measuring.exec_function(
            f, s, p, measuring.default_generate_data,
            create_dynamic_user())

    def update(self, s, p):
        """ Updating user n times parallel and serial"""
        f = self.__update_user__
        return measuring.exec_function(
            f, s, p, measuring.default_generate_data,
            create_dynamic_user())

    def delete(self, s, p):
        """ Deleting user n times parallel and serial"""
        f = self.__delete_user__
        return measuring.exec_function(
            f, s, p, measuring.default_generate_data,
            create_dynamic_user())

    def search(self, s, p):
        """ Searching on user n times parallel and serial"""
        f = self.__search_with_get_on_user__
        return measuring.exec_function(
            f, s, p, measuring.default_generate_data,
            get_filter('get'))

    def search_post(self, s, p):
        """ Searching on user n times parallel and serial"""
        f = self.__search_with_post_on_user__
        return measuring.exec_function(
            f, s, p, measuring.default_generate_data,
            get_filter('post'))

    @measuring.measure
    def __create_user__(self, runs_for_profiling, user):
        """ runs_for_profiling always second parameter
            user always third parameter """
        return scim.create_user(user)

    @measuring.measure
    def __replace_user__(self, runs_for_profiling, user):
        """ runs_for_profiling always second parameter
            user always third parameter """
        return scim.replace_user(self.user_ids.pop(), create_dynamic_user())

    @measuring.measure
    def __update_user__(self, runs_for_profiling, user):
        """ runs_for_profiling always second parameter
            user always third parameter """
        return scim.update_user(self.user_ids.pop(), create_dynamic_user())

    @measuring.measure
    def __delete_user__(self, runs_for_profiling, user):
        """ runs_for_profiling always second parameter
            user always third parameter """
        return scim.delete_user(self.user_ids.pop())

    @measuring.measure
    def __get_user__(self, runs_for_profiling, user):
        """ runs_for_profiling always second parameter
            user always third parameter"""
        return scim.get_user(self.user_ids.pop())

    @measuring.measure
    def __search_with_get_on_user__(self, runs_for_profiling, filter):
        """ runs_for_profiling always second parameter
            filter always third parameter"""
        return scim.search_with_get_on_users(filter)

    @measuring.measure
    def __search_with_post_on_user__(self, runs_for_profiling, filter):
        """ runs_for_profiling always second parameter
            filter always third parameter"""
        print "*** Filter: {} ****".format(filter)
        return scim.search_with_post_on_users(filter)


class Group():

    """ This class is responsible for all group test cases """
    group_ids = []

    def get_all_group_ids(self, amount):
        groupResult = scim.search_with_get_on_groups('count={}'.format(amount))
        len = groupResult['totalResults']
        itemsPerPage = groupResult['itemsPerPage']
        if len > itemsPerPage:
            len = itemsPerPage
        for count in range(len):
            self.group_ids.append(groupResult['Resources'][count]['id'])

    def create(self, s, p):
        """ Creating group n times parallel and serial"""
        f = self.__create_group__
        return measuring.exec_function(f, s, p, create_dynamic_group,
                                       create_dynamic_group())

    def read(self, s, p):
        """ Reading group n times parallel and serial"""
        f = self.__get_group__
        return measuring.exec_function(
            f, s, p, measuring.default_generate_data,
            create_dynamic_group())

    def replace(self, s, p):
        """ Replacing group n times parallel and serial"""
        f = self.__replace_group__
        return measuring.exec_function(f, s, p, create_dynamic_group,
                                       create_dynamic_group())

    def update(self, s, p):
        """ Updating group n times parallel and serial"""
        f = self.__update_group__
        return measuring.exec_function(f, s, p, create_dynamic_group,
                                       create_dynamic_group())

    def delete(self, s, p):
        """ Deleting group n times parallel and serial"""
        f = self.__delete_group__
        return measuring.exec_function(
            f, s, p, measuring.default_generate_data,
            create_dynamic_group())

    def search(self, s, p):
        """ Searching on group n times parallel and serial"""
        f = self.__search_with_get_on_group__
        return measuring.exec_function(
            f, s, p, measuring.default_generate_data,
            get_filter('get'))

    def search_post(self, s, p):
        """ Searching on group n times parallel and serial"""
        f = self.__search_with_post_on_group__
        return measuring.exec_function(
            f, s, p, measuring.default_generate_data,
            get_filter('post'))

    @measuring.measure
    def __create_group__(self, runs_for_profiling, group):
        """ runs_for_profiling always second parameter
            group always third parameter """
        return scim.create_group(group)

    @measuring.measure
    def __replace_group__(self, runs_for_profiling, group):
        """ runs_for_profiling always second parameter
            group always third parameter """
        return scim.replace_group(self.group_ids.pop(), create_dynamic_group())

    @measuring.measure
    def __update_group__(self, runs_for_profiling, group):
        """ runs_for_profiling always second parameter
            group always third parameter """
        return scim.update_group(self.group_ids.pop(), create_dynamic_group())

    @measuring.measure
    def __delete_group__(self, runs_for_profiling, group):
        """ runs_for_profiling always second parameter
            group always third parameter """
        return scim.delete_group(self.group_ids.pop())

    @measuring.measure
    def __get_group__(self, runs_for_profiling, group):
        """ runs_for_profiling always second parameter
            group always third parameter """
        return scim.get_group(self.group_ids.pop())

    @measuring.measure
    def __search_with_get_on_group__(self, runs_for_profiling, filter):
        """ runs_for_profiling always second parameter
            filter always third parameter """
        return scim.search_with_get_on_groups(filter)

    @measuring.measure
    def __search_with_post_on_group__(self, runs_for_profiling, filter):
        """ runs_for_profiling always second parameter
            filter always third parameter """
        return scim.search_with_post_on_groups(filter)
