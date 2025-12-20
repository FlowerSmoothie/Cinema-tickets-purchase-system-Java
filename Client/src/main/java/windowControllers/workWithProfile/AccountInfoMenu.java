package windowControllers.workWithProfile;

import classes.AccountData;
import classes.User;
import com.example.client.HelloApplication;
import entities.Order;
import enums.OptionsForWindows;
import enums.Requests;
import enums.Responses;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import stuff.Constants;
import upping.ClientRequest;
import upping.ClientSocket;
import upping.ServerResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccountInfoMenu
{

    @FXML
    private void initialize ()
    {
        if(ClientSocket.getInstance().getUser().getData().getLogin().equals("admin"))
        {
            deactivateAccountButton.setDisable(true);
            changeLoginButton.setDisable(true);
            changePasswordButton.setDisable(true);
            viewOrderListButton.setDisable(true);
        }
    }

    @FXML
    private Button backButton;

    @FXML
    private Button changeLoginButton;

    @FXML
    private Button changePasswordButton;

    @FXML
    private Button viewOrderListButton;

    @FXML
    private Button deactivateAccountButton;

    @FXML
    public void onBackButtonClick() throws IOException
    {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource(ClientSocket.getInstance().getUser().goToMenu()));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    @FXML
    public void onChangeLoginButtonClick() throws IOException
    {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ChangeLogin.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    @FXML
    public void onChangePasswordButtonClick() throws IOException
    {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ChangePassword.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }


    @FXML
    public void onViewOrdersButtonClick() throws IOException
    {
        Stage stage = (Stage) viewOrderListButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowOrderList.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    @FXML
    public void onDeactivateAccButtonClick() throws IOException
    {
        ClientSocket.getInstance().setOption(OptionsForWindows.DEACTIVATE_ACCOUNT);
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ConfirmationWindow.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
}
