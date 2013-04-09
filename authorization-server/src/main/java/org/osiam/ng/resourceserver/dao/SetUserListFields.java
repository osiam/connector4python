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


    private
    UserEntity
            entity;
    private final
    SetUserFields.Mode
            mode;

    public SetUserListFields(UserEntity entity, SetUserFields.Mode mode) {
        this.entity =
                entity;
        this.mode =
                mode;
    }


    public void updateListFields(Object userValue, SetUserFields.UserLists attributes, Field field) {
        if (mode ==
                SetUserFields.Mode.PATCH &&
                userValue ==
                        null) {
            return;
        }
        if (attributes ==
                SetUserFields.UserLists.ADDRESSES) {
            setAddresses(userValue);
        } else {
            try {
                updateMultiValueList(userValue, attributes, field);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void updateMultiValueList(Object userValue, SetUserFields.UserLists attributes, Field field) throws IllegalAccessException, InstantiationException {
        if (userValue instanceof User.ContainsListOfMultiValue) {
            field.setAccessible(true);
            Object
                    o =
                    field.get(entity);
            Class<?>
                    clazz =
                    attributes.getClazz();
            User.ContainsListOfMultiValue
                    listOfMultiValue =
                    (User.ContainsListOfMultiValue) userValue;
            updateList((Collection<Object>) o, clazz, listOfMultiValue);
        }
    }

    private void updateList(Collection<Object> targetList, Class<?> clazz, User.ContainsListOfMultiValue listOfMultiValue) throws InstantiationException, IllegalAccessException {
        clearIfNotInPatchMode(targetList);
        for (MultiValuedAttribute m : listOfMultiValue.values()) {
            if (notDeleted(m, targetList)) {
                addSingleObject(clazz, targetList, m);
            }
        }
    }

    private boolean notDeleted(MultiValuedAttribute m, Collection<Object> targetList) {
        return !"delete".equals(m.getOperation()) ||
                !seekAndDelete(m, targetList);

    }

    private boolean seekAndDelete(MultiValuedAttribute m, Collection<Object> targetList) {
        for (Object o : targetList) {
            if (deleteSingleAttribute(m, targetList, o)) {
                return true;
            }
        }
        return false;
    }

    private boolean deleteSingleAttribute(MultiValuedAttribute m, Collection<Object> targetList, Object o) {
        ChildOfMultiValueAttribute
                valueAttribute =
                (ChildOfMultiValueAttribute) o;
        return valueAttribute.getValue() ==
                m.getValue() &&
                targetList.remove(o);
    }

    private void addSingleObject(Class<?> clazz, Collection<Object> collection, MultiValuedAttribute m) throws InstantiationException, IllegalAccessException {
        Object
                target =
                createSingleObject(clazz, m);
        removeWhenValueExists(collection, m);
        collection.add(target);

    }

    private void removeWhenValueExists(Collection<Object> collection, MultiValuedAttribute multiValuedAttribute) {
        if (mode !=
                SetUserFields.Mode.PATCH) {
            return;
        }
        for (Object o : collection) {
            if (removeWhenValueExists(collection, o, multiValuedAttribute)) {
                return;
            }

        }
    }


    private boolean removeWhenValueExists(Collection<Object> collection, Object o, MultiValuedAttribute multiValuedAttribute) {
        ChildOfMultiValueAttribute
                entityValue =
                (ChildOfMultiValueAttribute) o;
        return entityValue.getValue().equals(multiValuedAttribute.getValue()) &&
                collection.remove(o);
    }

    private Object createSingleObject(Class<?> clazz, MultiValuedAttribute m) throws InstantiationException, IllegalAccessException {
        Object
                target =
                clazz.newInstance();

        ((ChildOfMultiValueAttribute) target).setValue(String.valueOf(m.getValue()));

        if (target instanceof ChildOfMultiValueAttributeWithType) {
            ((ChildOfMultiValueAttributeWithType) target).setType(m.getType());
        }
        if (target instanceof ChildOfMultiValueAttributeWithTypeAndPrimary) {
            ((ChildOfMultiValueAttributeWithTypeAndPrimary) target).setPrimary(m.isPrimary() !=
                    null ?
                    m.isPrimary() :
                    false);
        }
        return target;
    }

    private void clearIfNotInPatchMode(Collection<?> collection) {
        if (mode !=
                SetUserFields.Mode.PATCH &&
                collection !=
                        null) {
            collection.clear();
        }
    }

    //TODO generalize
    void setAddresses(Object userValue) {
        //has no operation field and will always be replaced ...
        entity.getAddresses().clear();

        if (userValue !=
                null) {
            for (Address m : User.Addresses.class.cast(userValue).getAddress()) {
                AddressEntity
                        fromScim =
                        AddressEntity.fromScim(m);
                fromScim.setUser(entity);
                entity.getAddresses().add(fromScim);
            }
        }
    }
}