name = 'search_user_10k'
description = 'The results of this test will show how long the system needed to search an user via get.'
configuration = {'create': {'User': 500000, 'Group': 50000}}

tests = [{'resource': 'User', 'method': 'search'}]
