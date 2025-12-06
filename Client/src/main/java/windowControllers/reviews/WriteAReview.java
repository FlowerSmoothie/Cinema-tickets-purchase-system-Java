package windowControllers.reviews;

import com.example.client.HelloApplication;
import entities.Review;
import entities.Seat;
import enums.Requests;
import enums.SeatTypes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import stuff.Constants;
import upping.ClientRequest;
import upping.ClientSocket;
import upping.ServerResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class WriteAReview {

    @FXML
    private Button backButton;

    @FXML
    private Label movieTitleLabel;

    @FXML
    private TextArea textArea;

    @FXML
    private RadioButton one;
    @FXML
    private RadioButton two;
    @FXML
    private RadioButton three;
    @FXML
    private RadioButton four;
    @FXML
    private RadioButton five;
    @FXML
    private ToggleGroup stars;

    @FXML
    private Button saveButton;



    private int starsCount;



    @FXML
    private void initialize()
    {
        three.setSelected(true);
        movieTitleLabel.setText(ClientSocket.getInstance().getMovie().getName());
        starsCount = 3;
    }


    public void onBackButtonClick() throws IOException
    {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowReviewList.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onRadioButtonClick() throws IOException
    {
        if(one.isSelected()) { starsCount = 1; }
        else if(two.isSelected()) { starsCount = 2; }
        else if(three.isSelected()) { starsCount = 3; }
        else if(four.isSelected()) { starsCount = 4; }
        else if(five.isSelected()) { starsCount = 5; }
    }

    public void onSaveButtonClick() throws IOException
    {
        if(textArea.getText().equals("")) return;
        Review r = new Review();
        r.setText(textArea.getText());
        r.setMovieID(ClientSocket.getInstance().getMovie().getID());
        r.setStars(starsCount);
        r.setUserID(ClientSocket.getInstance().getUser().getID());

        try
        {
            ClientRequest request = new ClientRequest();
            request.setRequest(Requests.SEND_REVIEW);
            request.setObject(r);
            ClientSocket.getInstance().getOutStream().writeObject(request);

            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            onBackButtonClick();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
