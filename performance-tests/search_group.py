name = 'search_Group'
description = 'The results of this test will show how long the system needed to search an Group via get.'
configuration = {'create': {'Group': 1}}

tests = [{'resource': 'Group', 'method': 'search'}]
