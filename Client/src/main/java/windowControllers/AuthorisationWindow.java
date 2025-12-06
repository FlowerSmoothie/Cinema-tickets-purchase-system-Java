package windowControllers;

import classes.*;
import upping.ClientRequest;
import upping.ClientSocket;
import upping.ServerResponse;
import com.example.client.HelloApplication;
import enums.Requests;
import enums.Responses;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import stuff.Constants;

import java.io.IOException;

public class AuthorisationWindow {


    @FXML
    private Button buttonRegister;
    @FXML
    private Button buttonLogin;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passField;

    private User user;


    private void setTextToMessageLabel(String text)
    {
        messageLabel.setVisible(true);
        messageLabel.setText(text);
    }

    @FXML
    protected void onRegisterButtonClick() throws IOException
    {
        Stage stage = (Stage) buttonRegister.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("RegistrationWindow.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    @FXML
    protected void onLoginButtonClick() throws IOException
    {
        AccountData accountData = new AccountData();
        accountData.setLogin(loginField.getText());
        accountData.setPassword(passField.getText());
        Responses r;
        if(accountData.isFieldLengthsOkay())
        {
            entities.User u = new entities.User();
            u.setLogin(accountData.getLogin());
            u.setPasswordHash(accountData.getPassword());
            ClientSocket.getInstance().getOutStream().flush();
            ClientRequest request = new ClientRequest();
            request.setRequest(Requests.LOGIN);
            request.setObject(u);
            ClientSocket.getInstance().getOutStream().writeObject(request);
            try
            {
                ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
                u = (entities.User)response.getResponseData();
                r = response.getResponseStatus();
                if(r == Responses.OKAY)
                {
                    u.getRole();
                    user = User.createUser(u);
                    switch (user.getStatus())
                    {
                        case BANNED:
                            setTextToMessageLabel(Constants.accountBanned);
                            break;
                        case DEACTIVATED:
                            setTextToMessageLabel(Constants.accountDeactivated);
                            break;
                        case UNBANNED:
                            ClientSocket.getInstance().setUser(user);
                            Stage stage = (Stage) buttonLogin.getScene().getWindow();
                            Parent root = FXMLLoader.load(HelloApplication.class.getResource(user.goToMenu()));
                            Scene newScene = new Scene(root);
                            stage.setScene(newScene);
                            break;
                    }
                }
                else setTextToMessageLabel(response.getResponseMessage());
            }
            catch(Exception e) { e.printStackTrace();
                setTextToMessageLabel(Constants.errorOnServer); }
        }
        else setTextToMessageLabel(Constants.loginOrPasswordLengthIsNotOkay);
    }

}
