name = 'search_user_100k'
description = 'The results of this test will show how long the system needed to search an user via get.'
configuration = {'create': {'User': 100000, 'Group': 10000}}

tests = [{'resource': 'User', 'method': 'search'}]
