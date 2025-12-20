package windowControllers.cinemas;

import classes.User;
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

import static enums.Responses.OKAY;

public class AddCinemaInfo
{

    @FXML
    private Button backButton;

    @FXML
    private Button addCinemaButton;

    @FXML
    private TextField nameField;
    @FXML
    private TextField adressField;
    @FXML
    private Label message;

    private void setTextToMessageLabel(String text)
    {
        message.setVisible(true);
        message.setText(text);
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

    public void onAddCinemaButtonClick() throws IOException
    {
        String name = nameField.getText();
        String adress = adressField.getText();
        if(!name.equals("") && !adress.equals(""))
        {
            try
            {
                Cinema cinema = new Cinema();
                cinema.setName(name);
                cinema.setAdress(adress);
                cinema.setCountOfHalls(0);
                cinema.setVisible(1);
                ClientRequest request = new ClientRequest();
                request.setRequest(Requests.ADD_CINEMA);
                request.setObject(cinema);
                ClientSocket.getInstance().getOutStream().writeObject(request);
                ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
                setTextToMessageLabel(response.getResponseMessage());
                if(response.getResponseStatus() == OKAY) onBackButtonClick();
            }
            catch(Exception e) { setTextToMessageLabel(Constants.errorOnServer); }
        }
        else
            setTextToMessageLabel(Constants.fieldsShouldntBeEmpty);
    }
}
