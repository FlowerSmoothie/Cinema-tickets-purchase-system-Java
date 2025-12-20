package windowControllers;

import classes.Administrator;
import classes.ContentManager;
import classes.SeanceManager;
import classes.User;
import com.example.client.HelloApplication;
import enums.Requests;
import enums.Responses;
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

public class RegistrationWindow
{
    @FXML
    private Button backButton;
    @FXML
    private Button registerButton;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passField;
    @FXML
    private TextField confirmPassField;
    @FXML
    private Label messageLabel;

    private User user;

    private void setTextToMessageLabel(String text)
    {
        messageLabel.setVisible(true);
        messageLabel.setText(text);
    }

    @FXML
    protected void onBackButtonClick() throws IOException
    {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("AuthorisationWindow.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    @FXML
    protected void onRegisterButtonClick() throws IOException
    {
        String password = passField.getText();
        String password2 = confirmPassField.getText();
        if(password.length() >= 5 && loginField.getText().length() >= 5)
        {
            if(password.equals(password2))
            {
                entities.User u = new entities.User();
                u.setLogin(loginField.getText());
                u.setPasswordHash(password);
                ClientRequest request = new ClientRequest();
                request.setRequest(Requests.REGISTER);
                request.setObject(u);
                ClientSocket.getInstance().getOutStream().writeObject(request);
                try
                {
                    ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
                    u = (entities.User)response.getResponseData();
                    Responses r = response.getResponseStatus();
                    setTextToMessageLabel(response.getResponseMessage());
                }
                catch(Exception e) { setTextToMessageLabel(Constants.errorOnServer); }
            }
            else setTextToMessageLabel(Constants.passwordsDoesntMatch);
        }
        else setTextToMessageLabel(Constants.loginOrPasswordLengthIsNotOkay);
    }

}
