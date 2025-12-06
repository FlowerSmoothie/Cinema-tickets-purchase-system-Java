package dao;

import entities.Hall;
import entities.Order;
import entities.Seance;
import entities.Ticket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class TicketDAO implements DAO
{

    public SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    @Override
    public void add(Object obj)
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(obj);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Object obj)
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(obj);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Object obj)
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(obj);
        transaction.commit();
        session.close();
    }

    @Override
    public Ticket findById(int id)
    {
        Session session = sessionFactory.openSession();
        Ticket ticket = session.find(Ticket.class, id);
        session.close();
        return ticket;
    }

    @Override
    public List<Ticket> findAll()
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Ticket> ticketsList = session.createQuery("FROM Ticket ").list();
        transaction.commit();
        session.close();
        return ticketsList;
    }

    public static List<Ticket> findBySeance(int seance)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Ticket> tickets = session.createQuery("FROM Ticket WHERE seanceID=" + seance).list();
        session.close();
        return tickets;
    }


    public static List<Ticket> findTicketsToOrders(List<Order> orders)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Ticket> tickets = new ArrayList<>();
        for(Order o: orders)
            tickets.add((Ticket)session.createQuery("FROM Ticket WHERE ticketID=" + o.getTicket()).list().get(0));
        session.close();
        return tickets;
    }

    public static List<Ticket> findById(List<Integer> ids)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Ticket> tickets = new ArrayList<>();
        for(int i: ids)
            tickets.add((Ticket)session.createQuery("FROM Ticket WHERE ticketID=" + i).list().get(0));
        session.close();
        return tickets;
    }

    public static void addTickets(List<Ticket> tickets)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        for(Ticket t: tickets)
        {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(t);
            tx.commit();
            session.close();
        }
    }

    public static void updateTicketStatus(int ticketID, int status)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.createQuery("UPDATE Ticket set status=" + status + " WHERE ticketID=" + ticketID);
        tx.commit();
        session.close();
    }

    public static Ticket findTicketByID(int id)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Ticket t = (Ticket)session.createQuery("FROM Ticket where ticketID=" + id).list().get(0);
        session.close();
        return t;
    }

}
