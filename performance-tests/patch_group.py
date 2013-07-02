name = 'patch_group'
description = 'The results of this test will show how long the system needed to update an group.'
configuration = {'create': {'group': 'per_call'}}

tests = [{'resource': 'group', 'method': 'update'}]
