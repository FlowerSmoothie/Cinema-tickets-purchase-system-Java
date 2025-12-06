package dao;

import entities.Seance;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

public class SeanceDAO implements DAO
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
    public Seance findById(int id)
    {
        Session session = sessionFactory.openSession();
        Seance seance = session.find(Seance.class, id);
        session.close();
        return seance;
    }

    @Override
    public List<Seance> findAll()
    {
        Session session = sessionFactory.openSession();
        List<Seance> seancesList = session.createQuery("FROM Seance ").list();
        session.close();
        return seancesList;
    }

    public static List<Seance> getSeancesByIDs(List<Integer> ints)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Seance> seances = new ArrayList<>();
        Seance s;
        for(Integer i: ints)
            seances.add((Seance)session.createQuery("FROM Seance where id=" + i).list().get(0));
        return seances;
    }

    public static List<Seance> getAllSeances()
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Seance> seancesList = session.createQuery("FROM Seance ").list();
        session.close();
        return seancesList;
    }

    public static void addSeance(Seance s)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(s);
        transaction.commit();
        session.close();
    }

    public static Seance getByID(int id)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Seance> seancesList = session.createQuery("FROM Seance where id=" + id).list();
        session.close();
        return seancesList.get(0);
    }

}
