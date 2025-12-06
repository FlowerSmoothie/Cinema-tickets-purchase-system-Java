package classes;

import enums.OrderStatuses;

import static enums.OrderStatuses.*;

public class Order
{

    private int recordID;

    private int userID;

    private int ticketID;

    private float price;

    private OrderStatuses status;

    private int orderID;

    public Order()
    {

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

    public void setStatus(OrderStatuses status) { this.status = status; }
    public void setStatus(int status)
    {
        switch(status)
        {
            case 0:
                this.status = WAITING_PAYMENT;
                break;
            case 1:
                this.status = PAYED;
                break;
            case 2:
                this.status = CANCELLED;
                break;
        }
    }
    public OrderStatuses getStatus() { return status; }

    public void setID(int orderID) { this.orderID = orderID; }
    public int getID() { return orderID; }

}
