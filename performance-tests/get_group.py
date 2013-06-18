name = 'get_Group'
description = 'The results of this test will show how long the system needed to get Group with an empty database.'
configuration = {'create': {'Group': 'per_call'}}

tests = [{'resource': 'Group', 'method': 'read'}]
