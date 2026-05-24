package com.app.dao_impl;

import com.app.dao.AuthDao;
import com.app.model.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthDaoImpl implements AuthDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Users login(String username, String password) {
        Query query = entityManager.createQuery("select u from Users u where u.username=:username and password=:password");
        query.setParameter("username",username);
        query.setParameter("password",password);

        return (Users) query.getSingleResult();
    }
}
