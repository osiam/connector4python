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

import scim.schema.v2.User;

import java.lang.reflect.Field;
import java.util.*;

/**
 * This class has the purpose to update fields from a ScimUser to an entity class.
 * <p/>
 * It will have to modes: one is PUT and the second will be PATCH.
 * <p/>
 * This class should be generalized and in the SCIM project to make it easier to create SCIM-Provisioning-Classes
 */
public class SetUserFields {


    private Object entity;
    private User user;
    private final Mode mode;
    private final SCIMEntities scimEntities;

    public SetUserFields(User user, Object entity, Mode mode, SCIMEntities scimEntities) {
        this.user = user;
        this.entity = entity;
        this.mode = mode;
        this.scimEntities = scimEntities;
    }

    public enum Mode {
        PATCH, POST
    }

    static final String[] READ_ONLY_FIELDS = {"id", "meta", "groups"};

    static final Set<String> READ_ONLY_FIELD_SET = new HashSet<>(Arrays.asList(READ_ONLY_FIELDS));

    static final Set<String> NOT_DELETABLE = new HashSet<>(Arrays.asList("username"));

    public void setFields() throws IllegalAccessException, InstantiationException {
        //        Map<String, Field> userFields = getFieldsOfInputAndTarget.getFieldsAsNormalizedMap(user.getClass());
        //Map<String, Field> entityFields = getFieldsOfInputAndTarget.getFieldsAsNormalizedMap(entity.getClass());

        GetFieldsOfInputAndTarget fields = new GetFieldsOfInputAndTarget(user.getClass(), entity.getClass());
        SetUserListFields setUserListFields = new SetUserListFields(entity, mode);
        SetUserSingleFields setUserSingleFields = new SetUserSingleFields(entity, mode);
        Set<String> doNotUpdateThem = deleteAttributes(fields.getTargetFields(), setUserSingleFields);

        for (Map.Entry<String, Field> e : fields.getInputFields().entrySet()) {
            Field field = fields.getInputFields().get(e.getKey());
            field.setAccessible(true);
            if (!READ_ONLY_FIELD_SET.contains(e.getKey()) && !doNotUpdateThem.contains(e.getKey())) {
                Object userValue = field.get(user);
                SCIMEntities.Entity attributes = scimEntities.fromString(e.getKey());
                if (attributes == null) {
                    setUserSingleFields.updateSingleField(fields.getTargetFields().get(e.getKey()), userValue, e.getKey());
                } else {
                    setUserListFields.set(userValue, attributes, fields.getTargetFields().get(e.getKey()));
                }
            }
        }
    }

    private Set<String> deleteAttributes(Map<String, Field> entityFields, SetUserSingleFields setUserSingleFields) throws IllegalAccessException {
        Set<String> doNotUpdateThem = new HashSet<>();
        if (mode == Mode.PATCH && user.getMeta() != null) {
            for (String s : user.getMeta().getAttributes()) {
                String key = s.toLowerCase();
                if (!NOT_DELETABLE.contains(key) && !READ_ONLY_FIELD_SET.contains(key)) {
                    deleteAttribute(entityFields, setUserSingleFields, doNotUpdateThem, key);
                }
            }
        }
        return doNotUpdateThem;
    }

    private void deleteAttribute(Map<String, Field> entityFields, SetUserSingleFields setUserSingleFields, Set<String> doNotUpdateThem, String key) throws IllegalAccessException {
        //may use pattern instead ..
        if (key.contains(".")) {
            deletePartsOfAComplexAttribute(entityFields, doNotUpdateThem, key);
        } else {
            deleteSimpleAttribute(entityFields, setUserSingleFields, doNotUpdateThem, key);
        }
    }

    private void deleteSimpleAttribute(Map<String, Field> entityFields, SetUserSingleFields setUserSingleFields, Set<String> doNotUpdateThem, String key) throws IllegalAccessException {
        Field entityField = entityFields.get(key);
        setUserSingleFields.setEntityFieldToNull(entityField);
        doNotUpdateThem.add(key);
    }

    private void deletePartsOfAComplexAttribute(Map<String, Field> entityFields, Set<String> doNotUpdateThem, String key) throws IllegalAccessException {
        String[] complexMethod = key.split("\\.");

        int lastElement = complexMethod.length - 1;
        Object object = entity;
        GetComplexEntityFields lastEntityFields = null;
        for (int i = 0; i < lastElement; i++) {
            if (READ_ONLY_FIELD_SET.contains(complexMethod[i])) {
                return;
            }
            lastEntityFields = new GetComplexEntityFields(entityFields, complexMethod[i], object).invoke();
        }
        assert lastEntityFields != null;
        lastEntityFields.nullValue(complexMethod[lastElement]);
        doNotUpdateThem.add(complexMethod[0]);
    }

    private class GetComplexEntityFields {
        private Map<String, Field> entityFields;
        private String key;
        private Object lastObjectOfField;

        public GetComplexEntityFields(Map<String, Field> entityFields, String key, Object lastObjectOfField) {
            this.entityFields = entityFields;
            this.key = key;
            this.lastObjectOfField = lastObjectOfField;
        }


        public GetComplexEntityFields invoke() throws IllegalAccessException {
            Field field = entityFields.get(key);
            field.setAccessible(true);
            lastObjectOfField = field.get(lastObjectOfField);
            Class<?> declaringClass = field.getType();
            entityFields = (new GetFieldsOfInputAndTarget()).getFieldsAsNormalizedMap(declaringClass);
            return this;
        }

        public void nullValue(String s) throws IllegalAccessException {
            Field field = entityFields.get(s);
            field.setAccessible(true);
            field.set(lastObjectOfField, null);
        }
    }
}