package com.controller;

import com.config.HibernateConfig;
import com.enums.Role;
import com.enums.Status;
import com.exception.ResourceNotFoundException;
import com.model.Users;
import com.service.AuthService;
import com.service.UserService;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;

import java.util.List;
import java.util.Scanner;

public class UserController {

    public static void main(String[] args) {

        Session session = HibernateConfig.getSessionFactory().openSession();
        Scanner sc = new Scanner(System.in);
        UserService userService = new UserService(session);
        AuthService authService = new AuthService(session);

        System.out.println("------- MaverickBank : LOGIN -------");
        System.out.println("Enter UserName: ");
        String username = sc.nextLine();
        System.out.println("Enter Password: ");
        String password = sc.nextLine();

        try {
            Users users = authService.login(username, password);
            switch (users.getRole().toString()) {
                case "CUSTOMER":
                    System.out.println("Customer Menu");

                    while(true){
                        System.out.println("\n1. Add User");
                        System.out.println("2. Delete User by ID");
                        System.out.println("3. Fetch All Users");
                        System.out.println("4. Get User By ID");
                        System.out.println("0. Exit");

                        int op = sc.nextInt();
                        if(op == 0){
                            break;
                        }

                        switch(op){
                            case 1:
                                Users newUser = new Users();
                                sc.nextLine();
                                System.out.println("Enter Username: ");
                                newUser.setUsername(sc.nextLine());
                                System.out.println("Enter Password: ");
                                newUser.setPassword(sc.nextLine());
                                System.out.println("Enter Email: ");
                                newUser.setEmail(sc.nextLine());
                                System.out.println("Enter Phone: ");
                                newUser.setPhone(sc.nextLine());
                                System.out.println("Enter Role (CUSTOMER/EMPLOYEE/ADMIN): ");
                                newUser.setRole(Role.valueOf(sc.next().toUpperCase()));
                                System.out.println("Enter Status (ACTIVE/INACTIVE/BLOCKED): ");
                                newUser.setStatus(Status.valueOf(sc.next().toUpperCase()));
                                userService.addUser(newUser, username);
                                System.out.println("User Added...");
                                break;

                            case 2:
                                System.out.println("Enter ID to Delete User:");
                                int userId = sc.nextInt();
                                try{
                                    userService.deleteById(userId, username);
                                    System.out.println("User Deleted!!");
                                } catch (
                                        ResourceNotFoundException e
                                ){
                                    System.out.println(e.getMessage());
                                }

                                break;

                            case 3:
                                List<Users> list = userService.getAllUsers();
                                list.forEach(System.out::println);
                                break;

                            case 4:
                                System.out.println("Enter User ID:");
                                int id = sc.nextInt();
                                try{
                                    Users user = userService.getById(id);
                                    System.out.println(user);
                                } catch (ResourceNotFoundException e){
                                    System.out.println(e.getMessage());
                                }
                                break;
                        }
                    }

                    break;

                case "EMPLOYEE":
                    System.out.println("Employee Menu");
                    break;

                case "ADMIN":
                    System.out.println("Admin Menu");
                    break;

                default:
                    break;
            }

        } catch (NoResultException e){

            System.out.println(
                    "Invalid Credentials"
            );
        }

        sc.close();
        session.close();
    }
}