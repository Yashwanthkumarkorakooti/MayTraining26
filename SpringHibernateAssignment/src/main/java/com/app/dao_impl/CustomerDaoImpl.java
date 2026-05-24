package com.app.dao_impl;

import com.app.dao.CustomerDao;
import com.app.exception.ResourceNotFoundException;
import com.app.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CustomerDaoImpl
        implements CustomerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Customer getByUsername(
            String username
    ) {

        String sql = "select c from Customer c " + "where c.users.username = ?1";
        TypedQuery<Customer> query = entityManager.createQuery(sql, Customer.class);
        query.setParameter(1, username);
        Customer customer = query.getSingleResult();

        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found");
        }

        return customer;
    }

    @Override
    public void update(Customer customer) {
        entityManager.merge(customer);
    }
}