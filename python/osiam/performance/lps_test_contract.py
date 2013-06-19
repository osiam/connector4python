__author__ = 'jtodea'

from osiam import connector
from obtain_access_token import FakeUser
from multiprocessing import Process
import logging
from datetime import datetime
import sys
import uuid

logger = logging.getLogger(__name__)

scim = None
max_response_time = 0
procs = []
complete_duration = []
error_amount = 0
timeout_amount = 0


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
    logger.addHandler(create_filehandler("/tmp",
                                         "osiam_performance_test_debug.log"))
    logger.setLevel(logging.INFO)
    logger.info('name;iterations;running_time;data_volume_in_bytes;response')
    max_response_time = timeout


def do_log(func):
    def wrapped(*args, **kwargs):
        tstart = datetime.now()
        result = func(*args, **kwargs)
        tstop = datetime.now()
        duration = tstop - tstart
        global error_amount, timeout_amount
        ms = duration.microseconds / 1000
        ## delete does not return json, so it is a response directly
        if result.__class__.__name__ is 'Response':
            if result.status_code is not 200:
                error_amount = error_amount + 1
        # when it is not a response, check if there is error keyword
        elif result.get('error') is not None:
            error_amount = error_amount + 1
        if ms >= max_response_time:
            timeout_amount = timeout_amount + 1
        logger.info('{0};{1};{2};{3};{4}'.format(func.__name__, args[1],
                                                 duration,
                                                 sys.getsizeof(args[2]),
                                                 result))
        complete_duration.append(ms)

        return {'duration': duration, 'result': result,
                'size_of_input': sys.getsizeof(args[2])}
    return wrapped


def create_filehandler(log_file_path, script_name):
    file_handler = logging.FileHandler('{0}/{1}_{2}.log'.format(
        log_file_path, datetime.now().isoformat(), script_name))
    formatter = logging.Formatter('%(message)s')
    file_handler.setFormatter(formatter)
    return file_handler


def measure_function(f, s, p, generate_data, data):
    """ executes a given function s times serial, p times parallel and
    summarises the duration of all calls """

    def calculate_percent(e):
        return int(float(sum(e)) / float(len(complete_duration)) * 100)

    global complete_duration, error_amount, procs, timeout_amount
    complete_duration = []
    error_amounts = []
    timeout_amounts = []
    for serial in range(s):
        error_amount = 0
        timeout_amount = 0
        for parallel in range(p):
            start_parallel_process(f(p, generate_data(data)))
        for process in procs:
            process.join()
        error_amounts.append(error_amount)
        timeout_amounts.append(timeout_amount)
        procs = []
    return {'min': min(complete_duration),
            'max': max(complete_duration),
            'avg': sum(complete_duration) / len(complete_duration),
            'timeout': "{}%".format(calculate_percent(timeout_amounts)),
            'error': "{}%".format(calculate_percent(error_amounts))}


def default_generate_data(data):
    return data


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


def __get_filter__(id):
    """ Defining the filter for search accordingly the id"""
    if id == 'get':
        return 'filter=displayName%20pr&count=100'
    if id == 'post':
        return '{\'filter\':\'displayName%20pr\', \'count\':\'100\'}'


def start_parallel_process(target):
    p = Process(target=target)
    p.start()
    procs.append(p)


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
        return measure_function(f, s, p, create_dynamic_user, None)

    def read(self, s, p):
        """ Reading user n times parallel and serial"""
        f = self.__get_user__
        return measure_function(f, s, p, default_generate_data,
                                create_dynamic_user())

    def replace(self, s, p):
        """ Replacing user n times parallel and serial"""
        f = self.__replace_user__
        return measure_function(f, s, p, default_generate_data,
                                create_dynamic_user())

    def update(self, s, p):
        """ Updating user n times parallel and serial"""
        f = self.__update_user__
        return measure_function(f, s, p, default_generate_data,
                                create_dynamic_user())

    def delete(self, s, p):
        """ Deleting user n times parallel and serial"""
        f = self.__delete_user__
        return measure_function(f, s, p, default_generate_data,
                                create_dynamic_user())

    def search(self, s, p):
        """ Searching on user n times parallel and serial"""
        f = self.__search_with_get_on_user__
        return measure_function(f, s, p, default_generate_data,
                                __get_filter__('get'))

    def search_post(self, s, p):
        """ Searching on user n times parallel and serial"""
        f = self.__search_with_post_on_user__
        return measure_function(f, s, p, default_generate_data,
                                __get_filter__('post'))

    @do_log
    def __create_user__(self, runs_for_profiling, user):
        """ runs_for_profiling always second parameter
            user always third parameter """
        return scim.create_user(user)

    @do_log
    def __replace_user__(self, runs_for_profiling, user):
        """ runs_for_profiling always second parameter
            user always third parameter """
        return scim.replace_user(self.user_ids.pop(), create_dynamic_user())

    @do_log
    def __update_user__(self, runs_for_profiling, user):
        """ runs_for_profiling always second parameter
            user always third parameter """
        return scim.update_user(self.user_ids.pop(), create_dynamic_user())

    @do_log
    def __delete_user__(self, runs_for_profiling, user):
        """ runs_for_profiling always second parameter
            user always third parameter """
        return scim.delete_user(self.user_ids.pop())

    @do_log
    def __get_user__(self, runs_for_profiling, user):
        """ runs_for_profiling always second parameter
            user always third parameter"""
        return scim.get_user(self.user_ids.pop())

    @do_log
    def __search_with_get_on_user__(self, runs_for_profiling, filter):
        """ runs_for_profiling always second parameter
            filter always third parameter"""
        return scim.search_with_get_on_users(filter)

    @do_log
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
        return measure_function(f, s, p, create_dynamic_group,
                                create_dynamic_group())

    def read(self, s, p):
        """ Reading group n times parallel and serial"""
        f = self.__get_group__
        return measure_function(f, s, p, default_generate_data,
                                create_dynamic_group())

    def replace(self, s, p):
        """ Replacing group n times parallel and serial"""
        f = self.__replace_group__
        return measure_function(f, s, p, create_dynamic_group,
                                create_dynamic_group())

    def update(self, s, p):
        """ Updating group n times parallel and serial"""
        f = self.__update_group__
        return measure_function(f, s, p, create_dynamic_group,
                                create_dynamic_group())

    def delete(self, s, p):
        """ Deleting group n times parallel and serial"""
        f = self.__delete_group__
        return measure_function(f, s, p, default_generate_data,
                                create_dynamic_group())

    def search(self, s, p):
        """ Searching on group n times parallel and serial"""
        f = self.__search_with_get_on_group__
        return measure_function(f, s, p, default_generate_data,
                                __get_filter__('get'))

    def search_post(self, s, p):
        """ Searching on group n times parallel and serial"""
        f = self.__search_with_post_on_group__
        return measure_function(f, s, p, default_generate_data,
                                __get_filter__('post'))

    @do_log
    def __create_group__(self, runs_for_profiling, group):
        """ runs_for_profiling always second parameter
            group always third parameter """
        return scim.create_group(group)

    @do_log
    def __replace_group__(self, runs_for_profiling, group):
        """ runs_for_profiling always second parameter
            group always third parameter """
        return scim.replace_group(self.group_ids.pop(group))

    @do_log
    def __update_group__(self, runs_for_profiling, group):
        """ runs_for_profiling always second parameter
            group always third parameter """
        return scim.update_group(self.group_ids.pop(group))

    @do_log
    def __delete_group__(self, runs_for_profiling, group):
        """ runs_for_profiling always second parameter
            group always third parameter """
        return scim.delete_group(self.group_ids.pop())

    @do_log
    def __get_group__(self, runs_for_profiling, group):
        """ runs_for_profiling always second parameter
            group always third parameter """
        return scim.get_group(self.group_ids.pop())

    @do_log
    def __search_with_get_on_group__(self, runs_for_profiling, filter):
        """ runs_for_profiling always second parameter
            filter always third parameter """
        return scim.search_with_get_on_groups(filter)

    @do_log
    def __search_with_post_on_group__(self, runs_for_profiling, filter):
        """ runs_for_profiling always second parameter
            filter always third parameter """
        return scim.search_with_post_on_groups(filter)
