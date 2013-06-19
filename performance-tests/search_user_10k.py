name = 'search_user'
description = 'The results of this test will show how long the system needed to search an user via get.'
configuration = {'create': {'User': 10000, 'Group': 1000}}

tests = [{'resource': 'User', 'method': 'search'}]
