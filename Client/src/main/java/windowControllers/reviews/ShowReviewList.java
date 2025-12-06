package windowControllers.reviews;

import com.example.client.HelloApplication;
import entities.Movie;
import entities.Review;
import enums.Requests;
import enums.Roles;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import upping.ClientRequest;
import upping.ClientSocket;
import upping.ServerResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowReviewList {

    @FXML
    private Button backButton;
    @FXML
    private Button openButton;
    @FXML
    private Button writeButton;

    @FXML
    private ChoiceBox<String> moviesCombo;

    @FXML
    private Button searchButton;

    @FXML
    private ListView<Review> reviewsList;

    private List<Movie> movies;

    private List<Review> reviews;

    private Movie movie;



    private String path = null;

    @FXML
    private void initialize()
    {
        try
        {
            reviewsList.getItems().clear();
            if(reviews != null )reviews.clear();

            ClientRequest request = new ClientRequest();
            request.setRequest(Requests.SHOW_ALL_MOVIES);
            ClientSocket.getInstance().getOutStream().writeObject(request);
            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            movies = (List<Movie>)response.getResponseData();
            for(Movie m: movies)
                moviesCombo.getItems().add(m.getName());

            request = new ClientRequest();
            request.setRequest(Requests.SHOW_REVIEWS);
            ClientSocket.getInstance().getOutStream().writeObject(request);
            response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            reviews = (List<Review>)response.getResponseData();

            reviewsList.setCellFactory(param -> new ListCell<Review>() {
                @Override
                protected void updateItem(Review item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null || item.getText() == null) {
                        setText(null);
                    } else {
                        setText(item.getText());
                    }
                }
            });

            if(ClientSocket.getInstance().getUser().getRole() != Roles.ADMIN) path = "ShowReview.fxml";
            else path = "ModerateReview.fxml";
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private List<Review> filterReviewsByMovie(int movieID)
    {
        List<Review> selected = new ArrayList<>();

        for(Review r: reviews)
            if(r.getMovieID() == movieID)
                selected.add(r);

        return selected;
    }


    public void onBackButtonClick() throws IOException
    {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource(ClientSocket.getInstance().getUser().goToMenu()));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }


    public void onOpenButtonClick() throws IOException
    {
        if(reviewsList.getSelectionModel().getSelectedItem() == null) return;
        ClientSocket.getInstance().setReview(reviewsList.getSelectionModel().getSelectedItem());
        Stage stage = (Stage) openButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource(path));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onWriteButtonClick() throws IOException
    {
        ClientSocket.getInstance().setMovie(movie);
        ClientSocket.getInstance().setReview(reviewsList.getSelectionModel().getSelectedItem());
        Stage stage = (Stage) writeButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("WriteAReview.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onSearchButtonClick() throws IOException
    {
        reviewsList.getItems().clear();

        String name = moviesCombo.getValue();
        if(name == null) return;
        int id = -1;
        for(Movie m: movies)
            if(m.getName().equals(name))
            {
                if(m.getVisible() == 1 && ClientSocket.getInstance().getUser().getRole() != Roles.ADMIN) writeButton.setDisable(false);
                openButton.setDisable(false);
                movie = m;
                id = m.getID();
                break;
            }
        List<Review> selected = filterReviewsByMovie(id);
        reviewsList.getItems().addAll(selected);
    }
}
