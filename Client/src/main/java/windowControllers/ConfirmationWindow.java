package windowControllers;

import classes.User;
import com.example.client.HelloApplication;
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
import stuff.Hash;
import upping.ClientRequest;
import upping.ClientSocket;
import upping.ServerResponse;

import java.io.IOException;

public class ConfirmationWindow {

    @FXML
    private TextField passwordField;

    @FXML
    private Button backButton;
    @FXML
    private Button confirmButton;

    @FXML
    private Label messageLabel;


    private void setTextToMessageLabel(String text)
    {
        messageLabel.setVisible(true);
        messageLabel.setText(text);
    }

    public void onBackButtonClick() throws IOException
    {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("AccountInfoMenu.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onConfirmButtonClick() throws IOException
    {
        try
        {
            String pass = Hash.getHash(passwordField.getText());
            if(pass.equals(ClientSocket.getInstance().getUser().getData().getPassword()))
            {
                ClientRequest request = new ClientRequest();
                request.setRequest(Requests.DEACTIVATE_ACCOUNT);
                request.setObject(ClientSocket.getInstance().getUser().getID());
                ClientSocket.getInstance().getOutStream().writeObject(request);

                ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
                if(response.getResponseStatus() == Responses.OKAY)
                {
                    ClientSocket.getInstance().setUser(null);
                    Stage stage = (Stage) backButton.getScene().getWindow();
                    Parent root = FXMLLoader.load(HelloApplication.class.getResource("AuthorisationWindow.fxml"));
                    Scene newScene = new Scene(root);
                    stage.setScene(newScene);
                }
                else setTextToMessageLabel(response.getResponseMessage());
            }
            else
                setTextToMessageLabel(Constants.wrongPassword);
        }
        catch(Exception e) { setTextToMessageLabel(Constants.someError); }
    }
}
