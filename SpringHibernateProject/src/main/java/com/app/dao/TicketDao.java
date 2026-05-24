package com.app.dao;

import com.app.model.Ticket;

import java.util.List;

public interface TicketDao {
    List<Ticket> findAll(String customerUsername);
    void save(Ticket ticket,String customerUsername);
    Ticket getById(int id, String customerUsername);
    void update(Ticket ticket);
}
