package entities;

import enums.SeatTypes;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "seats")
public class Seat implements EntityDB, Serializable
{

    @Id
    @Column(name = "SeatID")
    private int seatID;

    @Column(name = "HallID")
    private int hallID;

    @Column(name = "X")
    private int X;

    @Column(name = "Y")
    private int Y;

    @Column(name = "Type")
    private int type;

    public Seat()
    {

    }

    public Seat(int seatID, int hallID, int X, int Y, int type)
    {
        this.seatID = seatID;
        this.hallID = hallID;
        this.X = X;
        this.Y = Y;
        this.type = type;
    }

    public Seat(int seatID, int hallID, int X, int Y, SeatTypes type)
    {
        this.seatID = seatID;
        this.hallID = hallID;
        this.X = X;
        this.Y = Y;
        setType(type);
    }

    public void setID(int seatID) { this.seatID = seatID; }
    public int getID() { return seatID; }

    public void setHallID(int hallID) { this.hallID = hallID; }
    public int getHallID() { return hallID; }

    public void setX(int X) { this.X = X; }
    public int getX() { return X; }

    public void setY(int Y) { this.Y = Y; }
    public int getY() { return Y; }

    public int getType() { return type; }
    public void setType(int type) { this.type = type; }
    public void setType(SeatTypes type)
    {
        switch(type)
        {
            case STANDART:
                this.type = 0;
                break;
            case COMFORT:
                this.type = 1;
                break;
            case VIP:
                this.type = 2;
                break;
        }
    }

}
