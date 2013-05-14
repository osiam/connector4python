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

import org.osiam.ng.resourceserver.entities.GroupEntity;
import org.osiam.ng.resourceserver.entities.UserEntity;
import org.osiam.ng.scim.dao.SCIMUserProvisioning;
import org.osiam.ng.scim.exceptions.ResourceExistsException;
import org.osiam.ng.scim.schema.to.entity.GenericSCIMToEntityWrapper;
import org.osiam.ng.scim.schema.to.entity.SCIMEntities;
import org.springframework.stereotype.Service;
import scim.schema.v2.Group;
import scim.schema.v2.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: jtodea
 * Date: 15.03.13
 * Time: 17:36
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SCIMUserProvisioningBean extends SCIMProvisiongSkeleton<User> implements SCIMUserProvisioning {

    @Inject
    private UserDAO userDao;

    @Override
    protected GenericDAO getDao() {
        return userDao;
    }

    @Override
    public User create(User user) {
        UserEntity userEntity = UserEntity.fromScim(user);
        userEntity.setId(UUID.randomUUID());
        try {
            userDao.create(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceExistsException("The user with name " +
                    user.getUserName() +
                    " already exists.");
        }
        return userEntity.toScim();
    }

    @Override
    public List<User> search(String filter) {
        List<User> users = new ArrayList<>();
        for (Object g : getDao().search(filter)) { users.add(((UserEntity) g).toScim()); }
        return users;
    }

    @Override
    protected SCIMEntities getScimEntities() {
        return UserSCIMEntities.ENTITIES;
    }

    @Override
    public GenericSCIMToEntityWrapper.For getTarget() {
        return GenericSCIMToEntityWrapper.For.USER;
    }
}


