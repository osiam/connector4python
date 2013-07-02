name = 'sequence_10k'
description = 'The results of this test will show how long the system needed to reach the abort criteria.'
configuration = {'create': {'user': 10000, 'group': 1000}}

tests = [{'resource': 'user', 'method': 'search'},
         {'resource': 'user', 'method': 'update'},
         {'resource': 'user', 'method': 'replace'},
         {'resource': 'user', 'method': 'delete'},
         {'resource': 'group', 'method': 'search'}]