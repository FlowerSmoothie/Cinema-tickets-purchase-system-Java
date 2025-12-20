package windowControllers.workWithProfile;

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
import stuff.NewUserData;
import upping.ClientRequest;
import upping.ClientSocket;
import upping.ServerResponse;

import java.io.IOException;

public class ChangePassword {

    @FXML
    private TextField currentPasswordField;
    @FXML
    private TextField newPasswordField;
    @FXML
    private TextField repeatNewPasswordField;

    @FXML
    private Button editButton;
    @FXML
    private Button backButton;

    @FXML
    private Label messageLabel;


    private void setTextToMessageLabel(String text)
    {
        messageLabel.setVisible(true);
        messageLabel.setText(text);
    }

    public void onEditButtonClick() throws IOException
    {
        try
        {
            String curPass = ClientSocket.getInstance().getUser().getData().getPassword();
            if(Hash.getHash(currentPasswordField.getText()).equals(curPass))
            {
                if(newPasswordField.getText().equals(repeatNewPasswordField.getText()))
                {
                    String p = Hash.getHash(newPasswordField.getText());
                    ClientRequest request = new ClientRequest();
                    request.setRequest(Requests.CHANGE_PASSWORD);
                    NewUserData data = new NewUserData();
                    data.setPassword(curPass);
                    data.setNewDate(p);
                    data.setLogin(ClientSocket.getInstance().getUser().getData().getLogin());
                    request.setObject(data);
                    ClientSocket.getInstance().getOutStream().writeObject(request);

                    ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
                    if(response.getResponseStatus() == Responses.OKAY)
                    {
                        User user = ClientSocket.getInstance().getUser();
                        ClientSocket.getInstance().getUser().setPassword(p);
                        ClientSocket.getInstance().setUser(user);
                        onBackButtonClick();
                    }
                    else setTextToMessageLabel(response.getResponseMessage());
                }
                else
                    setTextToMessageLabel(Constants.passwordsDoesntMatch);
            }
            else
                setTextToMessageLabel(Constants.wrongPassword);
        }
        catch (Exception e) { setTextToMessageLabel(Constants.someError); }
    }

    public void onBackButtonClick() throws IOException
    {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("AccountInfoMenu.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
}
