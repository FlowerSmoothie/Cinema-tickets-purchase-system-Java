package entities;

import java.io.Serializable;

public class Seance implements EntityDB, Serializable
{

    private int ID;

    private int movieID;

    private int hallID;

    private String date;

    private String time;

    private float normal;

    private float comfort;

    private float VIP;

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
