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

import org.osiam.ng.resourceserver.entities.*;
import scim.schema.v2.Address;
import scim.schema.v2.MultiValuedAttribute;
import scim.schema.v2.User;

import java.lang.reflect.Field;
import java.util.Collection;

public class SetUserListFields {


    private UserEntity entity;

    public SetUserListFields(UserEntity entity) {
        this.entity = entity;
    }

    @SuppressWarnings("unchecked")
    public void updateListFields(Object userValue, SetUserFields.UserLists attributes, Field field) {
        switch (attributes) {
            case ADDRESSES:
                setAddresses(userValue);
                break;
        }
        try {
            updateMultiValueList(userValue, attributes, field);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private void updateMultiValueList(Object userValue, SetUserFields.UserLists attributes, Field field) throws IllegalAccessException, InstantiationException {
        if (userValue instanceof User.ContainsListOfMultiValue) {
            field.setAccessible(true);
            Object o = field.get(entity);
            Class<?> clazz = attributes.getClazz();
            User.ContainsListOfMultiValue listOfMultiValue = (User.ContainsListOfMultiValue) userValue;
            if (o instanceof Collection) {
                replaceCompleteList((Collection<Object>) o, clazz, listOfMultiValue);
            }
        }
    }

    private void replaceCompleteList(Collection<Object> targetList, Class<?> clazz, User.ContainsListOfMultiValue listOfMultiValue) throws InstantiationException, IllegalAccessException {
        targetList.clear();
        for (MultiValuedAttribute m : listOfMultiValue.values()) {
            addSingleObject(clazz, targetList, m);
        }
    }

    private void addSingleObject(Class<?> clazz, Collection<Object> collection, MultiValuedAttribute m) throws InstantiationException, IllegalAccessException {
        Object target = clazz.newInstance();
        if (target instanceof MinimalChildOfMultiValueAttribute) {
            ((MinimalChildOfMultiValueAttribute) target).setValue(String.valueOf(m.getValue()));
        }
        if (target instanceof ChildOfMultiValueAttributeWithType) {
            ((ChildOfMultiValueAttributeWithType) target).setType(m.getType());
        }
        if (target instanceof FullChildOfMultiValueAttribute)
            ((FullChildOfMultiValueAttribute) target).setPrimary(m.isPrimary() != null ? m.isPrimary() : false);
        collection.add(target);
    }


    void setAddresses(Object userValue) {
        entity.getAddresses().clear();
        if (userValue != null) {
            for (Address m : User.Addresses.class.cast(userValue).getAddress()) {
                AddressEntity fromScim = AddressEntity.fromScim(m);
                fromScim.setUser(entity);
                entity.getAddresses().add(fromScim);
            }
        }
    }
}