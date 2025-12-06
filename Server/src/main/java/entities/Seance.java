package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "seances")
public class Seance implements EntityDB, Serializable
{

    @Id
    @Column(name = "ID")
    private int ID;

    @Column(name = "MovieID")
    private int movieID;

    @Column(name = "HallID")
    private int hallID;

    @Column(name = "Date")
    private String date;

    @Column(name = "Time")
    private String time;

    @Column(name = "NormalPrice")
    private float normal;

    @Column(name = "ComfortPrice")
    private float comfort;

    @Column(name = "VIPPrice")
    private float VIP;

    @Column(name = "CinemaID")
    private int cinemaID;

    public Seance()
    {

    }

    public Seance(int ID, int movieID, int hallID, String date, String time, float normal, float comfort, float VIP, int cinemaID)
    {
        this.ID = ID;
        this.movieID = movieID;
        this.hallID = hallID;
        this.date = date;
        this.time = time;
        this.normal = normal;
        this.comfort = comfort;
        this.VIP = VIP;
        this.cinemaID = cinemaID;
    }

    public void setID(int ID) { this.ID = ID; }
    public int getID() { return ID; }

    public void setMovieID(int movieID) { this.movieID = movieID; }
    public int getMovieID() { return movieID; }

    public void setHallID(int hallID) { this.hallID = hallID; }
    public int getHallID() { return hallID; }

    public void setDate(String date) { this.date = date; }
    public String getDate() { return date; }

    public void setTime(String time) { this.time = time; }
    public String getTime() { return time; }

    public void setNormal(float normal) { this.normal = normal; }
    public float getNormal() { return normal; }

    public void setComfort(float comfort) { this.comfort = comfort; }
    public float getComfort() { return comfort; }

    public void setVIP(float VIP) { this.VIP = VIP; }
    public float getVIP() { return VIP; }

    public void setCinemaID(int cinemaID) { this.cinemaID = cinemaID; }
    public int getCinemaID() { return cinemaID; }


}
