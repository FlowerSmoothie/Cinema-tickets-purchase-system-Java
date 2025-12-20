package entities;

import java.io.Serializable;

public class Hall implements EntityDB, Serializable
{
    private int ID;

    private int number;

    private int cinemaID;

    private int length;

    private int width;

    private int visible;

    public Hall()
    {

    }

    public Hall(int ID, int number, int cinemaID, int length, int width, int visible)
    {
        this.ID = ID;
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

    public int getVisible() { return visible; }
    public void setVisible(int visible) { this.visible = visible; }

}
