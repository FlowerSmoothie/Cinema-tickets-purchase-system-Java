package entities;

import enums.TicketStatuses;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tickets")
public class Ticket implements EntityDB, Serializable
{

    @Id
    @Column(name = "TicketID")
    private int ticketID;

    @Column(name = "SeanceID")
    private int seanceID;

    @Column(name = "SeatID")
    private int seatID;

    @Column(name = "Status")
    private int status;

    @Column(name = "CinemaID")
    private int cinemaID;

    @Column(name = "MovieID")
    private int movieID;
    @Column(name = "TicketType")
    private int type;


    public Ticket()
    {

    }

    public Ticket(int ticketID, int seanceID, int seatID, int status, int cinemaID, int movieID, int type)
    {
        this.ticketID = ticketID;
        this.seanceID = seanceID;
        this.seatID = seatID;
        this.status = status;
        this.cinemaID = cinemaID;
        this.movieID = movieID;
        this.type = type;
    }

    public Ticket(int ticketID, int seanceID, int seatID, TicketStatuses status, int cinemaID, int movieID, int type)
    {
        this.ticketID = ticketID;
        this.seanceID = seanceID;
        this.seatID = seatID;
        setStatus(status);
        this.cinemaID = cinemaID;
        this.movieID = movieID;
        this.type = type;
    }

    public void setID(int ticketID) { this.ticketID = ticketID; }
    public int getID() { return ticketID; }

    public void setSeanceID(int seanceID) { this.seanceID = seanceID; }
    public int getSeanceID() { return seanceID; }

    public void setSeatID(int seatID) { this.seatID = seatID; }
    public int getSeatID() { return seatID; }

    public void setStatus(int status) { this.status = status; }
    public void setStatus(TicketStatuses status) { this.status = (status == TicketStatuses.AVAILABLE ? 1 : 0); }
    public int getStatus() { return status; }

    public int getCinemaID() { return cinemaID; }
    public void setCinemaID(int cinemaID) { this.cinemaID = cinemaID; }

    public int getMovieID() { return movieID; }
    public void setMovieID(int movieID) { this.movieID = movieID; }

    public int getType() { return type; }
    public void setType(int type) { this.type = type; }
}
