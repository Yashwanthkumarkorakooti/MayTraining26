package com.app;

import com.app.config.AppConfig;
import com.app.dao.UserDAO;
import com.app.dao_impl.UserDAOImpl;
import com.app.enums.Role;
import com.app.enums.Status;
import com.app.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserDAO userDAO =
                context.getBean(UserDAOImpl.class);

        while (true) {

            System.out.println("1. Add User");
            System.out.println("2. Delete User");
            System.out.println("3. Update User");
            System.out.println("4. Get All Users");
            System.out.println("5. Get User By ID");
            System.out.println("0. Exit");

            int op = sc.nextInt();
            if (op == 0) break;
            switch (op) {
                case 1:
                    try {
                        User user = new User();
                        sc.nextLine();
                        System.out.print("Username: ");
                        user.setUsername(sc.nextLine());

                        System.out.print("Password: ");
                        user.setPassword(sc.nextLine());

                        System.out.print("Email: ");
                        user.setEmail(sc.nextLine());

                        System.out.print("Phone: ");
                        user.setPhone(sc.nextLine());

                        System.out.print("Role (CUSTOMER/EMPLOYEE/ADMIN): ");
                        user.setRole(Role.valueOf(sc.next().toUpperCase()));

                        System.out.print("Status (ACTIVE/INACTIVE/BLOCKED): ");
                        user.setStatus(Status.valueOf(sc.next().toUpperCase()));

                        sc.nextLine();

                        System.out.print("Last Login (yyyy-mm-dd hh:mm:ss): ");
                        user.setLastLogin(sc.nextLine());

                        userDAO.insert(user);

                        System.out.println("User Added Successfully!");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        System.out.print("Enter User ID to Delete: ");
                        int deleteId = sc.nextInt();
                        userDAO.deleteUserById(deleteId);
                        System.out.println("User Deleted Successfully!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.print("Enter User ID to Update: ");
                        int updateId = sc.nextInt();
                        sc.nextLine();
                        User user = new User();

                        System.out.print("Enter Username: ");
                        user.setUsername(sc.nextLine());

                        System.out.print("Enter Password: ");
                        user.setPassword(sc.nextLine());

                        System.out.print("Enter Email: ");
                        user.setEmail(sc.nextLine());

                        System.out.print("Enter Phone: ");
                        user.setPhone(sc.nextLine());

                        System.out.print("Enter Role (CUSTOMER/EMPLOYEE/ADMIN): ");
                        user.setRole(Role.valueOf(sc.next().toUpperCase()));

                        System.out.print("Enter Status (ACTIVE/INACTIVE/BLOCKED): ");
                        user.setStatus(Status.valueOf(sc.next().toUpperCase()));

                        sc.nextLine();

                        System.out.print("Enter Last Login (yyyy-mm-dd hh:mm:ss): ");
                        user.setLastLogin(sc.nextLine());

                        userDAO.updateUserById(updateId, user);

                        System.out.println("User Updated Successfully!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;
                case 4:
                    userDAO.getAll().forEach(System.out::println);
                    break;
                case 5:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    System.out.println(userDAO.getById(id));
                    break;
                default:
                    System.out.println("Enter Valid Option");
            }
        }

        sc.close();
        context.close();
    }
}