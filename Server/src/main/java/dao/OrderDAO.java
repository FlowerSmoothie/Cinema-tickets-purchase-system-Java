package dao;

import entities.Order;
import entities.Ticket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements DAO
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
    public Order findById(int id)
    {
        Session session = sessionFactory.openSession();
        Order order = session.find(Order.class, id);
        session.close();
        return order;
    }

    @Override
    public List<Order> findAll()
    {
        Session session = sessionFactory.openSession();
        List<Order> ordersList = session.createQuery("FROM Order ").list();
        session.close();
        return ordersList;
    }

    public static List<Order> findRecordsByOrder(int orderID)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Order> ordersList = session.createQuery("FROM Order where orderID=" + orderID).list();
        session.close();
        return ordersList;
    }

    public static List<Order> findOrdersByID(int orderID)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Order> ordersList = session.createQuery("FROM Order where orderID=" + orderID).list();
        session.close();
        return ordersList;
    }

    public static List<Order> findAllOrdersByUserID(int userID)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Order> ordersList = session.createQuery("FROM Order where userID=" + userID).list();
        session.close();
        return ordersList;
    }

    public static Order findByID(int ID)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Order order = (Order)session.createQuery("FROM Order where id=" + ID).list().get(0);
        session.close();
        return order;
    }


}