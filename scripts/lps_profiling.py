__author__ = 'jtodea'

import logging
from datetime import datetime
import sys

logger = logging.getLogger(__name__)

def do_log(func):
    def wrapped(*args, **kwargs):
        tstart = datetime.now()
        result = func(*args, **kwargs)
        tstop = datetime.now()
        logger.info('{0};{1};{2}'.format(func.__name__, tstop-tstart, sys.getsizeof(result)))
        return result
    return wrapped

def __init__(log_file_path, script_name):
    print 'init profiling'
    file_handler = logging.FileHandler('{0}/{1}_{2}.log'.format(log_file_path, datetime.now().isoformat(), script_name))
    formatter = logging.Formatter('%(message)s')
    file_handler.setFormatter(formatter)
    logger.addHandler(file_handler)
    logger.setLevel(logging.INFO)
    logger.info('name;running_time;data_volume_in_bytes')