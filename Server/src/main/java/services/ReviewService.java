package services;

import dao.ReviewDAO;
import entities.Review;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ReviewService extends Service
{

    public ReviewService() { dao = new ReviewDAO(); }

    @Override
    public Review findEntity(int id) {
        return (Review)dao.findById(id);
    }

    @Override
    public List<Review> findAllEntities() {
        return ReviewDAO.findAllReviews();
    }

    public float countMovieRatingByReviews(int movie)
    {

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Review> reviews = ReviewDAO.getReviewsForMovie(movie);
        session.close();
        float rating = 0;
        for(Review review: reviews) rating += review.getStars();
        rating /= reviews.size();
        return rating;
    }

}
