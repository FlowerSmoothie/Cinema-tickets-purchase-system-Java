package windowControllers.movies;

import com.example.client.HelloApplication;
import entities.Movie;
import enums.OptionsForWindows;
import enums.Requests;
import enums.Responses;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import stuff.Constants;
import upping.ClientRequest;
import upping.ClientSocket;
import upping.ServerResponse;

import java.io.IOException;
import java.util.Objects;

public class AddMovieInfo
{

    @FXML
    private Button backButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField studioField;
    @FXML
    private TextArea descField;
    @FXML
    private TextField ageField;
    @FXML
    private Button addButton;
    @FXML
    private Label message;

    private Movie movie;

    private void setTextToMessageLabel(String text)
    {
        message.setVisible(true);
        message.setText(text);
    }

    public void onAddButtonClick() throws IOException
    {
        String movieName = nameField.getText();
        String studioName = studioField.getText();
        String description = descField.getText();
        String age = ageField.getText();
        int ageInt;

        if(!Objects.equals(movieName, "") && !Objects.equals(studioName, "") && !Objects.equals(description, "") && !Objects.equals(age, ""))
        {
            try
            {
                movie = new Movie();
                ageInt = Integer.parseInt(age);
                movie.setName(movieName);
                movie.setStudio(studioName);
                movie.setDescription(description);
                movie.setAge(ageInt);
                movie.setVisible(1);
                ClientRequest request = new ClientRequest();
                request.setRequest(Requests.ADD_MOVIE);
                request.setObject(movie);
                ClientSocket.getInstance().getOutStream().writeObject(request);
                ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
                if(response.getResponseStatus() == Responses.OKAY)
                    onBackButtonClick();
                else setTextToMessageLabel(response.getResponseMessage());
            }
            catch(NumberFormatException e)
            {
                setTextToMessageLabel(Constants.checkEnteredData);
            }
            catch (Exception e)
            {
                setTextToMessageLabel(Constants.errorOnServer);
            }
        }
    }

    public void onBackButtonClick() throws IOException
    {
        ClientSocket.getInstance().setOption(OptionsForWindows.MOVIES);
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowSomethingList.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
}
