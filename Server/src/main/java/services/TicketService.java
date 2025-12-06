package services;

import dao.TicketDAO;
import entities.Order;
import entities.Seance;
import entities.Ticket;

import java.util.List;

public class TicketService extends Service
{

    public TicketService() { dao = new TicketDAO(); }

    @Override
    public Ticket findEntity(int id) {
        return (Ticket)dao.findById(id);
    }

    @Override
    public List<Ticket> findAllEntities() {
        return dao.findAll();
    }

    public List<Ticket> findTicketsToSeance(int seanceID) { return TicketDAO.findBySeance(seanceID);  }

    public List<Ticket> findTicketsToOrders(List<Order> orders) { return TicketDAO.findTicketsToOrders(orders); }

    public List<Ticket> findTicketsByID(List<Integer> ids) { return TicketDAO.findById(ids); }

    public Ticket findTicketByID(int id) { return TicketDAO.findTicketByID(id); }

    public void addTickets(List<Ticket> tickets) { TicketDAO.addTickets(tickets); }

    public List<Ticket> findAllForSeance(int id) { return TicketDAO.findBySeance(id); }

    public void updateTicketStatus(int ticketID, int status) { TicketDAO.updateTicketStatus(ticketID, status); }

}
