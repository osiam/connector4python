name = 'search_user_500k'
description = 'The results of this test will show how long the system needed to search an user via get.'
configuration = {'create': {'user': 500000, 'group': 50000}}

tests = [{'resource': 'user', 'method': 'search'}]
