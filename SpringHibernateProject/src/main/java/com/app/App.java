package com.app;

import com.app.config.AppConfig;
import com.app.dao.AuthDao;
import com.app.dao.TicketDao;
import com.app.dao_impl.AuthDaoImpl;
import com.app.dao_impl.TicketDaoImpl;
import com.app.enums.Priority;
import com.app.exception.InvalidOwnershipException;
import com.app.exception.ResourceNotFoundException;
import com.app.model.Ticket;
import com.app.model.User;
import jakarta.persistence.NoResultException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        AuthDao authDao = context.getBean(AuthDaoImpl.class);
        TicketDao ticketDao = context.getBean(TicketDao.class);

        Scanner sc = new Scanner(System.in);
        System.out.println("----- SupportFlow: LOGIN -----");
        System.out.println("Enter UserName: ");
        String username = sc.nextLine();
        System.out.println("Enter Password: ");
        String password = sc.nextLine();

        try{
            User user = authDao.login(username,password);
            switch (user.getRole().toString()){
                case "CUSTOMER":
                    System.out.println("Welcome " + username);
                    while(true){
                        System.out.println("1. Add Ticket");
                        System.out.println("2. Delete Ticket by id");
                        System.out.println("3. Fetch all Tickets");
                        System.out.println("4. Update Ticket");
                        System.out.println("0. Exit ");

                        int op = sc.nextInt();
                        if(op == 0) break;

                        switch (op){
                            case 1:
                                sc.nextLine();
                                System.out.println("Enter Subject");
                                String subject = sc.nextLine();
                                System.out.println("Enter details");
                                String details = sc.nextLine();
                                System.out.println("Enter priority");
                                String priority = sc.nextLine().toUpperCase();

                                ticketDao.save(new Ticket(subject,details, Priority.valueOf(priority)), username);                                System.out.println("Ticket added...");

                                break;
                            case 2:
                                //delete
                                break;
                            case 3:
                                System.out.println("----- All Tickets -----");
                                ticketDao.findAll(username).forEach(System.out::println);
                                break;
                            case 4:
                                System.out.println("Enter ticket id to update");
                                int id = sc.nextInt();
                                //fetch ticket and check ownership

                                try{
                                    Ticket ticket = ticketDao.getById(id,username);
                                    System.out.println("Existing ticket record");
                                    System.out.println("Enter values for update..");
                                    sc.nextLine();
                                    System.out.println("Enter subject");
                                    subject = sc.nextLine();
                                    System.out.println("Enter details");
                                    details = sc.nextLine();
                                    System.out.println("Enter priority");
                                    priority = sc.next();

                                    ticket.setSubject(subject);
                                    ticket.setDetails(details);
                                    ticket.setPriority(Priority.valueOf(priority));
                                    ticketDao.update(ticket);
                                    System.out.println("Record updated");
                                } catch (ResourceNotFoundException | InvalidOwnershipException e){
                                    System.out.println(e.getMessage());
                                }

                                break;
                            default:
                                break;
                        }
                    }
                    break;
                default:
                    break;
            }
        }catch (NoResultException e){
            System.out.println("Invalid Credentials");
        }
        sc.close();
        context.close();
    }
}
