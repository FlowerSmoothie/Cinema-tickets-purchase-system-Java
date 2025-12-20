package windowCoolTables;

import entities.Cinema;
import entities.Movie;
import entities.Seance;
import enums.OrderStatuses;

public class OrderTable
{

    private int orderID;
    private int status;
    private String statusStr;
    private Cinema cinema;
    private String cinemaStr;
    private Movie movie;
    private String movieStr;
    private Seance seance;
    private String seanceStr;
    private String date;
    private String time;

    public int getOrderID() { return orderID; }
    public void setOrderID(int orderID) { this.orderID = orderID; }

    public String getStatusStr() { return statusStr; }
    public void setStatusStr(String statusStr) { this.statusStr = statusStr; }

    public String getCinemaStr() { return cinemaStr; }
    public void setCinemaStr(String cinemaStr) { this.cinemaStr = cinemaStr; }

    public String getMovieStr() { return movieStr; }

    public void setMovieStr(String movieStr) { this.movieStr = movieStr; }

    public String getSeanceStr() { return seanceStr; }
    public void setSeanceStr(String seanceStr) { this.seanceStr = seanceStr; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public Cinema getCinema() { return cinema; }
    public void setCinema(Cinema cinema) { this.cinema = cinema; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    public Seance getSeance() { return seance; }
    public void setSeance(Seance seance) { this.seance = seance; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}
