name = 'patch_Group'
description = 'The results of this test will show how long the system needed to update an Group.'
configuration = {'create': {'Group': 'per_call'}}

tests = [{'resource': 'Group', 'method': 'update'}]