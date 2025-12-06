package windowControllers.movies;

import com.example.client.HelloApplication;
import entities.Movie;
import enums.OptionsForWindows;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import upping.ClientSocket;

import java.io.IOException;

public class ShowInfoAboutMovie
{
    @FXML
    private Button backButton;

    @FXML
    private Label movieNameLabel;
    @FXML
    private Label studioNameLabel;
    @FXML
    private Label ageLabel;
    @FXML
    private Label rateLabel;

    @FXML
    private TextArea descArea;


    @FXML
    private void initialize()
    {
        Movie m = ClientSocket.getInstance().getMovie();
        movieNameLabel.setText(m.getName());
        studioNameLabel.setText(m.getStudio());
        ageLabel.setText(Integer.toString(m.getAge()) + "+");
        float rating = m.getRating();
        if(rating == 0) rateLabel.setText("-");
        else rateLabel.setText(Float.toString(m.getRating()));
        descArea.setText(m.getDescription());
    }


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

}
