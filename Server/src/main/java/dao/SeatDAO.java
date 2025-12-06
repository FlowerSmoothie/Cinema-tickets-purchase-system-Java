package dao;

import entities.Hall;
import entities.Order;
import entities.Seat;
import entities.Ticket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class SeatDAO implements DAO
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
    public Seat findById(int id)
    {
        Session session = sessionFactory.openSession();
        Seat seat = session.find(Seat.class, id);
        session.close();
        return seat;
    }

    @Override
    public List<Seat> findAll()
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Seat> seatsList = session.createQuery("FROM Seat ").list();
        transaction.commit();
        session.close();
        return seatsList;
    }

    public static List<Seat> getSeatsFromHall(int id)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Seat> seats = session.createQuery("FROM Seat where hallID=" + id).list();
        session.close();
        return seats;
    }

    public static void updateSeatsList(List<Seat> seats)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        for(Seat s: seats)
        {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(s);
            transaction.commit();
        }
        session.close();
    }

    public static List<Seat> findSeatcByTickets(List<Ticket> tickets)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Seat> seats = new ArrayList<>();
        for(Ticket t: tickets)
            seats.add(session.find(Seat.class, t.getSeatID()));
        session.close();
        return seats;
    }

    public static void addSeats(List<Seat> seats)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        for(Seat t: seats)
        {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(t);
            tx.commit();
            session.close();
        }
    }

}
