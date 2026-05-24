package com.app.dao_impl;

import com.app.dao.TicketDao;
import com.app.enums.Status;
import com.app.exception.InvalidOwnershipException;
import com.app.exception.ResourceNotFoundException;
import com.app.model.Customer;
import com.app.model.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class TicketDaoImpl implements TicketDao {
    @PersistenceContext
    private EntityManager entityManager;

    private CustomerDaoImpl customerDao;

    @Autowired
    public void setCustomerDao(CustomerDaoImpl customerDao) {
        this.customerDao = customerDao;
    }



    @Override
    public List<Ticket> findAll(String cutomerUsername) {
        TypedQuery<Ticket> query = entityManager.createQuery(
                "select t from Ticket t where t.customer.user.username=:username",
                Ticket.class);
        query.setParameter("username",cutomerUsername);
        return query.getResultList();
    }

    @Override
    public void save(Ticket ticket, String customerUsername) {
        Customer customer = customerDao.getByUsernmae(customerUsername);
        // attach customer to ticket
        ticket.setCustomer(customer);
        //set ticket status
        ticket.setStatus(Status.OPEN);

        entityManager.persist(ticket);
    }

    @Override
    public Ticket getById(int id, String customerUsername) {
        Ticket ticket = entityManager.find(Ticket.class,id);
        if(ticket == null)
            throw new ResourceNotFoundException("Invalid id given..");
        //ownership check
        if(!(ticket.getCustomer().getUser().getUsername().equals(customerUsername))){
            throw new InvalidOwnershipException("You do not own this ticket");
        }
        return ticket;
    }

    @Override
    public void update(Ticket ticket) {
        entityManager.merge(ticket);
    }
}
