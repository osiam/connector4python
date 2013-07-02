name = 'put_user'
description = 'The results of this test will show how long the system needed to replace an user.'
configuration = {'create': {'user': 'per_call'}}

tests = [{'resource': 'user', 'method': 'replace'}]
