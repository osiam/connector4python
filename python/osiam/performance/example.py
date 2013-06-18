#! /usr/bin/env python

import multiprocessing
import copy
from osiam import connector
import random


def do_log(func):
    def wrapped(*args, **kwargs):
        func(*args, **kwargs)
        print ('doLog for {0} with {1}'.format(func.__name__, args[0],))
    return wrapped

def calcall(arg):
    return calc(*arg)

def calc(func, arg):
    result = func(arg)
    return '%s says that %s(%s) = %s' % (
        multiprocessing.current_process().name,
        func.__name__, arg, result
    )

def mul(a):
    return a*a

def create_pool():
    PROCESSES = 4
    print 'Creating pool with %d processes' % PROCESSES
    pool = multiprocessing.Pool(PROCESSES)
    return pool

def run_with_pool(pool):
    TASKS = [(mul, 2)] + [(mul, 4)] + [(mul, 6)] + [(mul, 8)]

    #results = pool.imap(calcall, TASKS)
    results = [pool.apply_async(calc, t) for t in TASKS]
    for i in results:
        #print ('Result: %s' % i)
        print ('Result: %s' % i.get())

class Test:
    def run_without_pool(self):
        q = multiprocessing.Queue()
        test = connector.SCIMUser(
            userName='user_name{0}'.format(random.random()),
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
        for i in range(4):
            self.start_process(test, q)
            print (q.get())

    @do_log
    def start_process(self, test, q):
        p = multiprocessing.Process(target=self.without_pool, args=(test, q,))
        p.start()

    def without_pool(self, arg, q):
        result = copy.deepcopy(arg)
        q.put('%s says that %s(%s) = %s' % (
            multiprocessing.current_process().name,
            'without_pool', arg, result
        ))

if __name__ == '__main__':
    pool = create_pool()
    run_with_pool(pool)
    Test().run_without_pool()
    print 'DONE!'