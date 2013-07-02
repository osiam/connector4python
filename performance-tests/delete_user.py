name = 'delete_user'
description = 'The results of this test will show how long the system needed to delete user.'
configuration = {'create': {'user': 'per_call'}}

tests = [{'resource': 'user', 'method': 'delete'}]
