name = 'search_group'
description = 'The results of this test will show how long the system needed to search an group via get.'
configuration = {'create': {'group': 1}}

tests = [{'resource': 'group', 'method': 'search'}]
