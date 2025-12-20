package windowControllers.menus;

import com.example.client.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import upping.ClientSocket;

import java.io.IOException;

public class MenuUser {


    @FXML
    private Button logOutButton;

    @FXML
    private Button showAfishaButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button showReviewsButton;

    public void onLogOutButtonClick() throws IOException
    {
        ClientSocket.getInstance().setUser(null);
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("AuthorisationWindow.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onAfishaButtonClick() throws IOException
    {
        Stage stage = (Stage) showAfishaButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("MovieShowsPoster.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onProfileButtonClick() throws IOException
    {
        Stage stage = (Stage) profileButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("AccountInfoMenu.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onReviewsButtonClick() throws IOException
    {
        Stage stage = (Stage) showReviewsButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowReviewList.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
}
