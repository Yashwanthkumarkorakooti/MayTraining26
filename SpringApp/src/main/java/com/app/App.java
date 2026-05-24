package com.app;

import com.app.config.AppConfig;
import com.app.dao.IncidentDao;
import com.app.dao_impl.IncidentDaoImpl;
import com.app.enums.IncidentStatus;
import com.app.enums.IncidentType;
import com.app.exceptions.ResourceNotFoundException;
import com.app.model.Incident;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
//        System.out.println(context.getBean(DataSource.class));

        IncidentDao incidentDao = context.getBean(IncidentDaoImpl.class);
        while(true){
            System.out.println("1. Add Incident");
            System.out.println("2. Delete Incident by Id");
            System.out.println("3. Update Incident");
            System.out.println("4. All incidents ");
            System.out.println("5. Get Incident by id");
            System.out.println("0. Exit");
            int op = sc.nextInt();
            if(op == 0)
                break;
            switch(op){
                case 1:
                    incidentDao.insert(new Incident(302,IncidentType.REPORT_ABUSE, "incident details", IncidentStatus.ACTIVE));
                    break;
                case 2:
                    System.out.println("Enter Id to delete incident");
                    int id = sc.nextInt();
                    try {
                        incidentDao.deleteById(id);
                    }
                    catch(ResourceNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Enter incident ID to update: ");
                    try{
                        //verified the id
                        id = sc.nextInt();
                        Incident incident = incidentDao.getById(id);
                        System.out.println("Existing Incident record");
                        System.out.println(incident);

                        // take new progress output
                        System.out.println("Enter Progress Details to edit");
                        sc.nextLine();
                        String progress = sc.nextLine();
                        incident.setProgressDetails(progress);

                        //update the incident in DB
                        incidentDao.update(id,incident);

                    }catch (EmptyResultDataAccessException e){
                        System.out.println("Invalid ID.....");
                    }
                case 4:
                    incidentDao.getAll().forEach(System.out::println);
                    break;
                case 5:
                    System.out.println("enter id to fetch record");
                    id = sc.nextInt();
                    try {
                        Incident incident = incidentDao.getById(id);
                        System.out.println(incident);
                    }
                    catch(EmptyResultDataAccessException e){
                        System.out.println("Invalid Id.....");
                    }
                    break;
            } //switch ends

        }
        sc.close();
        context.close();
    }
}
