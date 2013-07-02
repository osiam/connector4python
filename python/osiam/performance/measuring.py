from datetime import datetime
from multiprocessing import Process
import logging
import sys


logger = logging.getLogger(__name__)
# If a function needs more time max_response_time it is considered as
# unsuccessful.
max_response_time = 0
# Storage for running process, it will be used to lock until all process are
# finished to prevent mixed up measurings.
procs = []
# A list of all measured durations, it will be used to find min, max and
# calculate avg
complete_duration = []
# descripes how many error occured during the test
error_amount = 0
# descripes how many functions needed more time then max_response_time
timeout_amount = 0


def measure(func):
    """ This function is a decorator method to measure the time a method
    needed.

    It will storage the duration in complete_duration, set the error_amount + 1
    if an error occurs and will set the timeout_amount if the method needed
    more time then set in max_response_time.

    The saved values will be used in exec_function."""

    def wrapped(*args, **kwargs):
        tstart = datetime.now()
        result = func(*args, **kwargs)
        tstop = datetime.now()
        duration = tstop - tstart
        global error_amount, timeout_amount
        ms = duration.microseconds / 1000
        # delete does not return json, so it is a response directly
        if result.__class__.__name__ is 'Response':
            if result.status_code is not 200:
                error_amount = error_amount + 1
        # when it is not a response, check if there is error keyword
        elif result.get('error_code') is not None:
            error_amount = error_amount + 1
        if ms >= max_response_time:
            timeout_amount = timeout_amount + 1
        logger.info('{0};{1};{2};{3};{4}'.format(func.__name__, args[0],
                                                 duration,
                                                 sys.getsizeof(args[1]),
                                                 result))
        complete_duration.append(ms)

    return wrapped


def create_filehandler(log_file_path, script_name):
    """ This method will create a filehandler for logging in the format
    log_file_path/date-script_name"""
    file_handler = logging.FileHandler('{0}/{1}_{2}.log'.format(
        log_file_path, datetime.now().isoformat(), script_name))
    formatter = logging.Formatter('%(message)s')
    file_handler.setFormatter(formatter)
    return file_handler


def exec_function(f, s, p, generate_data, data):
    """ executes a given function f, with the generated data of generate_data,
    s iterations, p times parallel and summarises the duration of all calls.

    The measurement is done by measure."""

    def start_parallel_process(target):
        """ Starts a parallel Process and append it to procs"""
        p = Process(target=target)
        p.start()
        procs.append(p)

    def calculate_percent(e):
        """Calulates the percent of e in relation to the length of
        complete_duration"""
        return int(float(sum(e)) / float(len(complete_duration)) * 100)

    global complete_duration, error_amount, procs, timeout_amount
    #Reset the lists
    complete_duration = []
    error_amounts = []
    timeout_amounts = []
    for iteration in range(s):
        #error_ and timeout_amount are restricted to one iteration of a test.
        error_amount = 0
        timeout_amount = 0
        #start the processes
        for parallel in range(p):
            start_parallel_process(f(p, generate_data(data)))
        #join all processes to prevent that a measurement of an unfinished
        #process run into an other iteration
        for process in procs:
            process.join()
        error_amounts.append(error_amount)
        timeout_amounts.append(timeout_amount)
        procs = []
    return {'min': min(complete_duration),
            'max': max(complete_duration),
            'avg': sum(complete_duration) / len(complete_duration),
            'timeout': calculate_percent(timeout_amounts),
            'error': calculate_percent(error_amounts)}


def default_generate_data(data):
    return data
