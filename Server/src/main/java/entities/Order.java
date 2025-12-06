package entities;

import enums.OrderStatuses;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "orders")
public class Order implements EntityDB, Serializable
{

    @Id
    @Column(name = "RecordID")
    private int recordID;

    @Column(name = "UserID")
    private int userID;

    @Column(name = "Ticket")
    private int ticketID;

    @Column(name = "Price")
    private float price;

    @Column(name = "Status")
    private int status;

    @Column(name = "OrderID")
    private int orderID;


    public Order()
    {

    }

    public Order(int recordID, int userID, int ticket, float price, int status, int orderID)
    {
        this.recordID = recordID;
        this.userID = userID;
        this.ticketID = ticket;
        this.price = price;
        this.status = status;
        this.orderID = orderID;
    }

    public Order(int recordID, int userID, int ticket, float price, OrderStatuses status, int orderID)
    {
        this.recordID = recordID;
        this.userID = userID;
        this.ticketID = ticket;
        this.price = price;
        setStatus(status);
        this.orderID = orderID;
    }

    public void setRecordID(int recordID) { this.recordID = recordID; }
    public int getRecordID() { return recordID; }

    public void setUserID(int userID) { this.userID = userID; }
    public int getUserID() { return userID; }

    public void setTicket(int ticketID) { this.ticketID = ticketID; }
    public int getTicket() { return ticketID; }

    public void setPrice(float price) { this.price = price; }
    public float getPrice() { return price; }

    public void setStatus(int status) { this.status = status; }
    public void setStatus(OrderStatuses status)
    {
        switch(status)
        {
            case WAITING_PAYMENT:
                this.status = 0;
                break;
            case PAYED:
                this.status = 1;
                break;
            case CANCELLED:
                this.status = 2;
                break;
        }
    }
    public int getStatus() { return status; }

    public void setID(int orderID) { this.orderID = orderID; }
    public int getID() { return orderID; }


}
