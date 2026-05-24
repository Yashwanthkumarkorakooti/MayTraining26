package com.app.dao_impl;

import com.app.dao.CustomerDao;
import com.app.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class CustomerDaoImpl implements CustomerDao {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Customer getByUsernmae(String customerUsername) {
        String sql = "select c from Customer c where c.user.username = ?1";
        TypedQuery<Customer> query = entityManager.createQuery(sql, Customer.class);
        query.setParameter(1,customerUsername);
        return query.getSingleResult();
    }
}
