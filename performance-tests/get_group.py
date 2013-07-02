name = 'get_group'
description = 'The results of this test will show how long the system needed to get group with an empty database.'
configuration = {'create': {'group': 'per_call'}}

tests = [{'resource': 'group', 'method': 'read'}]
