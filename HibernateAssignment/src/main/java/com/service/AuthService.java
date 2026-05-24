package com.service;

import com.model.Users;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AuthService {
    private Session session;
    public AuthService(Session session) {
        this.session = session;
    }


    public Users login(String username, String password) {
        Transaction tx = session.beginTransaction();
        Users user = session.createQuery("from Users where username=:username and password=:password", Users.class)
                .setParameter("username",username)
                .setParameter("password",password)
                .getSingleResult();
        tx.commit();
        return user;
    }
}
