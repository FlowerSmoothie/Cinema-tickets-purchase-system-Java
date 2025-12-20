package windowControllers.workWithProfile;

import classes.User;
import com.example.client.HelloApplication;
import entities.Cinema;
import entities.Hall;
import entities.Movie;
import enums.Requests;
import enums.Responses;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import stuff.Constants;
import stuff.Hash;
import stuff.NewUserData;
import upping.ClientRequest;
import upping.ClientSocket;
import upping.ServerResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ChangeLogin
{

    @FXML
    private TextField newLoginField;
    @FXML
    private TextField passwordField;

    @FXML
    private Button backBtn;
    @FXML
    private Button changeBtn;

    @FXML
    private Label message;
    @FXML
    private Label loginLabel;



    private void setTextToMessageLabel(String text)
    {
        message.setVisible(true);
        message.setText(text);
    }

    @FXML
    private void initialize()
    {
        try
        {
            loginLabel.setText(ClientSocket.getInstance().getUser().getData().getLogin());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            setTextToMessageLabel(Constants.someError);
        }
    }

    public void onChangeButtonClick() throws IOException
    {
        try
        {
            NewUserData data = new NewUserData();
            data.setLogin(ClientSocket.getInstance().getUser().getData().getLogin());
            data.setPassword(Hash.getHash(passwordField.getText()));
            data.setNewDate(newLoginField.getText());
            ClientRequest request = new ClientRequest();
            request.setRequest(Requests.CHANGE_LOGIN);
            request.setObject(data);
            ClientSocket.getInstance().getOutStream().writeObject(request);

            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            if(response.getResponseStatus() == Responses.OKAY)
            {
                User user = ClientSocket.getInstance().getUser();
                user.setLogin(newLoginField.getText());
                ClientSocket.getInstance().setUser(user);
                onBackButtonClick();
            }
            else setTextToMessageLabel(response.getResponseMessage());
        }
        catch (Exception e) { setTextToMessageLabel(Constants.errorOnServer); }
    }

    public void onBackButtonClick() throws IOException
    {
        Stage stage = (Stage) backBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("AccountInfoMenu.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
}
