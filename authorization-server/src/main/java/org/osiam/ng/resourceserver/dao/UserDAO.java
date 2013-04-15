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
import java.util.logging.Level;

@Repository
@Transactional
public class UserDAO extends GetInternalIdSkeleton implements GenericDAO<UserEntity> {

    @Inject
    private PasswordEncoder passwordEncoder;

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void create(UserEntity userEntity) {
        String hash = passwordEncoder.encodePassword(userEntity.getPassword(), userEntity.getId());
        findExistingMultiValueAttributes(userEntity);
        userEntity.setPassword(hash);
        em.persist(userEntity);
    }

    //TODO ugly!!! is there a jpa solution for it?
    @SuppressWarnings("unchecked")
    private void findExistingMultiValueAttributes(UserEntity user) {
        user.setRoles(
                (Set<RolesEntity>) replaceInstanceWithEntityInstance(user.getRoles(), RolesEntity.class));
        user.setEmails(
                (Set<EmailEntity>) replaceInstanceWithEntityInstance(user.getEmails(), EmailEntity.class));
        user.setEntitlements(
                (Set<EntitlementsEntity>) replaceInstanceWithEntityInstance(user.getEntitlements(),
                        EntitlementsEntity.class));
        user.setIms((Set<ImEntity>) replaceInstanceWithEntityInstance(user.getIms(), ImEntity.class));
        user.setPhotos(
                (Set<PhotoEntity>) replaceInstanceWithEntityInstance(user.getPhotos(), PhotoEntity.class));
        user.setPhoneNumbers(
                (Set<PhoneNumberEntity>) replaceInstanceWithEntityInstance(user.getPhoneNumbers(),
                        PhoneNumberEntity.class));
        user.setX509Certificates(
                (Set<X509CertificateEntity>) replaceInstanceWithEntityInstance(user.getX509Certificates(),
                        X509CertificateEntity.class));
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

    @Override
    public UserEntity getById(String id) {
        try {
            return getInternalIdSkeleton(id);
        } catch (ClassCastException c) {
            LOGGER.log(Level.WARNING, c.getMessage(), c);
            throw new ResourceNotFoundException("Resource " + id + " is not an User.");
        }
    }

    public UserEntity getByUsername(String userName) {
        Query query = em.createNamedQuery("getUserByUsername");
        query.setParameter("username", userName);
        return getSingleInternalIdSkeleton(query, userName);
    }


    @Override
    public UserEntity update(UserEntity entity) {
        //not hashed ...
        String password = entity.getPassword();
        if (password.length() != 128) {
            entity.setPassword(passwordEncoder.encodePassword(password, entity.getId()));
        }
        findExistingMultiValueAttributes(entity);
        em.merge(entity);
        return entity;
    }

    @Override
    public void delete(String id) {
        UserEntity userEntity = getById(id);
        em.remove(userEntity);

    }
}