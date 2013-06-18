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

procs = []

def __init__(server, client, client_id):
    """ Getting access token and initializes profiling """
    fakeUser = FakeUser('marissa', 'koala', client_id,
                        'http://' + client + ':5000/oauth2',
                        'http://' + server + ':8080/osiam-server')

    access_token = fakeUser.get_access_token()
    global scim
    scim = connector.SCIM('http://localhost:8080/osiam-server', access_token)
    print 'init profiling'
    logger.addHandler(create_filehandler("/tmp", "method_calls_debug"))
    logger.setLevel(logging.INFO)
    logger.info('name;iterations;running_time;data_volume_in_bytes')


def do_log(func):
    def wrapped(*args, **kwargs):
        tstart = datetime.now()
        func(*args, **kwargs)
        tstop = datetime.now()
        duration = tstop - tstart
        logger.info('{0};{1};{2};{3}'.format(func.__name__, args[1], duration,
                                             sys.getsizeof(args[2])))
        return duration
    return wrapped


def create_filehandler(log_file_path, script_name):
    file_handler = logging.FileHandler('{0}/{1}_{2}.log'.format(
        log_file_path, datetime.now().isoformat(), script_name))
    formatter = logging.Formatter('%(message)s')
    file_handler.setFormatter(formatter)
    return file_handler


def all(s, p):
    """ This method runs all test cases from user and group class """
    User().all(s, p)
    Group().all(s, p)


def measure_function(f, s, p, *data):
    """ executes a given function s times serial, p times parallel and
    summarises the duration of all calls """
    complete_duration = []
    print "executing {} {} times serial and {} times parallel".format(
        f.__name__, s, p)
    for serial in range(s):
        for parallel in range(p):
            # convert to milliseconds
            complete_duration.append(f(p, *data).microseconds / 1000)
        for process in procs:
            process.join()
        global procs
        procs = []
    return {'min': min(complete_duration),
            'max': max(complete_duration),
            'avg': sum(complete_duration) / len(complete_duration)}


class User():
    """ This class is responsible for all user test cases """
    user_ids = []

    def __build_user__(self):
        """ Building user object for creation"""
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

    def __get_filter__(self, id):
        """ Defining the filter for search accordingly the id"""
        if id == 'get':
            return 'filter=userName%20pr&count=100'
        if id == 'post':
            return '{\'filter\':\'userName%20pr\', \'count\':\'100\'}'

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
        f = self.__create_user_parallel__
        return measure_function(f, s, p, self.__build_user__())

    def read(self, s, p):
        """ Reading user n times parallel and serial"""
        f = self.__get_user_parallel__
        return measure_function(f, s, p, self.__build_user__())

    def replace(self, s, p):
        """ Replacing user n times parallel and serial"""
        f = self.__replace_user_parallel__
        return measure_function(f, s, p, self.__build_user__())

    def update(self, s, p):
        """ Updating user n times parallel and serial"""
        f = self.__update_user_parallel__
        return measure_function(f, s, p, self.__build_user__())

    def delete(self, s, p):
        """ Deleting user n times parallel and serial"""
        f = self.__delete_user_parallel__
        return measure_function(f, s, p, self.__build_user__())

    def search(self, s, p):
        """ Searching on user n times parallel and serial"""
        f = self.__search_with_get_on_user_parallel__
        return measure_function(f, s, p, self.__build_user__())

    def search_post(self, s, p):
        """ Searching on user n times parallel and serial"""
        f = self.__search_with_post_on_user_parallel__
        return measure_function(f, s, p, self.__build_user__())

    def all(self, s, p):
        """ Running all user tests n times parallel and serial"""
        self.create(s, p)
        self.read(s, p)
        self.replace(s, p)
        self.update(s, p)
        self.search(s, p)
        self.delete(s, p)

    @do_log
    def __create_user_parallel__(self, runs_for_profiling, user):
        """ runs_for_profiling always second parameter
            user always third parameter """
        p = Process(target=scim.create_user(user))
        p.start()
        procs.append(p)

    @do_log
    def __replace_user_parallel__(self, runs_for_profiling, user):
        """ runs_for_profiling always second parameter
            user always third parameter """
        p = Process(target=scim.replace_user(self.user_ids.pop(),
                                             self.__build_user__()))
        p.start()
        procs.append(p)

    @do_log
    def __update_user_parallel__(self, runs_for_profiling, user):
        """ runs_for_profiling always second parameter
            user always third parameter """
        p = Process(target=scim.update_user(self.user_ids.pop(),
                                            self.__build_user__()))
        p.start()
        procs.append(p)

    @do_log
    def __delete_user_parallel__(self, runs_for_profiling, user):
        """ runs_for_profiling always second parameter
            user always third parameter """
        p = Process(target=scim.delete_user(self.user_ids.pop()))
        p.start()
        procs.append(p)

    @do_log
    def __get_user_parallel__(self, runs_for_profiling, user):
        """ runs_for_profiling always second parameter
            user always third parameter"""
        p = Process(target=scim.get_user(self.user_ids.pop()))
        p.start()
        procs.append(p)

    def __search_user_parallel__(self, runs_for_profiling):
        self.__search_with_get_on_user_parallel__(runs_for_profiling,
                                                  self.__get_filter__('get'))
        self.__search_with_post_on_user_parallel__(runs_for_profiling,
                                                   self.__get_filter__('post'))

    @do_log
    def __search_with_get_on_user_parallel__(self, runs_for_profiling, filter):
        """ runs_for_profiling always second parameter
            filter always third parameter"""
        p = Process(target=scim.search_with_get_on_users(filter))
        p.start()
        procs.append(p)

    @do_log
    def __search_with_post_on_user_parallel__(self, runs_for_profiling,
                                              filter):
        """ runs_for_profiling always second parameter
            filter always third parameter"""
        p = Process(target=scim.search_with_post_on_users(filter))
        p.start()
        procs.append(p)


class Group():
    """ This class is responsible for all group test cases """
    group_ids = []

    def __build_group__(self, member='{\'value\':\'UUID\'}'):
        """ Building group object for creation """
        return connector.SCIMGroup(
            displayName='display_name{0}'.format(uuid.uuid4()))

    def __get_filter__(self, id):
        """ Defining the filter for search accordingly the id"""
        if id == 'get':
            return 'filter=displayName%20pr&count=100'
        if id == 'post':
            return '{\'filter\':\'displayName%20pr\', \'count\':\'100\'}'

    def __get_all_group_ids__(self):
        groupResult = scim.search_with_get_on_groups('')
        len = groupResult['totalResults']
        itemsPerPage = groupResult['itemsPerPage']
        if len > itemsPerPage:
            len = itemsPerPage
        for count in range(len):
            self.group_ids.append(groupResult['Resources'][count]['id'])

    def create(self, s, p):
        """ Creating group n times parallel and serial"""
        for count in range(s):
            self.__create_group_serial__(s, self.__build_group__())

        for count in range(p):
            self.__create_group_parallel__(p, self.__build_group__())

        self.__get_all_group_ids__()

    def read(self, s, p):
        """ Reading group n times parallel and serial"""
        for count in range(s):
            self.__get_group_serial__(s, self.__build_group__())

        for count in range(p):
            self.__get_group_parallel__(p, self.__build_group__())

    def replace(self, s, p):
        """ Replacing group n times parallel and serial"""
        for count in range(s):
            self.__replace_group_serial__(s, self.__build_group__())

        for count in range(p):
            self.__replace_group_parallel__(p, self.__build_group__())

    def update(self, s, p):
        """ Updating group n times parallel and serial"""
        for count in range(s):
            self.__update_group_serial__(s, self.__build_group__())

        for count in range(p):
            self.__update_group_parallel__(p, self.__build_group__())

    def delete(self, s, p):
        """ Deleting group n times parallel and serial"""
        for count in range(s):
            self.__delete_group_serial__(s, self.__build_group__())

        for count in range(p):
            self.__delete_group_parallel__(p, self.__build_group__(), s+count)

    def search(self, s, p):
        """ Searching on group n times parallel and serial"""
        for count in range(s):
            self.__search_group_serial__(s)

        for count in range(p):
            self.__search_group_parallel__(p)

    def all(self, s, p):
        """ Running all group tests n times parallel and serial"""
        self.create(s, p)
        self.read(s, p)
        self.replace(s, p)
        self.update(s, p)
        self.search(s, p)
        self.delete(s, p)

    @do_log
    def __create_group_serial__(self, runs_for_profiling, group):
        """ runs_for_profiling always second parameter
            group always third parameter """
        scim.create_group(group)

    @do_log
    def __create_group_parallel__(self, runs_for_profiling, group):
        """ runs_for_profiling always second parameter
            group always third parameter """
        p = Process(target=scim.create_group(group))
        p.start()
        procs.append(p)

    @do_log
    def __replace_group_serial__(self, runs_for_profiling, group):
        """ runs_for_profiling always second parameter
            group always third parameter """
        scim.replace_group(self.group_ids.pop(), group)

    @do_log
    def __replace_group_parallel__(self, runs_for_profiling, group):
        """ runs_for_profiling always second parameter
            group always third parameter """
        p = Process(target=scim.replace_group(self.group_ids.pop(), group))
        p.start()
        procs.append(p)

    @do_log
    def __update_group_serial__(self, runs_for_profiling, group):
        """ runs_for_profiling always second parameter
            group always third parameter """
        scim.update_group(self.group_ids.pop(), group)

    @do_log
    def __update_group_parallel__(self, runs_for_profiling, group):
        """ runs_for_profiling always second parameter
            group always third parameter """
        p = Process(target=scim.update_group(self.group_ids.pop(), group))
        p.start()
        procs.append(p)

    @do_log
    def __delete_group_serial__(self, runs_for_profiling, group):
        """ runs_for_profiling always second parameter
            group always third parameter """
        scim.delete_group(self.group_ids.pop())

    @do_log
    def __delete_group_parallel__(self, runs_for_profiling, group):
        """ runs_for_profiling always second parameter
            group always third parameter """
        p = Process(target=scim.delete_group(self.group_ids.pop()))
        p.start()
        procs.append(p)

    @do_log
    def __get_group_serial__(self, runs_for_profiling, group):
        """ runs_for_profiling always second parameter
            group always third parameter """
        scim.get_group(self.group_ids.pop())

    @do_log
    def __get_group_parallel__(self, runs_for_profiling, group):
        """ runs_for_profiling always second parameter
            group always third parameter """
        p = Process(target=scim.get_group(self.group_ids.pop()))
        p.start()
        procs.append(p)

    def __search_group_serial__(self, runs_for_profiling):
        self.__search_with_get_on_group_serial__(
            runs_for_profiling, self.__get_filter__('get'))
        self.__search_with_post_on_group_serial__(
            runs_for_profiling, self.__get_filter__('post'))

    @do_log
    def __search_with_get_on_group_serial__(self, runs_for_profiling, filter):
        """ runs_for_profiling always second parameter
            filter always third parameter """
        scim.search_with_get_on_groups(filter)

    @do_log
    def __search_with_post_on_group_serial__(self, runs_for_profiling, filter):
        """ runs_for_profiling always second parameter
            filter always third parameter """
        scim.search_with_post_on_groups(filter)

    def __search_group_parallel__(self, runs_for_profiling):
        self.__search_with_get_on_group_parallel__(
            runs_for_profiling, self.__get_filter__('get'))
        self.__search_with_post_on_group_parallel__(
            runs_for_profiling, self.__get_filter__('post'))

    @do_log
    def __search_with_get_on_group_parallel__(self, runs_for_profiling, filter):
        """ runs_for_profiling always second parameter
            filter always third parameter """
        p = Process(target=scim.search_with_get_on_groups(filter))
        p.start()
        procs.append(p)

    @do_log
    def __search_with_post_on_group_parallel__(self, runs_for_profiling, filter):
        """ runs_for_profiling always second parameter
            filter always third parameter """
        p = Process(target=scim.search_with_post_on_groups(filter))
        p.start()
        procs.append(p)
