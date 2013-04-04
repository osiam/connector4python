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

    private UserEntity entity;
    private User user;

    public SetUserFields(User user, UserEntity entity) {
        this.user = user;
        this.entity = entity;
    }

    public enum UserLists {
        EMAILS("emails"),
        IMS("ims"),
        PHONENUMBERS("phonenumbers"),
        PHOTOS("photos"),

        ENTITLEMENTS("entitlements"),
        ROLES("roles"),
        X509("x509certificates"),
        ADDRESSES("addresses");

        private final String isUserClass;

        UserLists(String isUserClass) {
            this.isUserClass = isUserClass;
        }

        private static Map<String, UserLists> fromString = new HashMap<>();

        static {
            for (UserLists d : values())
                fromString.put(d.isUserClass, d);
        }
    }

    static final String[] READ_ONLY_FIELDS = {"id", "meta", "groups"};
    static final Set<String> READ_ONLY_FIELD_SET = new HashSet<>(Arrays.asList(READ_ONLY_FIELDS));

    public void setFields() throws IllegalAccessException {
        Map<String, Field> userFields = getFieldsAsNormalizedMap(user.getClass());
        Map<String, Field> entityFields = getFieldsAsNormalizedMap(entity.getClass());
        SetUserListFields setUserListFields = new SetUserListFields(entity);
        SetUserSingleFields setUserSingleFields = new SetUserSingleFields(entity);

        for (Map.Entry<String, Field> e : userFields.entrySet()) {
            Field field = userFields.get(e.getKey());
            field.setAccessible(true);
            if (!READ_ONLY_FIELD_SET.contains(e.getKey())) {
                Object userValue = field.get(user);
                UserLists attributes = UserLists.fromString.get(e.getKey());
                if (attributes == null) {
                    setUserSingleFields.updateSingleField(user, entityFields.get(e.getKey()), userValue, e.getKey());
                } else {
                    setUserListFields.updateListFields(userValue, attributes);
                }
            }
        }
    }

    Map<String, Field> getFieldsAsNormalizedMap(Class<?> clazz) {
        Map<String, Field> fields = new HashMap<>();
        if (clazz != null) {
            for (Field f : clazz.getDeclaredFields()) {
                fields.put(f.getName().toLowerCase(), f);
            }
            fields.putAll(getFieldsAsNormalizedMap(clazz.getSuperclass()));
        }
        return fields;
    }
}