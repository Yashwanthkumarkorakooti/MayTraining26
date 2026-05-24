package com.app.dao;

import com.app.model.Users;

public interface AuthDao {
    Users login(String username, String password);
}
