package services;

import dao.HallDAO;
import dao.MovieDAO;
import entities.Hall;
import entities.Movie;

import java.util.List;

public class MovieService extends Service
{

    public MovieService() { dao = new MovieDAO(); }

    @Override
    public Movie findEntity(int id) {
        return (Movie)dao.findById(id);
    }

    @Override
    public List<Movie> findAllEntities() { return MovieDAO.findAvailableMovies(); }

    public static List<Movie> findEvery() { return MovieDAO.findEvery(); }
    public static Movie findMovieEntity(int id) { return MovieDAO.findMovieEntity(id); }

    public void deleteMovie(Movie m) { MovieDAO.deleteMovie(m); }

}
