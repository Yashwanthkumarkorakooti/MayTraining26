package com.service;

import com.enums.Status;
import com.exception.ResourceNotFoundException;
import com.model.Customer;
import com.model.Users;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class UserService {

    private final Session session;
    private CustomerService customerService;

    public UserService(Session session) {

        this.session = session;
        customerService = new CustomerService(session);
    }

    public void insert(Users users) {

        Transaction tx = session.beginTransaction();

        session.persist(users);

        tx.commit();
    }

    public void deleteRecord(int id) {

        Transaction tx = session.beginTransaction();

        Users users = session.find(Users.class, id);

        if(users == null){
            tx.commit();
            throw new ResourceNotFoundException("Invalid ID given...");
        }

        session.createMutationQuery(
                        "delete from Users where id=:id"
                )
                .setParameter("id", id)
                .executeUpdate();

        tx.commit();
    }

    public List<Users> getAllUsers() {

        Transaction tx = session.beginTransaction();

        List<Users> list =
                session.createQuery(
                        "from Users",
                        Users.class
                ).list();

        tx.commit();

        return list;
    }

    public Users getById(int id) {

        Transaction tx = session.beginTransaction();

        Users users =
                session.find(Users.class, id);

        tx.commit();

        if(users == null){
            throw new ResourceNotFoundException(
                    "Invalid ID given..."
            );
        }

        return users;
    }

    public void addUser(
            Users users,
            String customerUsername
    ) {

        Customer customer =
                customerService.getByUsername(
                        customerUsername
                );

        users.setStatus(Status.ACTIVE);
        users.setLast_login(LocalDateTime.now());

        Transaction tx =
                session.beginTransaction();

        session.persist(users);

        tx.commit();
    }

    public void deleteById(
            int userId,
            String username
    ) {

        Transaction tx =
                session.beginTransaction();

        Users users =
                session.find(
                        Users.class,
                        userId
                );

        tx.commit();

        if(users == null){
            throw new ResourceNotFoundException(
                    "User ID Invalid!!"
            );
        }

        Customer customer =
                customerService.getByUsername(
                        username
                );

        tx =
                session.beginTransaction();

        session.remove(users);

        tx.commit();
    }
}