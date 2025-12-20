package windowControllers.users;

import com.example.client.HelloApplication;
import entities.Movie;
import entities.User;
import enums.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import stuff.Constants;
import upping.ClientRequest;
import upping.ClientSocket;
import upping.ServerResponse;

import java.io.IOException;

public class ShowInfoAboutUser
{

    @FXML
    private Button backButton;
    @FXML
    private Label loginLabel;

    @FXML
    private RadioButton userRadio;
    @FXML
    private RadioButton contentRadio;
    @FXML
    private RadioButton seanceRadio;
    @FXML
    private RadioButton adminRadio;

    @FXML
    private ToggleGroup role;

    @FXML
    private Label statusLabel;
    @FXML
    private Button unbanButton;
    @FXML
    private Button banButton;

    @FXML
    private Button updateButton;

    @FXML
    private Label messageLabel;

    private Roles roles;

    private User user;


    @FXML
    private void initialize()
    {
        user = ClientSocket.getInstance().getUserToChange();
        loginLabel.setText(user.getLogin());
        switch (user.getRole())
        {
            case 0:
                userRadio.setSelected(true);
                break;
            case 1:
                adminRadio.setSelected(true);
                break;
            case 2:
                seanceRadio.setSelected(true);
                break;
            case 3:
                contentRadio.setSelected(true);
                break;
        }
        switch (user.getStatus())
        {
            case 0:
                unbanButton.setDisable(true);
                break;
            case 1:
                banButton.setDisable(true);
                break;
            case -1:
                banButton.setDisable(true);
                unbanButton.setDisable(true);
                updateButton.setDisable(true);
                break;
        }
    }


    public void onBackButtonClick() throws IOException
    {
        ClientSocket.getInstance().setUserToChange(null);
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowUserList.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }


    public void onRoleToggle() throws IOException
    {
        if(userRadio.isSelected()) user.setRole(Roles.USER);
        else if(contentRadio.isSelected()) user.setRole(Roles.CONTENT_MANAGER);
        else if(seanceRadio.isSelected()) user.setRole(Roles.SEANCE_MANAGER);
        else if(adminRadio.isSelected()) user.setRole(Roles.ADMIN);
    }

    public void onUnbanClick() throws IOException
    {
        user.setStatus(UserStatuses.UNBANNED);
        unbanButton.setDisable(true);
        banButton.setDisable(false);
    }

    public void onBanClick() throws IOException
    {
        user.setStatus(UserStatuses.BANNED);
        banButton.setDisable(true);
        unbanButton.setDisable(false);
    }

    public void onUpdateClick() throws IOException
    {
        try
        {
            ClientRequest request = new ClientRequest();
            request.setRequest(Requests.UPDATE_USER);
            request.setObject(user);
            ClientSocket.getInstance().getOutStream().writeObject(request);

            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            if(response.getResponseStatus() != Responses.OKAY) messageLabel.setText(Constants.someError);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
