package services;

import dao.DAO;
import dao.OrderDAO;
import entities.Order;
import entities.Ticket;

import java.util.ArrayList;
import java.util.List;

public class OrderService extends Service
{

    public OrderService() { dao = new OrderDAO(); }

    @Override
    public Order findEntity(int id) {
        return (Order)dao.findById(id);
    }

    @Override
    public List<Order> findAllEntities() { return dao.findAll(); }

    public List<Order> findAllOrdersFromCertainUser(int userID) { return OrderDAO.findAllOrdersByUserID(userID); }

    public static List<Order> findAllRecordsByOrderID(int orderID) { return OrderDAO.findRecordsByOrder(orderID); }

    public void updateTickets(int orderID, int status)
    {
        List<Order> orders = findAllRecordsByOrderID(orderID);
        TicketService t = new TicketService();
        for (Order o: orders)
            t.updateTicketStatus(o.getTicket(), status);
    }

    public Order findByID(int ID) { return OrderDAO.findByID(ID); }

    public void placeOrder(List<Order> orders)
    {
        for(Order o: orders)
            dao.add(o);
    }

}
