package windowControllers.workWithProfile;

import classes.CheckDocument;
import com.example.client.HelloApplication;
import entities.*;
import enums.OrderStatuses;
import enums.Requests;
import enums.Responses;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import upping.ClientRequest;
import upping.ClientSocket;
import upping.ServerResponse;
import windowCoolTables.AfishaEntity;
import windowCoolTables.HallCinemaTableEntity;
import windowCoolTables.SeatsTableEntity;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static enums.Responses.OKAY;

public class OrderInfo {

    @FXML
    private TableView<SeatsTableEntity> seatsTable;

    @FXML
    private TableColumn<SeatsTableEntity, Integer> columnX;
    @FXML
    private TableColumn<SeatsTableEntity, Integer> columnY;


    @FXML
    private Button backButton;
    @FXML
    private Button getCheckButton;
    @FXML
    private Button payButton;
    @FXML
    private Button cancelButton;


    @FXML
    private Label statusLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label movieNameLabel;
    @FXML
    private Label cinemaNameLabel;
    @FXML
    private Label orderNumberLabel;

    @FXML
    private Label errLabel;

    private int orderID;
    private List<Order> orders;
    private List<Seat> seats;

    private List<SeatsTableEntity> seatsForTable;
    private ObservableList<SeatsTableEntity> seatsObs;

    private classes.Order o = new classes.Order();

    private float price = 0;

    private OrderStatuses status;


    @FXML
    private void redrawStatus()
    {
        switch(o.getStatus())
        {
            case PAYED:
                statusLabel.setText("оплачен");
                cancelButton.setDisable(true);
                payButton.setDisable(true);
                break;
            case WAITING_PAYMENT:
                statusLabel.setText("не оплачен");
                break;
            case CANCELLED:
                statusLabel.setText("отменён");
                cancelButton.setDisable(true);
                payButton.setDisable(true);
                break;
        }

    }

    private void checkDate()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime seanceDT = LocalDateTime.parse((ClientSocket.getInstance().getSeance().getDate() + " " + ClientSocket.getInstance().getSeance().getTime()), formatter);
        if(LocalDateTime.now().isAfter(seanceDT))
        {
            cancelButton.setDisable(true);
            payButton.setDisable(true);
        }
    }


    @FXML
    private void initialize()
    {
        try
        {

            // setting up table columns
            {
                columnX.setCellValueFactory(new PropertyValueFactory<SeatsTableEntity, Integer>("X"));
                columnY.setCellValueFactory(new PropertyValueFactory<SeatsTableEntity, Integer>("Y"));
            }

            // getting arrays of needed items to work with order
            {

                orderID = ClientSocket.getInstance().getCurrentID();
                orderNumberLabel.setText(Integer.toString(orderID));

                ClientRequest request = new ClientRequest();
                request.setRequest(Requests.VIEW_RECORDS_FOR_ORDERS);
                request.setObject(orderID);
                ClientSocket.getInstance().getOutStream().writeObject(request);

                ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
                orders = (List<Order>)response.getResponseData();
                seatsForTable = new ArrayList<SeatsTableEntity>();


                request = new ClientRequest();
                request.setRequest(Requests.GET_SEATS_FROM_ORDER);
                request.setObject(orders);
                ClientSocket.getInstance().getOutStream().writeObject(request);

                response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
                seats = (List<Seat>)response.getResponseData();

                for(int i = 0; i < orders.size(); i++)
                {
                    SeatsTableEntity ste = new SeatsTableEntity();
                    price += orders.get(i).getPrice();
                    ste.setX(seats.get(i).getX());
                    ste.setY(seats.get(i).getY());
                    seatsForTable.add(ste);
                }

                seatsObs = FXCollections.observableArrayList(seatsForTable);
                seatsTable.setItems(seatsObs);

                o.setID(orders.get(0).getID());
                o.setStatus(orders.get(0).getStatus());

                cinemaNameLabel.setText(ClientSocket.getInstance().getCinema().getName());
                movieNameLabel.setText(ClientSocket.getInstance().getMovie().getName());
                dateLabel.setText(ClientSocket.getInstance().getSeance().getDate());
                timeLabel.setText(ClientSocket.getInstance().getSeance().getTime());
                priceLabel.setText(Float.toString(price));

                switch (o.getStatus())
                {
                    case PAYED -> { payButton.setDisable(true); cancelButton.setDisable(true); getCheckButton.setDisable(false); }
                    case CANCELLED -> { payButton.setDisable(true); cancelButton.setDisable(true); getCheckButton.setDisable(true); }
                    case WAITING_PAYMENT -> { payButton.setDisable(false); cancelButton.setDisable(false); getCheckButton.setDisable(true); }
                }


                redrawStatus();
                checkDate();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void onBackButtonClick() throws IOException
    {
        ClientSocket.getInstance().setOrderList(orders);
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowOrderList.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onPayButtonClick() throws IOException
    {
        ClientRequest request = new ClientRequest();
        request.setRequest(Requests.PAY_ORDER);
        request.setObject(orderID);
        ClientSocket.getInstance().getOutStream().writeObject(request);
        try
        {
            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            if(response.getResponseStatus() == OKAY)
            {
                o.setStatus(OrderStatuses.PAYED);
                redrawStatus();
                getCheckButton.setDisable(false);
                cancelButton.setDisable(true);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void onCancelButtonClick() throws IOException
    {
        ClientRequest request = new ClientRequest();
        request.setRequest(Requests.CANCEL_ORDER);
        request.setObject(orderID);
        ClientSocket.getInstance().getOutStream().writeObject(request);
        try
        {
            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            if(response.getResponseStatus() == OKAY)
            {
                o.setStatus(OrderStatuses.CANCELLED);

                redrawStatus();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void onCheckButtonClick() throws IOException
    {
        try {
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            //String date = LocalDateTime.now().format(formatter);

            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy-hhmmss");

            PrintWriter writer = new PrintWriter("Check " + sdf.format(new Date()) + ".txt");
            writer.println(CheckDocument.getCheck(o.getID(), cinemaNameLabel.getText(), ClientSocket.getInstance().getCinema().getAdress(), movieNameLabel.getText(), dateLabel.getText(), timeLabel.getText()));
            writer.close();

        } catch (IOException e) {
            errLabel.setText("Произошла ошибка!");
            e.printStackTrace();
        }
    }
}
