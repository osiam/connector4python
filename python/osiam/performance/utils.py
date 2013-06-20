
def get_filter(method):
    """ Defining the filter for search accordingly the method (post | get)"""
    if method == 'get':
        return 'filter=displayName%20pr&count=100'
    if method == 'post':
        return '{\'filter\':\'displayName%20pr\', \'count\':\'100\'}'
