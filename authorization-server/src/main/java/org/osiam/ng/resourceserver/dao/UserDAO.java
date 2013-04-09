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
import org.osiam.ng.scim.exceptions.ResourceNotFoundException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Repository
@Transactional
public class UserDAO {

    @Inject
    private PasswordEncoder passwordEncoder;
    @PersistenceContext
    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(UserEntity userEntity) {
        String hash = passwordEncoder.encodePassword(userEntity.getPassword(), userEntity.getInternalId());
        findExistingMultiValueAttributes(userEntity);
        userEntity.setPassword(hash);
        em.persist(userEntity);
    }

    //TODO ugly!!! is there a jpa solution for it?
    @SuppressWarnings("unchecked")
    private void findExistingMultiValueAttributes(UserEntity userEntity) {
        userEntity.setRoles((Set<RolesEntity>) replaceInstanceWithEntityInstance(userEntity.getRoles(), RolesEntity.class));
        userEntity.setEmails((Set<EmailEntity>) replaceInstanceWithEntityInstance(userEntity.getEmails(), EmailEntity.class));
        userEntity.setEntitlements((Set<EntitlementsEntity>) replaceInstanceWithEntityInstance(userEntity.getEntitlements(), EntitlementsEntity.class));
        userEntity.setIms((Set<ImEntity>) replaceInstanceWithEntityInstance(userEntity.getIms(), ImEntity.class));
        userEntity.setPhotos((Set<PhotoEntity>) replaceInstanceWithEntityInstance(userEntity.getPhotos(), PhotoEntity.class));
        userEntity.setPhoneNumbers((Set<PhoneNumberEntity>) replaceInstanceWithEntityInstance(userEntity.getPhoneNumbers(), PhoneNumberEntity.class));
        userEntity.setX509Certificates((Set<X509CertificateEntity>) replaceInstanceWithEntityInstance(userEntity.getX509Certificates(), X509CertificateEntity.class));
    }

    @SuppressWarnings("unchecked")
    private Set<?> replaceInstanceWithEntityInstance(Collection<?> roles, Class<?> clazz) {
        Set<Object> result = new HashSet<>();
        for (Object r : roles) {
            MultiValueAttributeEntitySkeleton originValue = (MultiValueAttributeEntitySkeleton) r;
            MultiValueAttributeEntitySkeleton entity =
                    (MultiValueAttributeEntitySkeleton) em.find(r.getClass(), originValue.getValue());
            if (entity != null) {
                result.add(clazz.cast(entity));
            } else {
                result.add(clazz.cast(originValue));
            }
        }
        return result;
    }

    public UserEntity getById(String id) {
        Query query = em.createNamedQuery("getUserById");
        query.setParameter("internalId", UUID.fromString(id));
        return getSingleUserEntity(query, id);
    }

    public UserEntity getByUsername(String userName) {
        Query query = em.createNamedQuery("getUserByUsername");
        query.setParameter("username", userName);
        return getSingleUserEntity(query, userName);
    }

    private UserEntity getSingleUserEntity(Query query, String identifier) {
        List result = query.getResultList();
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("No user " +
                    identifier +
                    " found.");
        }
        return (UserEntity) result.get(0);
    }

    public void update(UserEntity entity) {
        //not hashed ...
        String password = entity.getPassword();
        if (password.length() != 128) {
            entity.setPassword(passwordEncoder.encodePassword(password, entity.getInternalId()));
        }
        findExistingMultiValueAttributes(entity);
        em.merge(entity);
    }
}