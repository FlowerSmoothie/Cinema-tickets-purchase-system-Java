package dao;

import entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class UserDAO implements DAO
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
    public User findById(int id)
    {
        Session session = sessionFactory.openSession();
        User user = session.find(User.class, id);
        session.close();
        return user;
    }

    @Override
    public List<User> findAll()
    {
        Session session = sessionFactory.openSession();
        List<User> usersList = session.createQuery("FROM User ").list();
        session.close();
        return usersList;
    }

    public static List<User> findAllUserEntities()
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<User> usersList = session.createQuery("FROM User ").list();
        session.close();
        return usersList;
    }

}
