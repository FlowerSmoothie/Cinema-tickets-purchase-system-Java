package dao;

import entities.Cinema;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class CinemaDAO implements DAO
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
    public Cinema findById(int id)
    {
        Session session = sessionFactory.openSession();
        Cinema cinema = session.find(Cinema.class, id);
        session.close();
        return cinema;
    }

    @Override
    public List<Cinema> findAll()
    {
        Session session = sessionFactory.openSession();
        List<Cinema> cinemasList = session.createQuery("FROM Cinema WHERE visible=1").list();
        session.close();
        return cinemasList;
    }

    public static List<Cinema> findAllCinemaEntities()
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Cinema> cinemasList = session.createQuery("FROM Cinema WHERE visible=1").list();
        session.close();
        return cinemasList;
    }

    public static void deleteCinema(Cinema c)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        c.setVisible(0);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(c);
        transaction.commit();
        session.close();
    }

    public static Cinema findCinema(int id)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Cinema> cinemasList = session.createQuery("FROM Cinema WHERE ID=" + id).list();
        session.close();
        return cinemasList.get(0);
    }

}
