""" This module is responsible for all group test cases """
from osiam import connector
import measuring
import utils
import uuid


scim = None
group_ids = []


def create_dynamic_group(data=None):
    """
    This method creates a new group with an unique displayName.

    This method is used as generate_data in measuring.exec_function.

    The measuring.exec_function will send data therefor the data parameter is
    mandatory but unused"""
    return connector.SCIMGroup(
        displayName='display_name{0}'.format(uuid.uuid4()))


def get_all_group_ids(amount):
    global group_ids
    group_ids = utils.get_ids(scim.search_with_get_on_groups, amount)


def create(s, p):
    """ Creating group n times parallel and serial"""
    f = __create_group__
    return measuring.exec_function(f, s, p, create_dynamic_group,
                                   create_dynamic_group())


def read(s, p):
    """ Reading group n times parallel and serial"""
    f = __get_group__
    return measuring.exec_function(
        f, s, p, measuring.default_generate_data,
        create_dynamic_group())


def replace(s, p):
    """ Replacing group n times parallel and serial"""
    f = __replace_group__
    return measuring.exec_function(f, s, p, create_dynamic_group,
                                   create_dynamic_group())


def update(s, p):
    """ Updating group n times parallel and serial"""
    f = __update_group__
    return measuring.exec_function(f, s, p, create_dynamic_group,
                                   create_dynamic_group())


def delete(s, p):
    """ Deleting group n times parallel and serial"""
    f = __delete_group__
    return measuring.exec_function(
        f, s, p, measuring.default_generate_data,
        create_dynamic_group())


def search(s, p):
    """ Searching on group n times parallel and serial"""
    f = __search_with_get_on_group__
    return measuring.exec_function(
        f, s, p, measuring.default_generate_data,
        utils.get_filter('get'))


def search_post(s, p):
    """ Searching on group n times parallel and serial"""
    f = __search_with_post_on_group__
    return measuring.exec_function(
        f, s, p, measuring.default_generate_data,
        utils.get_filter('post'))


@measuring.measure
def __create_group__(group):
    """ runs_for_profiling is used to determine the amount of
        parallel calls to generate useful logs and it must be the first
    parameter.
        group must be the second parameter due to the log functionality,
    it will also be used to determine the send data """
    return scim.create_group(group)


@measuring.measure
def __replace_group__(group):
    """ runs_for_profiling is used to determine the amount of
        parallel calls to generate useful logs and it must be the first
    parameter.
        group must be the second parameter due to the log functionality,
    it will also be used to determine the send data """
    return scim.replace_group(group_ids.pop(), create_dynamic_group())


@measuring.measure
def __update_group__(group):
    """ runs_for_profiling is used to determine the amount of
        parallel calls to generate useful logs and it must be the first
    parameter.
        group must be the second parameter due to the log functionality,
    it will also be used to determine the send data """
    return scim.update_group(group_ids.pop(), create_dynamic_group())


@measuring.measure
def __delete_group__(group):
    """ runs_for_profiling is used to determine the amount of
        parallel calls to generate useful logs and it must be the first
    parameter.
        group must be the second parameter due to the log functionality,
    it will also be used to determine the send data """
    return scim.delete_group(group_ids.pop())


@measuring.measure
def __get_group__(group):
    """ runs_for_profiling is used to determine the amount of
        parallel calls to generate useful logs and it must be the first
    parameter.
        group must be the second parameter due to the log functionality,
    it will also be used to determine the send data """
    return scim.get_group(group_ids.pop())


@measuring.measure
def __search_with_get_on_group__(filter):
    """ runs_for_profiling is used to determine the amount of
        parallel calls to generate useful logs and it must be the first
    parameter.
        filter must be the second parameter due to the log functionality,
    it will also be used to determine the send data """
    return scim.search_with_get_on_groups(filter)


@measuring.measure
def __search_with_post_on_group__(filter):
    """ runs_for_profiling is used to determine the amount of
        parallel calls to generate useful logs and it must be the first
    parameter.
        filter must be the second parameter due to the log functionality,
    it will also be used to determine the send data """
    return scim.search_with_post_on_groups(filter)
