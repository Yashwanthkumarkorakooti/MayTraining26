package com.app.dao;

import com.app.model.User;

import java.util.List;

public interface UserDAO {

    void insert(User user);

    List<User> getAll();

    User getById(int id);

    void deleteUserById(int id);

    void updateUserById(int id, User user);
}