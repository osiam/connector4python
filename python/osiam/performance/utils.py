def get_ids(f, amount):
    result = []
    group_results = f('count={}'.format(amount))
    for i in group_results['Resources']:
        result.append(i['id'])
    return result


def get_all_ids(f):
    # ask for one to get the total_results
    total_results = f('count=1')['totalResults']
    return get_ids(f, total_results)

def get_filter(method):
    """ Defining the filter for search accordingly the method (post | get)"""
    if method == 'get':
        return 'filter=displayName%20pr&count=100'
    if method == 'post':
        return '{\'filter\':\'displayName%20pr\', \'count\':\'100\'}'
