package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "statistics")
public class Statistic implements EntityDB, Serializable
{

    @Id
    @Column(name = "ID")
    private int ID;

    @Column(name = "SeanceID")
    private int seanceID;

    @Column(name = "MovieID")
    private int movieID;

    @Column(name = "standartTicketsTotal")
    private int standartTicketsTotal;
    @Column(name = "standartTicketsSold")
    private int standartTicketsSold;
    @Column(name = "standartTicketsPrice")
    private float standartTicketsPrice;

    @Column(name = "comfortTicketsTotal")
    private int comfortTicketsTotal;
    @Column(name = "comfortTicketsSold")
    private int comfortTicketsSold;
    @Column(name = "comfortTicketsPrice")
    private float comfortTicketsPrice;

    @Column(name = "VIPTicketsTotal")
    private int VIPTicketsTotal;
    @Column(name = "VIPTicketsSold")
    private int VIPTicketsSold;
    @Column(name = "VIPTicketsPrice")
    private float VIPTicketsPrice;

    public Statistic()
    {

    }

    public Statistic(int ID, int seanceID, int movieID, int standartTicketsTotal, int standartTicketsSold, float standartTicketsPrice, int comfortTicketsTotal, int comfortTicketsSold, float comfortTicketsPrice, int VIPTicketsTotal, int VIPTicketsSold, float VIPTicketsPrice)
    {
        this.ID = ID;
        this.seanceID = seanceID;
        this.movieID = movieID;
        this.standartTicketsTotal = standartTicketsTotal;
        this.standartTicketsSold = standartTicketsSold;
        this.standartTicketsPrice = standartTicketsPrice;
        this.comfortTicketsTotal = standartTicketsTotal;
        this.comfortTicketsSold = standartTicketsSold;
        this.comfortTicketsPrice = standartTicketsPrice;
        this.VIPTicketsTotal = standartTicketsTotal;
        this.VIPTicketsSold = standartTicketsSold;
        this.VIPTicketsPrice = standartTicketsPrice;
    }

    public void setID(int ID) { this.ID = ID; }
    public int getID() { return ID; }

    public void setSeanceID(int seanceID) { this.seanceID = seanceID; }
    public int getSeanceID() { return seanceID; }

    public void setMovieID(int movieID) { this.movieID = movieID; }
    public int getMovieID() { return movieID; }

    public int getStandartTicketsTotal() {
        return standartTicketsTotal;
    }

    public void setStandartTicketsTotal(int standartTicketsTotal) {
        this.standartTicketsTotal = standartTicketsTotal;
    }

    public int getStandartTicketsSold() {
        return standartTicketsSold;
    }

    public void setStandartTicketsSold(int standartTicketsSold) {
        this.standartTicketsSold = standartTicketsSold;
    }

    public float getStandartTicketsPrice() {
        return standartTicketsPrice;
    }

    public void setStandartTicketsPrice(float standartTicketsPrice) {
        this.standartTicketsPrice = standartTicketsPrice;
    }

    public int getComfortTicketsTotal() {
        return comfortTicketsTotal;
    }

    public void setComfortTicketsTotal(int comfortTicketsTotal) {
        this.comfortTicketsTotal = comfortTicketsTotal;
    }

    public int getComfortTicketsSold() {
        return comfortTicketsSold;
    }

    public void setComfortTicketsSold(int comfortTicketsSold) {
        this.comfortTicketsSold = comfortTicketsSold;
    }

    public float getComfortTicketsPrice() {
        return comfortTicketsPrice;
    }

    public void setComfortTicketsPrice(float comfortTicketsPrice) {
        this.comfortTicketsPrice = comfortTicketsPrice;
    }

    public int getVIPTicketsTotal() {
        return VIPTicketsTotal;
    }

    public void setVIPTicketsTotal(int VIPTicketsTotal) {
        this.VIPTicketsTotal = VIPTicketsTotal;
    }

    public int getVIPTicketsSold() {
        return VIPTicketsSold;
    }

    public void setVIPTicketsSold(int VIPTicketsSold) {
        this.VIPTicketsSold = VIPTicketsSold;
    }

    public float getVIPTicketsPrice() {
        return VIPTicketsPrice;
    }

    public void setVIPTicketsPrice(float VIPTicketsPrice) {
        this.VIPTicketsPrice = VIPTicketsPrice;
    }
}
