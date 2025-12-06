package windowControllers.reviews;

import com.example.client.HelloApplication;
import entities.Review;
import entities.User;
import enums.OptionsForWindows;
import enums.Requests;
import enums.UserStatuses;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import upping.ClientRequest;
import upping.ClientSocket;
import upping.ServerResponse;

import java.io.IOException;

public class ShowReview {
    @FXML
    private Button backButton;

    @FXML
    private Label authorLabel;

    @FXML
    private TextArea reviewTextArea;

    @FXML
    private Label starsLabel;

    private Review review;

    private User user;

    @FXML
    private void initialize()
    {
        try
        {
            starsLabel.setText("");

            review = ClientSocket.getInstance().getReview();

            ClientRequest request = new ClientRequest();
            request.setRequest(Requests.SHOW_USER);
            request.setObject(review.getUserID());
            ClientSocket.getInstance().getOutStream().writeObject(request);
            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            user = (User)response.getResponseData();

            authorLabel.setText(user.getLogin());
            reviewTextArea.setText(review.getText());
            switch(review.getStars())
            {
                case 5:
                    starsLabel.setText(starsLabel.getText() + "*");
                case 4:
                    starsLabel.setText(starsLabel.getText() + "*");
                case 3:
                    starsLabel.setText(starsLabel.getText() + "*");
                case 2:
                    starsLabel.setText(starsLabel.getText() + "*");
                case 1:
                    starsLabel.setText(starsLabel.getText() + "*");
                    break;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void onBackButtonClick() throws IOException
    {
        ClientSocket.getInstance().setOption(OptionsForWindows.MOVIES);
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowReviewList.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
}
