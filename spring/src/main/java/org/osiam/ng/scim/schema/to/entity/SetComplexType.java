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

import java.lang.reflect.Field;
import java.util.Map;

public class SetComplexType {

    private final SetUserFields.Mode mode;
    private final Object entity;

    public SetComplexType(SetUserFields.Mode mode, Object entity) {

        this.mode = mode;
        this.entity = entity;
    }

    void setComplexType(Field entityField, Object value) throws IllegalAccessException, InstantiationException {
        Object target = getOrCreateComplexType(entityField);
        SetUserSingleFields setUserSingleFields = new SetUserSingleFields(target, mode);
        GetFieldsOfInputAndTarget getFieldsOfInputAndTarget =
                new GetFieldsOfInputAndTarget(value.getClass(), entityField.getType());
        setValuesOfComplexType(value, getFieldsOfInputAndTarget, setUserSingleFields);
        entityField.set(entity, target);
    }

    private Object getOrCreateComplexType(Field entityField) throws IllegalAccessException, InstantiationException {
        entityField.setAccessible(true);
        Object target = entityField.get(entity);
        if (target == null) {
            target = entityField.getType().newInstance();
        }
        return target;
    }

    private void setValuesOfComplexType(Object value, GetFieldsOfInputAndTarget getFieldsOfInputAndTarget, SetUserSingleFields setUserSingleFields) throws IllegalAccessException, InstantiationException {
        for (Map.Entry<String, Field> i : getFieldsOfInputAndTarget.getInputFields().entrySet()) {
            setValueOfComplexType(value, getFieldsOfInputAndTarget, setUserSingleFields, i);
        }
    }

    private void setValueOfComplexType(Object value, GetFieldsOfInputAndTarget getFieldsOfInputAndTarget, SetUserSingleFields setUserSingleFields, Map.Entry<String, Field> i) throws IllegalAccessException, InstantiationException {
        Field f = getFieldsOfInputAndTarget.getTargetFields().get(i.getKey());
        i.getValue().setAccessible(true);
        Object userValue = i.getValue().get(value);
        setUserSingleFields.updateSingleField(f, userValue, i.getKey());
    }


}