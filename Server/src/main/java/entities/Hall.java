package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "halls")
public class Hall implements EntityDB, Serializable
{

    @Id
    @Column(name = "ID")
    private int ID;

    @Column(name = "Number")
    private int number;

    @Column(name = "CinemaID")
    private int cinemaID;

    @Column(name = "length")
    private int length; // x

    @Column(name = "width")
    private int width; // y

    @Column(name = "Visible")
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
