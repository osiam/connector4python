name = 'search_user_10k'
description = 'The results of this test will show how long the system needed to search an user via get.'
configuration = {'create': {'user': 10000, 'group': 1000}}

tests = [{'resource': 'user', 'method': 'search'}]
