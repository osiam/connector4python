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
import org.osiam.ng.scim.dao.SCIMGroupProvisioning;
import org.osiam.ng.scim.exceptions.ResourceExistsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import scim.schema.v2.Group;

import javax.inject.Inject;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class SCIMGroupProvisioningBean implements SCIMGroupProvisioning {
    Logger logger = Logger.getLogger(SCIMGroupProvisioningBean.class.getName());
    @Inject
    private GroupDAO groupDAO;

    @Override
    public Group createGroup(Group group) {
        GroupEntity enrichedGroup = GroupEntity.fromScim(group);
        enrichedGroup.setId(UUID.randomUUID());
        try {
            groupDAO.create(enrichedGroup);
        } catch (DataIntegrityViolationException e) {
            logger.log(Level.INFO, "An exception got thrown while creating a group.", e);
            throw new ResourceExistsException(group.getDisplayName() + " already exists.");
        }
        return enrichedGroup.toScim();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Group getById(String id) {
        return groupDAO.get(id).toScim();
    }

    @Override
    public void deleteGroup(String id) {
        groupDAO.delete(id);
    }
}
