name = 'search_user'
description = 'The results of this test will show how long the system needed to search an user via get.'
configuration = {'create': {'user': 1}}

tests = [{'resource': 'user', 'method': 'search'}]
