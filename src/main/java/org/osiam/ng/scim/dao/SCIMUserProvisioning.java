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

package org.osiam.ng.scim.dao;

import scim.schema.v2.User;

/**
 * This interface has the purpose to get SCIM user out of and into a database, which must be provided by the using
 * application. Inside UserController
 */
public interface SCIMUserProvisioning {
    /**
     * This method returns a SCIM user.
     * <p/>
     * It must throw an ResourceNotFoundException if no user got found.
     *
     * @param id the external identifier of an user
     * @return the found user
     * @throws org.osiam.ng.scim.exceptions.ResourceNotFoundException
     *          if no user with the given id got found
     */
    User getById(String id);


    /**
     * This method creates a user.
     *
     * @param user A user representation which should be created
     * @return the created user representation
     * @throws org.osiam.ng.scim.exceptions.ResourceExistsException
     *          if the resource already exists
     */
    User createUser(User user);

    /**
     * This method updates an user.
     *
     * @param id,   the external identifier of an user
     * @param user, an user representation which should be created
     * @return the updated user
     * @throws org.osiam.ng.scim.exceptions.ResourceNotFoundException
     *          if no user with the given id got found
     */
    User updateUser(String id, User user);
}
