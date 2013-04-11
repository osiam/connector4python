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

package org.osiam.ng.scim.schema.to.entity;

import scim.schema.v2.Name;

import java.lang.reflect.Field;

public class SetUserSingleFields {
    private final SetUserFields.Mode mode;
    private final SetComplexType setComplexType;
    private Object entity;

    public SetUserSingleFields(Object entity, SetUserFields.Mode mode) {
        this.entity = entity;
        this.mode = mode;
        setComplexType = new SetComplexType(mode, entity);
    }

    public void updateSingleField(Field entityField, Object userValue, String key) throws IllegalAccessException, InstantiationException {
        if (mode == SetUserFields.Mode.PATCH && userValue == null) {
            return;
        }
        if (userValue instanceof Name) {
            setComplexType.setComplexType(entityField, userValue);
        } else {
            if (!(key == "password" && userValue != null && String.valueOf(userValue).isEmpty())) {
                updateSimpleField(entityField, userValue);
            }
        }
    }


    public void setEntityFieldToNull(Field entityField) throws IllegalAccessException {
        updateSimpleField(entityField, null);
    }

    private void updateSimpleField(Field entityField, Object userValue) throws IllegalAccessException {
        if (entityField != null) {
            entityField.setAccessible(true);
            entityField.set(entity, userValue);
        }
    }
}