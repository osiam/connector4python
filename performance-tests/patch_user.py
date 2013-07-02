name = 'patch_user'
description = 'The results of this test will show how long the system needed to update an user.'
configuration = {'create': {'user': 'per_call'}}

tests = [{'resource': 'user', 'method': 'update'}]
