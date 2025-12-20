package windowControllers.cinemas;

import com.example.client.HelloApplication;
import entities.Cinema;
import enums.OptionsForWindows;
import enums.Requests;
import enums.Responses;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import stuff.Constants;
import upping.ClientRequest;
import upping.ClientSocket;
import upping.ServerResponse;

import java.io.IOException;

public class EditCinemaInfo
{

    @FXML
    private Button backButton;

    @FXML
    private Label oldNameLabel;
    @FXML
    private TextField newNameField;

    @FXML
    private Label oldAdressLabel;
    @FXML
    private TextField newAdressField;

    @FXML
    private Button changeButton;

    @FXML
    private Label messageLabel;

    private Cinema cinema;


    private void setTextToMessageLabel(String text)
    {
        messageLabel.setVisible(true);
        messageLabel.setText(text);
    }

    @FXML
    private void initialize()
    {
        try
        {
            cinema = ClientSocket.getInstance().getCinema();
            oldAdressLabel.setText(cinema.getAdress());
            oldNameLabel.setText(cinema.getName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            setTextToMessageLabel(Constants.someError);
        }
    }


    public void onEditButtonClick() throws IOException
    {
        String newName = newNameField.getText();
        String newAdress = newAdressField.getText();
        if(!newName.equals("")) cinema.setName(newName);
        if(!newAdress.equals("")) cinema.setAdress(newAdress);
        cinema.setVisible(1);
        try
        {
            ClientRequest request = new ClientRequest();
            request.setRequest(Requests.UPDATE_CINEMA);
            request.setObject(cinema);
            ClientSocket.getInstance().getOutStream().writeObject(request);

            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            setTextToMessageLabel(response.getResponseMessage());
        }
        catch(Exception e)
        {
            setTextToMessageLabel(Constants.errorOnServer);
        }
    }


    public void onBackButtonClick() throws IOException
    {
        try
        {
            ClientSocket.getInstance().setOption(OptionsForWindows.CINEMAS);
            Stage stage = (Stage) backButton.getScene().getWindow();
            Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowSomethingList.fxml"));
            Scene newScene = new Scene(root);
            stage.setScene(newScene);
        }
        catch(Exception e) { e.printStackTrace(); }
    }
}
