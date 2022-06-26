package com.finalproject.Repository;

import com.finalproject.Models.POJO.User;
import com.google.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class UserRepositoryImpl implements UserRepository {
    private final EntityManager em;

    @Inject
    public UserRepositoryImpl(EntityManager em) {
        this.em = em;
    }


    @Override
    public User getUserById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public User getUserByUsername(String username) {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);

            return query.getSingleResult();
    }

    @Override
    public User getUserByEmail(String mail) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.mail = :mail", User.class);
        query.setParameter("mail", mail);
        return query.getSingleResult();
    }

    @Override
    public User saveUser(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        return user;
    }

    @Override
    public void deleteUser(User user) {
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
    }
}
