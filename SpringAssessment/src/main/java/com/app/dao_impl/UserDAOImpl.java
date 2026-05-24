package com.app.dao_impl;

import com.app.dao.UserDAO;
import com.app.enums.Role;
import com.app.enums.Status;
import com.app.exceptions.ResourceNotFoundException;
import com.app.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAOImpl implements UserDAO {

    private final JdbcTemplate jdbcTemplate;

    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<User> mapper() {

        return (rs, num) -> {

            return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    Role.valueOf(rs.getString("role").toUpperCase()),
                    Status.valueOf(rs.getString("status").toUpperCase()),
                    rs.getString("last_login")
                    );
        };
    }

    @Override
    public void insert(User user) {
        String sql = """
                insert into users
                (username,password,email,phone,role,status,last_login)
                values(?,?,?,?,?,?,?)
                """;
        jdbcTemplate.update(
                sql, user.getUsername(),
                user.getPassword(), user.getEmail(),
                user.getPhone(), user.getRole().toString(),
                user.getStatus().toString(), user.getLastLogin()
        );

        System.out.println("User Added....");
    }

    @Override
    public List<User> getAll() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, mapper());
    }

    @Override
    public User getById(int id) throws ResourceNotFoundException {
        String sql = "select * from users where id=?";
        return jdbcTemplate.queryForObject(sql, mapper(), id);
    }

    @Override
    public void deleteUserById(int id) throws ResourceNotFoundException {
        String sql = "delete from users where id=?";
        int numRow = jdbcTemplate.update(sql, id);
        if (numRow == 0)
            throw new ResourceNotFoundException("Invalid id");

        System.out.println("User Deleted...");
    }

    @Override
    public void updateUserById(int id, User user) throws ResourceNotFoundException {

        String sql = """
                update users set username=?,
                password=?,email=?,phone=?,role=?,status=?,last_login=?
                where id=?
                """;

        int numRow =
                jdbcTemplate.update(
                        sql, user.getUsername(),
                        user.getPassword(), user.getEmail(),
                        user.getPhone(), user.getRole().toString(),
                        user.getStatus().toString(), user.getLastLogin(),
                        id
                );

        if (numRow == 0)
            throw new ResourceNotFoundException(
                    "Invalid id"
            );

        System.out.println("Record Updated.....");
    }
}