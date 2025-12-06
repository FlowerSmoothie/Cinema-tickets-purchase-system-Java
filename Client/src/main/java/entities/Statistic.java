package entities;

import java.io.Serializable;

public class Statistic implements EntityDB, Serializable
{

    private int ID;

    private int seanceID;

    private int movieID;

    private int standartTicketsTotal;
    private int standartTicketsSold;
    private float standartTicketsPrice;

    private int comfortTicketsTotal;
    private int comfortTicketsSold;
    private float comfortTicketsPrice;

    private int VIPTicketsTotal;
    private int VIPTicketsSold;
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
