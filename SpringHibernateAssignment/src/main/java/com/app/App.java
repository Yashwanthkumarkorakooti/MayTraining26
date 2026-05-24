package com.app;

import com.app.config.AppConfig;
import com.app.dao.AuthDao;
import com.app.dao.CustomerDao;
import com.app.dao_impl.AuthDaoImpl;
import com.app.exception.ResourceNotFoundException;
import com.app.model.Customer;
import com.app.model.Users;
import jakarta.persistence.NoResultException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        AppConfig.class
                );

        AuthDao authDao = context.getBean(AuthDaoImpl.class);
        CustomerDao customerDao = context.getBean(CustomerDao.class);

        Scanner sc = new Scanner(System.in);
        System.out.println("----- BANK MANAGEMENT LOGIN -----");
        System.out.println("Enter Username:");

        String username = sc.nextLine();
        System.out.println("Enter Password:");
        String password = sc.nextLine();
        try {
            Users user = authDao.login(username, password);

            switch (user.getRole().toString()) {
                case "CUSTOMER":
                    System.out.println("Welcome " + username);
                    while (true) {
                        System.out.println("1. View Profile");
                        System.out.println("2. Update Profile");
                        System.out.println("3. View KYC Status");
                        System.out.println("4. View Branch Details");
                        System.out.println("0. Exit");
                        int op = sc.nextInt();
                        if (op == 0)
                            break;
                        switch (op) {
                            case 1:
                                System.out.println("----- PROFILE -----");
                                System.out.println(customerDao.getByUsername(username));
                                break;

                            case 2:
                                try {
                                    Customer customer = customerDao.getByUsername(username);
                                    sc.nextLine();
                                    System.out.println("Enter Full Name:");
                                    String name = sc.nextLine();
                                    System.out.println("Enter Phone:");
                                    String phone = sc.nextLine();
                                    System.out.println("Enter Address:");
                                    String address = sc.nextLine();
                                    customer.setFull_name(name);
                                    customer.setPhone(phone);
                                    customer.setAddress(address);
                                    customerDao.update(customer);

                                    System.out.println("Profile Updated Successfully");

                                } catch (
                                        ResourceNotFoundException e
                                ) {
                                    System.out.println(e.getMessage());
                                }

                                break;
                            case 3:
                                Customer customer = customerDao.getByUsername(username);
                                System.out.println("KYC Status : " + customer.getKyc_status());
                                break;

                            case 4:
                                customer = customerDao.getByUsername(username);
                                System.out.println(customer.getBranch());
                                break;
                            default:
                                System.out.println("Invalid Option");
                        }
                    }

                    break;
                default:
                    System.out.println("Unauthorized User");
            }

        } catch (
                NoResultException e
        ) {
            System.out.println("Invalid Credentials");
        }

        sc.close();
        context.close();
    }
}