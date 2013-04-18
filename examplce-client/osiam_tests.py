# coding=utf-8
"""This file is "SDK" for OSIAM NG it should enable you to use OSIAM NG easily"""

import json
import unittest

import requests
from mock import patch

import osiam


__author__ = 'phil'

log = True


class SCIMTestCase(unittest.TestCase):
    scim = osiam.SCIM('http://localhost:8080/authorization-server', "token")
    user = osiam.SCIMUser(userName='userName')
    group = osiam.SCIMGroup(displayName='displayName')

    def __mock_call__(self, methodToMock, result, func, *funcArgs):
        with patch.object(requests, methodToMock) as mock_method:
            mock_method.return_value.text = json.dumps(result, default=osiam.convert_to_builtin_type)
            mock_method.return_value.content = json.dumps(result, default=osiam.convert_to_builtin_type)
            o = func(*funcArgs)
            assert o is not None
            return o

    def __test_result_of__(self, methodToMock, result, func, *funcArgs):
            self.assertEquals(self.__mock_call__(methodToMock, result, func, *funcArgs).__class__, result.__class__)


    def test_get_an_user(self):
        self.__test_result_of__('get', self.user, self.scim.get_user, 'id')

    def test_get_a_group(self):
        self.__test_result_of__('get', self.user, self.scim.get_group, 'id')

    def test_create_an_user(self):
            self.__test_result_of__('post', self.user, self.scim.create_user, self.user)

    def test_create_a_group(self):
        self.__test_result_of__('post', self.group, self.scim.create_group, self.group)

    def test_replace_an_user(self):
        self.__test_result_of__('put', self.user, self.scim.replace_user, 'id',self.user)

    def test_replace_a_group(self):
        self.__test_result_of__('put', self.group, self.scim.replace_group, 'id',self.group)

    def test_update_an_user(self):
            self.__test_result_of__('patch', self.user, self.scim.update_user, 'id',self.user)

    def test_replace_a_group(self):
        self.__test_result_of__('patch', self.group, self.scim.update_group, 'id',self.group)

    def test_delete_an_user(self):
        self.__mock_call__('delete', self.user, self.scim.delete_user, 'id')

    def test_delete_a_group(self):
        o = self.__mock_call__('delete', self.group, self.scim.delete_group, 'id')

    if __name__ == '__main__':
        unittest.main()

