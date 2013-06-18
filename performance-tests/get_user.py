name = 'get_user'
description = 'The results of this test will show how long the system needed to get user with an empty database.'
configuration = {'create': {'User': 'per_call'}}

tests = [{'resource': 'User', 'method': 'read'}]
