""" This module wraps scim functionality for measurements."""
from osiam import connector
import measuring
import uuid
import utils

scim = None
# Contains user found by User.get_all_user_ids
user_ids = []


def create_dynamic_user(data=None):
    """
    Creates a new user with an unique userName.

    This method is used as generate_data in measuring.exec_function.

    The measuring.exec_function will send data therefor the data parameter is
    mandatory but unused"""
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


def get_all_user_ids(amount):
    """
    Is used to get an amount of User and store into the user_ids list.
    """
    global user_ids
    user_ids = utils.get_ids(scim.search_with_get_on_users, amount)


def create(s, p):
    """ Creating user n times parallel and serial"""
    f = __create_user__
    return measuring.exec_function(f, s, p, create_dynamic_user, None)


def read(s, p):
    """ Reading user n times parallel and serial"""
    f = __get_user__
    return measuring.exec_function(
        f, s, p, measuring.default_generate_data,
        create_dynamic_user())


def replace(s, p):
    """ Replacing user n times parallel and serial"""
    f = __replace_user__
    return measuring.exec_function(
        f, s, p, measuring.default_generate_data,
        create_dynamic_user())


def update(s, p):
    """ Updating user n times parallel and serial"""
    f = __update_user__
    return measuring.exec_function(
        f, s, p, measuring.default_generate_data,
        create_dynamic_user())


def delete(s, p):
    """ Deleting user n times parallel and serial"""
    f = __delete_user__
    return measuring.exec_function(
        f, s, p, measuring.default_generate_data,
        create_dynamic_user())


def search(s, p):
    """ Searching on user n times parallel and serial"""
    f = __search_with_get_on_user__
    return measuring.exec_function(
        f, s, p, measuring.default_generate_data,
        utils.get_filter('get'))


def search_post(s, p):
    """ Searching on user n times parallel and serial"""
    f = __search_with_post_on_user__
    return measuring.exec_function(
        f, s, p, measuring.default_generate_data,
        utils.get_filter('post'))


@measuring.measure
def __create_user__(runs_for_profiling, user):
    """ runs_for_profiling is used to determine the amount of
        parallel calls to generate useful logs and it must be the first
    parameter.
        user must be the second parameter due to the log functionality,
    it will also be used to determine the send data """
    return scim.create_user(user)


@measuring.measure
def __replace_user__(runs_for_profiling, user):
    """ runs_for_profiling is used to determine the amount of
        parallel calls to generate useful logs and it must be the first
    parameter.
        user must be the second parameter due to the log functionality,
    it will also be used to determine the send data """
    return scim.replace_user(user_ids.pop(), create_dynamic_user())


@measuring.measure
def __update_user__(runs_for_profiling, user):
    """ runs_for_profiling is used to determine the amount of
        parallel calls to generate useful logs and it must be the first
    parameter.
        user must be the second parameter due to the log functionality,
    it will also be used to determine the send data """
    return scim.update_user(user_ids.pop(), create_dynamic_user())


@measuring.measure
def __delete_user__(runs_for_profiling, user):
    """ runs_for_profiling is used to determine the amount of
        parallel calls to generate useful logs and it must be the first
    parameter.
        user must be the second parameter due to the log functionality,
    it will also be used to determine the send data """
    return scim.delete_user(user_ids.pop())


@measuring.measure
def __get_user__(runs_for_profiling, user):
    """ runs_for_profiling is used to determine the amount of
        parallel calls to generate useful logs and it must be the first
    parameter.
        user must be the second parameter due to the log functionality,
    it will also be used to determine the send data"""
    return scim.get_user(user_ids.pop())


@measuring.measure
def __search_with_get_on_user__(runs_for_profiling, filter):
    """ runs_for_profiling is used to determine the amount of
        parallel calls to generate useful logs and it must be the first
    parameter.
        filter must be the second parameter due to the log functionality,
    it will also be used to determine the send data"""
    return scim.search_with_get_on_users(filter)


@measuring.measure
def __search_with_post_on_user__(runs_for_profiling, filter):
    """ runs_for_profiling is used to determine the amount of
        parallel calls to generate useful logs and it must be the first
    parameter.
        filter must be the second parameter due to the log functionality,
    it will also be used to determine the send data"""
    print "*** Filter: {} ****".format(filter)
    return scim.search_with_post_on_users(filter)
