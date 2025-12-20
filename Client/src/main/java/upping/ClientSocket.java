package upping;

import classes.User;
import entities.*;
import enums.OptionsForWindows;

import java.io.*;
import java.net.*;
import java.util.List;

public class ClientSocket {
    private static final ClientSocket SINGLE_INSTANCE = new ClientSocket();

    private ClientSocket() {
        try {
            socket = new Socket("localhost", 2525);
            coos = new ObjectOutputStream(socket.getOutputStream());
            cois = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
        }
    }

    public static ClientSocket getInstance() {
        return SINGLE_INSTANCE;
    }

    private static Socket socket;
    private ObjectInputStream cois;
    private ObjectOutputStream coos;

    private User user;
    private List<Order> orderList;

    private int currentID;

    private Cinema cinema;
    private Hall hall;
    private Movie movie;
    private Seance seance;
    private Review review;
    private Order order;
    private entities.User userToChange;

    private OptionsForWindows option;

    public Socket getSocket() {
        return socket;
    }


    public ObjectInputStream getInStream() {
        return cois;
    }
    public ObjectOutputStream getOutStream() { return coos; }

    public void setOutStream(ObjectOutputStream out) { this.coos = out; }
    public void setInStream(ObjectInputStream in) {
        this.cois = in;
    }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<Order> getOrderList() { return orderList; }
    public void setOrderList(List<Order> orderList) { this.orderList = orderList; }

    public OptionsForWindows getOption() { return option; }
    public void setOption(OptionsForWindows option) { this.option = option; }

    public void setCurrentID(int id) { this.currentID = id; }
    public int getCurrentID() { return currentID; }

    public void setCinema(Cinema cinema) { this.cinema = cinema; }
    public Cinema getCinema() { return cinema; }

    public void setHall(Hall hall) { this.hall = hall; }
    public Hall getHall() { return hall; }

    public void setMovie(Movie movie) { this.movie = movie; }
    public Movie getMovie() { return movie; }

    public void setSeance(Seance seance) { this.seance = seance; }
    public Seance getSeance() { return seance; }
    public void setReview(Review review) { this.review = review; }
    public Review getReview() { return review; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public entities.User getUserToChange() { return userToChange; }

    public void setUserToChange(entities.User userToChange) { this.userToChange = userToChange; }
}
