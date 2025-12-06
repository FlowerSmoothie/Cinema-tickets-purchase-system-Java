package dao;

import entities.Cinema;
import entities.Hall;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class HallDAO implements DAO
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
    public Hall findById(int id)
    {
        Session session = sessionFactory.openSession();
        Hall hall = session.find(Hall.class, id);
        session.close();
        return hall;
    }

    @Override
    public List<Hall> findAll()
    {
        Session session = sessionFactory.openSession();
        List<Hall> hallsList = session.createQuery("FROM Hall  WHERE visible=1").list();
        session.close();
        return hallsList;
    }

    public static int getHallCountFromCinema(int cinemaId)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Hall> halls = session.createQuery("FROM Hall WHERE cinemaID=" + cinemaId).list();
        System.out.println(halls);
        session.close();
        if(halls == null) return 0;
        return halls.size();
        //return 0;
    }

    public static List<Hall> getHallsByIDs(List<Integer> ids)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Hall> halls = new ArrayList<>();
        for(Integer id: ids)
            halls.add(session.find(Hall.class, id));
        session.close();
        return halls;
    }

    public static int addEntityID(Hall hall)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        int id = (Integer)session.save(hall);
        transaction.commit();
        session.close();
        return id;
    }

    public static List<Hall> getByCinemaID(int cinemaID)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Hall> halls = session.createQuery("FROM Hall where cinemaID=" + cinemaID).list();
        session.close();
        return halls;
    }

    public static List<Hall> getShowableByCinemaID(int cinemaID)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Hall> halls = session.createQuery("FROM Hall where visible=1 and cinemaID=" + cinemaID).list();
        session.close();
        return halls;
    }

    public static void deleteHall(Hall c)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        c.setVisible(0);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(c);
        transaction.commit();
        session.close();
    }

    public static List<Hall> findAllHalls()
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Hall> hallsList = session.createQuery("FROM Hall  WHERE visible=1").list();
        session.close();
        return hallsList;
    }

    public static Hall getHallByID(int id)
    {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Hall h = (Hall)session.createQuery("FROM Hall WHERE id=" + id).list().get(0);
        return h;
    }


}
