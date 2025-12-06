package factory;

import entities.Hall;
import entities.Movie;

public class ShowableHall implements ShowableOnViewList
{

    private int ID;

    private int number;

    private int cinemaID;

    private int length;

    private int width;

    private int visible;

    public ShowableHall()
    {

    }

    public ShowableHall(int number, int cinemaID, int length, int width, int visible)
    {
        this.number = number;
        this.cinemaID = cinemaID;
        this.length = length;
        this.width = width;
        this.visible = visible;
    }

    public int getID() { return ID; }
    public void setID(int id) { this.ID = id; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public int getCinemaID() { return cinemaID; }
    public void setCinemaID(int cinemaID) { this.cinemaID = cinemaID; }

    public int getLength() { return length; }
    public void setLength(int length) { this.length = length; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public String getName() { return Integer.toString(number); }

    public void convertFromEntity(Object entity)
    {
        Hall m = (Hall)entity;
        this.ID = m.getID();
        this.cinemaID = m.getCinemaID();
        this.number = m.getNumber();
        this.length = m.getLength();
        this.width = m.getLength();
    }

    public Object convertToEntity()
    {
        return new Hall(ID, number, cinemaID, length, width, visible);
    }

}
