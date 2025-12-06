package windowControllers.movies;

import com.example.client.HelloApplication;
import entities.Movie;
import enums.OptionsForWindows;
import enums.Requests;
import javafx.event.ActionEvent;
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

public class EditMovieInfo {

    @FXML
    private Button backButton;

    @FXML
    private Label oldNameLabel;
    @FXML
    private TextField newNameField;

    @FXML
    private Label oldStudioLabel;
    @FXML
    private TextField newStudioField;

    @FXML
    private TextArea descField;

    @FXML
    private Label oldAgeLabel;
    @FXML
    private TextField newAgeField;

    @FXML
    private Button editButton;

    @FXML
    private Label message;

    private Movie movie;

    @FXML
    private void initialize()
    {
        try
        {
            movie = ClientSocket.getInstance().getMovie();
            oldAgeLabel.setText(Integer.toString(movie.getAge()));
            oldNameLabel.setText(movie.getName());
            oldStudioLabel.setText(movie.getStudio());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            setTextToMessageLabel(Constants.someError);
        }
    }

    private void setTextToMessageLabel(String text)
    {
        message.setVisible(true);
        message.setText(text);
    }

    public void onEditButtonClick() throws IOException
    {
        try
        {
            String s = newNameField.getText();
            if(!s.equals("")) movie.setName(s);
            s = newStudioField.getText();
            if(!s.equals("")) movie.setStudio(s);
            s = descField.getText();
            if(!s.equals("")) movie.setDescription(s);
            s = newAgeField.getText();
            if(!s.equals("")) movie.setAge(Integer.parseInt(newAgeField.getText()));
            movie.setVisible(1);

            ClientRequest request = new ClientRequest();
            request.setRequest(Requests.UPDATE_MOVIE);
            request.setObject(movie);
            ClientSocket.getInstance().getOutStream().writeObject(request);

            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            setTextToMessageLabel(response.getResponseMessage());
        }
        catch(NumberFormatException e)
        {
            setTextToMessageLabel(Constants.checkEnteredData);
        }
        catch(Exception e)
        {
            setTextToMessageLabel(Constants.someError);
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
