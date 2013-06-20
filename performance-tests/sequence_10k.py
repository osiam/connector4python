name = 'sequence_10k'
description = 'The results of this test will show how long the system needed to reach the abort criteria.'
configuration = {'create': {'User': 10000, 'Group': 1000}}

tests = [{'resource': 'User', 'method': 'search'},
         {'resource': 'User', 'method': 'update'},
         {'resource': 'User', 'method': 'replace'},
         {'resource': 'User', 'method': 'delete'},
         {'resource': 'Group', 'method': 'search'}]