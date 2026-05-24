package com.app;

import com.app.config.AppConfig;
import com.app.dao.AuthDao;
import com.app.dao_impl.AuthDaoImpl;
import com.app.model.Users;
import jakarta.persistence.NoResultException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        AuthDao authDao = context.getBean(AuthDaoImpl.class);

        Scanner sc = new Scanner(System.in);
        System.out.println("-------- MaverickBank : LOGIN --------");
        System.out.println("Enter Username: ");
        String username = sc.nextLine();
        System.out.println("Enter Password: ");
        String password = sc.nextLine();

        try{
            Users users = authDao.login(username,password);
        }catch (NoResultException e){
            System.out.println("Invalid Credentials....");
        }

        sc.close();
        context.close();

    }
}
