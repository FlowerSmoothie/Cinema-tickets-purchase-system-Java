package dao;

import entities.Review;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ReviewDAO implements DAO
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
    public Review findById(int id)
    {
        Session session = sessionFactory.openSession();
        Review review = session.find(Review.class, id);
        session.close();
        return review;
    }

    @Override
    public List<Review> findAll()
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Review> reviewsList = session.createQuery("FROM Review ").list();
        transaction.commit();
        session.close();
        return reviewsList;
    }

    public static List<Review> findAllReviews()
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Review> reviewsList = session.createQuery("FROM Review ").list();
        session.close();
        return reviewsList;
    }

    public static List<Review> getReviewsForMovie(int movie)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Review> reviewsList = session.createQuery("FROM Review WHERE movieID=" + movie).list();
        return reviewsList;
    }

}
