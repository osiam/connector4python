/*
 * Copyright (C) 2013 tarent AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.osiam.ng.resourceserver.dao;

import org.osiam.ng.resourceserver.entities.UserEntity;
import org.osiam.ng.scim.dao.SCIMUserProvisioning;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scim.schema.v2.User;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
@Service

public class ScimUserProvisioningBean implements SCIMUserProvisioning {

    @Inject
    private UserDAO userDao;


    @Override
    public User getById(String id) {
        UserEntity userEntity = userDao.getById(id);
        return userEntity.toScim();
    }

    @Override
    public User createUser(User user) {
        UserEntity userEntity = UserEntity.fromScim(user);
        userDao.createUser(userEntity);
//        UserEntity userEntity = userDao.getByUsername(user.getUserName());
        return user;
    }
}
