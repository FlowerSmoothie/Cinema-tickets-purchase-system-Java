package windowControllers.cinemas;

import com.example.client.HelloApplication;
import entities.Cinema;
import entities.Order;
import entities.Seat;
import enums.OptionsForWindows;
import enums.Requests;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import upping.ClientRequest;
import upping.ClientSocket;
import upping.ServerResponse;
import windowCoolTables.SeatsTableEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowInfoAboutCinema {

    @FXML
    private Button backButton;
    @FXML
    private Button viewHallsButton;

    @FXML
    private Label nameLabel;
    @FXML
    private Label adressLabel;
    @FXML
    private Label hallsLabel;

    public void onBackButtonClick() throws IOException
    {
        String path;
        if(ClientSocket.getInstance().getOption() == OptionsForWindows.TICKET_RESERVATION) path = "TicketReservationWindow.fxml";
        else path = "ShowSomethingList.fxml";
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource(path));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    @FXML
    private void initialize()
    {
        Cinema c = ClientSocket.getInstance().getCinema();

        nameLabel.setText(c.getName());
        adressLabel.setText(c.getAdress());
        hallsLabel.setText(Integer.toString(c.getCountOfHalls()));

        if(ClientSocket.getInstance().getOption() == OptionsForWindows.TICKET_RESERVATION) viewHallsButton.setVisible(false);
    }

    public void onViewHallsButtonClick() throws IOException
    {
        ClientSocket.getInstance().setOption(OptionsForWindows.HALLS);
        Stage stage = (Stage) viewHallsButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowSomethingList.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
}
