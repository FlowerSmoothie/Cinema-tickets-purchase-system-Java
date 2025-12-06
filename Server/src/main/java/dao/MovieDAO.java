package dao;

import entities.Hall;
import entities.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class MovieDAO implements DAO
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
    public Movie findById(int id)
    {
        Session session = sessionFactory.openSession();
        Movie movie = session.find(Movie.class, id);
        session.close();
        return movie;
    }

    @Override
    public List<Movie> findAll()
    {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Movie> moviesList = session.createQuery("FROM Movie  WHERE visible=1").list();
        transaction.commit();
        session.close();
        return moviesList;
    }


    public static List<Movie> findAvailableMovies()
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Movie> moviesList = session.createQuery("FROM Movie  WHERE visible=1").list();
        transaction.commit();
        session.close();
        return moviesList;
    }

    public static List<Movie> findEvery()
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Movie> moviesList = session.createQuery("FROM Movie ").list();
        session.close();
        return moviesList;
    }

    public static void deleteMovie(Movie c)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        c.setVisible(0);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(c);
        transaction.commit();
        session.close();
    }

    public static Movie findMovieEntity(int id)
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Movie> moviesList = session.createQuery("FROM Movie where id=" + id).list();
        session.close();
        return moviesList.get(0);
    }

}
