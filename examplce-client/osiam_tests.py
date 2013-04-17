# coding=utf-8
"""This file is "SDK" for OSIAM NG it should enable you to use OSIAM NG easily"""

import json

import requests

import unittest

import osiam
from obtain_access_token import FakeUser
from mock import patch
from requests.exceptions import RequestException, ConnectionError



__author__ = 'phil'

log = True


class SCIMTestCase(unittest.TestCase):
    scim = None

    def setUp(self):
        """get an access_token"""
        token = FakeUser('marissa', 'koala', 'testClient', 'http://localhost:5000/oauth2').get_access_token()
        self.scim = osiam.SCIM('http://localhost:8080/authorization-server', token)

    def tearDown(self):
        print "tear Down"

    def test_get_an_user(self):

        with patch.object(requests, 'get') as mock_method:
            user = osiam.SCIMUser(userName= 'userName')
            mock_method.return_value.text = json.dumps(user, default=osiam.convert_to_builtin_type)
            o = self.scim.get_user('id')
            assert o is not None

if __name__ == '__main__':
    unittest.main()

