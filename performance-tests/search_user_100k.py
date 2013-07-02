name = 'search_user_100k'
description = 'The results of this test will show how long the system needed to search an user via get.'
configuration = {'create': {'user': 100000, 'group': 10000}}

tests = [{'resource': 'user', 'method': 'search'}]
