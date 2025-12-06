package windowControllers.users;

import com.example.client.HelloApplication;
import entities.Cinema;
import entities.Hall;
import entities.Movie;
import entities.User;
import enums.Requests;
import factory.ShowableCinema;
import factory.ShowableHall;
import factory.ShowableMovie;
import factory.ShowableOnViewList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import upping.ClientRequest;
import upping.ClientSocket;
import upping.ServerResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowUserList
{

    @FXML
    private Button backButton;
    @FXML
    private Button showButton;

    @FXML
    private ListView<User> userList;

    private List<User> users = new ArrayList<>();




    @FXML
    private void initialize()
    {
        try
        {
            ClientRequest request = new ClientRequest();
            request.setRequest(Requests.SHOW_USERS);
            ClientSocket.getInstance().getOutStream().writeObject(request);
            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            users = (List<User>)response.getResponseData();


            userList.setCellFactory(param -> new ListCell<User>() {
                @Override
                protected void updateItem(User item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null || item.getLogin() == null) {
                        setText(null);
                    } else {
                        setText(item.getLogin());
                    }
                }
            });

            userList.getItems().addAll(users);
            showButton.setDisable(false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void onShowButtonClick() throws IOException
    {
        User u = userList.getSelectionModel().getSelectedItem();
        if(u == null || u.getLogin().equals("admin")) return;
        ClientSocket.getInstance().setUserToChange(u);
        Stage stage = (Stage) showButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowInfoAboutUser.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onBackButtonClick() throws IOException
    {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource(ClientSocket.getInstance().getUser().goToMenu()));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }


}
