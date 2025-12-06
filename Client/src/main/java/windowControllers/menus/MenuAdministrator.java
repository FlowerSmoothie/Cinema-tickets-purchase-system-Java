package windowControllers.menus;

import com.example.client.HelloApplication;
import enums.OptionsForWindows;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import upping.ClientSocket;

import java.io.IOException;

public class MenuAdministrator {

    @FXML
    private Button logOutButton;

    @FXML
    private Button showAfishaButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button viewReviewsButton;

    @FXML
    private Button viewSeancesButton;

    @FXML
    private Button viewMoviesButton;

    @FXML
    private Button viewCinemasButton;

    @FXML
    private Button viewStatisticsButton;

    @FXML
    private Button viewUsersButton;

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

    public void onMReviewsButtonClick() throws IOException
    {
        Stage stage = (Stage) viewReviewsButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowReviewList.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onSeancesButtonClick() throws IOException
    {
        Stage stage = (Stage) viewSeancesButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowSeanceList.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onMoviesButtonClick() throws IOException
    {
        ClientSocket.getInstance().setOption(OptionsForWindows.MOVIES);
        Stage stage = (Stage) viewMoviesButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowSomethingList.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onCinemasButtonClick() throws IOException
    {
        ClientSocket.getInstance().setOption(OptionsForWindows.CINEMAS);
        Stage stage = (Stage) viewCinemasButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowSomethingList.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onStatisticsButtonClick() throws IOException
    {
        Stage stage = (Stage) viewStatisticsButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowStatisticsOnTickets.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onUsersButtonClick() throws IOException
    {Stage stage = (Stage) viewStatisticsButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowUserList.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
}
