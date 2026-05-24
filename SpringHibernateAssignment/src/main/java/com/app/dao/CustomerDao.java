package com.app.dao;

import com.app.model.Customer;

public interface CustomerDao {

    Customer getByUsername(String username);

    void update(Customer customer);
}