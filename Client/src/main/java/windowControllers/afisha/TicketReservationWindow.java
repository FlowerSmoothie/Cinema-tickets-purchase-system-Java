package windowControllers.afisha;

import com.example.client.HelloApplication;
import entities.*;
import enums.OptionsForWindows;
import enums.OrderStatuses;
import enums.Requests;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import stuff.Constants;
import upping.ClientRequest;
import upping.ClientSocket;
import upping.ServerResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketReservationWindow {

    @FXML
    private Button backButton;

    @FXML
    private Label hallNumberLabel;

    @FXML
    private TextArea seatsArea;

    @FXML
    private Label standartPriceLabel;
    @FXML
    private Label comfortPriceLabel;
    @FXML
    private Label VIPPriceLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Button reserveButton;
    @FXML
    private Button unreserveButton;

    @FXML
    private TextField YText;
    @FXML
    private TextField XText;

    @FXML
    private Button movieButton;
    @FXML
    private Button cinemaButton;

    @FXML
    private Label yourPriceLabel;
    @FXML
    private Button placeOrderButton;


    private List<Ticket> tickets = new ArrayList<>();
    private int x;
    private int y;

    private float standart;
    private float comfort;
    private float VIP;

    private float yourPrice = 0;

    private List<Ticket> cart = new ArrayList<>();


    private void redrawArea()
    {
        String str = "";
        Ticket t;

        for(int i = 0; i < y; i++)
        {
            for(int j = 0; j < x; j++)
            {
                t = tickets.get(y * i + j);
                switch (t.getStatus())
                {
                    case 0:
                        str += "X"; break;
                    case 1:
                        switch (t.getType())
                        {
                            case 0 -> str += "S";
                            case 1 -> str += "C";
                            case 2 -> str += "V";
                        }
                        break;
                    case 2:
                        str += "O";
                }
                str += " ";
            }
            str += "\n";
        }
        seatsArea.setText(str);
    }


    @FXML
    private void initialize()
    {
        try
        {
            Seance s = ClientSocket.getInstance().getSeance();

            ClientRequest request = new ClientRequest();
            request.setRequest(Requests.SHOW_TICKETS_FOR_SEANCE);
            request.setObject(s.getID());
            ClientSocket.getInstance().getOutStream().writeObject(request);

            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            tickets = (List<Ticket>)response.getResponseData();

            request = new ClientRequest();
            request.setRequest(Requests.SHOW_HALL);
            request.setObject(s.getHallID());
            ClientSocket.getInstance().getOutStream().writeObject(request);

            response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            Hall h = (Hall)response.getResponseData();

            x = h.getLength();
            y = h.getWidth();

            redrawArea();

            hallNumberLabel.setText(Integer.toString(h.getNumber()));

            standart = s.getNormal();
            comfort = s.getComfort();
            VIP = s.getVIP();

            standartPriceLabel.setText(Float.toString(standart));
            comfortPriceLabel.setText(Float.toString(comfort));
            VIPPriceLabel.setText(Float.toString(VIP));

            yourPriceLabel.setText("0");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    public void onBackButtonClick() throws IOException
    {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("MovieShowsPoster.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onMovieButtonClick() throws IOException
    {
        ClientSocket.getInstance().setOption(OptionsForWindows.TICKET_RESERVATION);
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowInfoAboutMovie.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onCinemaButtonClick() throws IOException
    {
        ClientSocket.getInstance().setOption(OptionsForWindows.TICKET_RESERVATION);
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowInfoAboutCinema.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }


    public void onUnreserveButtonClick() throws IOException
    {
        int xCoord;
        int yCoord;

        try
        {
            yCoord = Integer.parseInt(YText.getText());
            xCoord = Integer.parseInt(XText.getText());

            if(xCoord > x || yCoord > y) return;

            yCoord--;
            xCoord--;


            Ticket t = tickets.get(yCoord * y + xCoord);
            if(t.getStatus() == 2)
            {
                tickets.get(yCoord * y + xCoord).setStatus(1);
                cart.add(t);
                redrawArea();

                switch(t.getType())
                {
                    case 0 -> yourPrice -= standart;
                    case 1 -> yourPrice -= comfort;
                    case 2 -> yourPrice -= VIP;
                }
                yourPriceLabel.setText(Float.toString(yourPrice));
                redrawArea();
            }
            else
                messageLabel.setText(Constants.checkEnteredData);

        }
        catch(NumberFormatException e)
        {
            messageLabel.setText(Constants.shouldBeNumbersInFields);
        }
    }

    public void onReserveButtonClick() throws IOException
    {
        int xCoord;
        int yCoord;


        try
        {
            yCoord = Integer.parseInt(YText.getText());
            xCoord = Integer.parseInt(XText.getText());

            if(xCoord > x || yCoord > y) return;

            yCoord--;
            xCoord--;


            Ticket t = tickets.get(yCoord * y + xCoord);
            if(t.getStatus() == 1)
            {
                tickets.get(yCoord * y + xCoord).setStatus(2);
                cart.add(t);
                redrawArea();

                switch(t.getType())
                {
                    case 0 -> yourPrice += standart;
                    case 1 -> yourPrice += comfort;
                    case 2 -> yourPrice += VIP;
                }
                yourPriceLabel.setText(Float.toString(yourPrice));
                redrawArea();
            }
            else
                messageLabel.setText(Constants.cannotReserveTicket);

        }
        catch(NumberFormatException e)
        {
            messageLabel.setText(Constants.shouldBeNumbersInFields);
        }
    }

    public void onPlaceOrderButtonClick() throws IOException
    {
        try
        {

            List<Order> orders = new ArrayList<>();
            int userID = ClientSocket.getInstance().getUser().getID();

            ClientRequest request = new ClientRequest();
            request.setRequest(Requests.VIEW_ORDER_COUNT);
            request.setObject(userID);
            ClientSocket.getInstance().getOutStream().writeObject(request);

            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            int orderID = (Integer)response.getResponseData();

            orderID = userID * 10 + orderID;

            for(int i = 0; i < cart.size(); i++)
            {
                Order o = new Order();
                o.setStatus(OrderStatuses.WAITING_PAYMENT);
                o.setUserID(userID);
                o.setTicket(cart.get(i).getID());
                o.setID(orderID);
                switch (cart.get(i).getType())
                {
                    case 0 -> o.setPrice(standart);
                    case 1 -> o.setPrice(comfort);
                    case 2 -> o.setPrice(VIP);
                }
                orders.add(o);
            }

            request = new ClientRequest();
            request.setRequest(Requests.ADD_ORDER);
            request.setObject(orders);
            ClientSocket.getInstance().getOutStream().writeObject(request);

            response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            messageLabel.setText(response.getResponseMessage());

            onBackButtonClick();

        }
        catch (Exception e)
        {
            messageLabel.setText(Constants.errorOnServer);
        }
    }

}
