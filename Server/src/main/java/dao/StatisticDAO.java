package dao;

import entities.Statistic;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class StatisticDAO implements DAO
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
    public Statistic findById(int id)
    {
        Session session = sessionFactory.openSession();
        Statistic statistic = session.find(Statistic.class, id);
        session.close();
        return statistic;
    }

    @Override
    public List<Statistic> findAll()
    {
        Session session = sessionFactory.openSession();
        List<Statistic> statisticsList = session.createQuery("FROM Statistic ").list();
        session.close();
        return statisticsList;
    }

    public static Statistic getForSeance(int id)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Statistic> statisticsList = session.createQuery("FROM Statistic WHERE seanceID=" + id).list();
        System.out.println(statisticsList.size());
        session.close();
        return statisticsList.get(0);
    }

}
