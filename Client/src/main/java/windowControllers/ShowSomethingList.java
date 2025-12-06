package windowControllers;

import com.example.client.HelloApplication;
import entities.Cinema;
import entities.Hall;
import entities.Movie;
import entities.Review;
import enums.OptionsForWindows;
import enums.Requests;
import enums.Roles;
import factory.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import upping.ClientRequest;
import upping.ClientSocket;
import upping.ServerResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowSomethingList {

    @FXML
    private ListView<ShowableOnViewList> itemList;

    @FXML
    private Button backButton;

    @FXML
    private Button addButton;
    @FXML
    private Button openButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button sureDeleteButton;

    @FXML
    private Label somethingLabel;

    List<Movie> movies = new ArrayList<>();
    List<Cinema> cinemas = new ArrayList<>();
    List<Hall> halls = new ArrayList<>();
    List<ShowableOnViewList> showables = new ArrayList<>();

    OptionsForWindows opt;

    private void drawTable()
    {
        itemList.getItems().clear();
        showables.clear();

        try
        {
            ClientRequest request = new ClientRequest();

            Requests req = null;

            opt = ClientSocket.getInstance().getOption();

            switch (opt)
            {
                case MOVIES -> { req = Requests.SHOW_MOVIES; somethingLabel.setText("фильмов"); }
                case CINEMAS -> { req = Requests.SHOW_CINEMAS; somethingLabel.setText("кинотеатров"); }
                case HALLS -> {
                    req = Requests.SHOW_HALLS_FROM_CINEMA;
                    request.setObject(ClientSocket.getInstance().getCinema().getID());
                    somethingLabel.setText("залов");
                }
            }

            request.setRequest(req);
            ClientSocket.getInstance().getOutStream().writeObject(request);
            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();

            switch(opt)
            {
                case MOVIES:
                    movies = (List<Movie>)response.getResponseData();
                    if(movies == null) return;
                    ShowableMovie sm;
                    for(Movie m: movies)
                    {
                        sm = new ShowableMovie();
                        sm.setAge(m.getAge());
                        sm.setDescription(m.getDescription());
                        sm.setName(m.getName());
                        sm.setRating(m.getRating());
                        sm.setStudio(m.getStudio());
                        sm.setID(m.getID());
                        showables.add(sm);
                    }
                    break;
                case CINEMAS:
                    cinemas = (List<Cinema>)response.getResponseData();
                    if(cinemas == null) return;
                    ShowableCinema sc;
                    for(Cinema c: cinemas)
                    {
                        sc = new ShowableCinema();
                        sc.setAdress(c.getAdress());
                        sc.setID(c.getID());
                        sc.setName(c.getName());
                        sc.setCountOfHalls(c.getCountOfHalls());
                        showables.add(sc);
                    }
                    break;
                case HALLS:
                    halls = (List<Hall>)response.getResponseData();
                    if(halls == null) return;
                    ShowableHall sh;
                    for(Hall h: halls)
                    {
                        sh = new ShowableHall();
                        sh.setCinemaID(h.getCinemaID());
                        sh.setID(h.getID());
                        sh.setLength(h.getLength());
                        sh.setWidth(h.getWidth());
                        sh.setNumber(h.getNumber());
                        showables.add(sh);
                    }
                    break;
            }

            itemList.getItems().addAll(showables);
        }
        catch (Exception e) { e.printStackTrace(); }

    }

    @FXML
    private void initialize()
    {
        try
        {

            drawTable();

            itemList.setCellFactory(param -> new ListCell<ShowableOnViewList>() {
                @Override
                protected void updateItem(ShowableOnViewList item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null || item.getName() == null) {
                        setText(null);
                    } else {
                        setText(item.getName());
                    }
                }
            });
            openButton.setDisable(false);
            editButton.setDisable(false);
            deleteButton.setDisable(false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void onBackButtonClick() throws IOException
    {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource(ClientSocket.getInstance().getUser().goToMenu()));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onAddButtonClick() throws IOException
    {
        String str = null;
        switch(opt)
        {
            case MOVIES -> str = "AddMovieInfo.fxml";
            case CINEMAS -> str = "AddCinemaInfo.fxml";
            case HALLS -> str = "AddHallInfo.fxml";
        }
        Stage stage = (Stage) addButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource(str));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onShowButtonClick() throws IOException
    {
        try
        {
            String str = null;
            switch(opt)
            {
                case MOVIES -> { str = new String("ShowInfoAboutMovie.fxml"); ClientSocket.getInstance().setMovie((Movie)itemList.getSelectionModel().getSelectedItem().convertToEntity()); }
                case CINEMAS -> { str = new String("ShowInfoAboutCinema.fxml"); ClientSocket.getInstance().setCinema((Cinema)itemList.getSelectionModel().getSelectedItem().convertToEntity()); }
                case HALLS -> { str = new String("ShowInfoAboutHall.fxml"); ClientSocket.getInstance().setHall((Hall)itemList.getSelectionModel().getSelectedItem().convertToEntity()); }
            }
            Stage stage = (Stage) openButton.getScene().getWindow();
            Parent root = FXMLLoader.load(HelloApplication.class.getResource(str));
            Scene newScene = new Scene(root);
            stage.setScene(newScene);
        }
        catch(Exception e) { return; }
    }

    public void onEditButtonClick() throws IOException
    {
        String str = null;
        if(itemList.getSelectionModel().getSelectedItem() == null) return;
        switch(opt)
        {
            case MOVIES -> { str = "EditMovieInfo.fxml"; ClientSocket.getInstance().setMovie((Movie)itemList.getSelectionModel().getSelectedItem().convertToEntity()); }
            case CINEMAS -> { str = "EditCinemaInfo.fxml"; ClientSocket.getInstance().setCinema((Cinema)itemList.getSelectionModel().getSelectedItem().convertToEntity()); }
            case HALLS -> { str = "EditHallInfo.fxml"; ClientSocket.getInstance().setHall((Hall)itemList.getSelectionModel().getSelectedItem().convertToEntity()); }
        }
        Stage stage = (Stage) openButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource(str));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onDeleteButtonClick() throws IOException
    {
        sureDeleteButton.setVisible(true);
    }

    public void onSureDeleteButtonClick() throws IOException
    {
        Requests req = null;
        ClientRequest request = new ClientRequest();
        switch(opt)
        {
            case MOVIES -> { req = Requests.DELETE_MOVIE;
                request.setObject((Movie)itemList.getSelectionModel().getSelectedItem().convertToEntity()); }
            case CINEMAS -> { req = Requests.DELETE_CINEMA;
                request.setObject((Cinema)itemList.getSelectionModel().getSelectedItem().convertToEntity()); }
            case HALLS -> { req = Requests.DELETE_HALL;
                request.setObject((Hall)itemList.getSelectionModel().getSelectedItem().convertToEntity()); }
        }
        request.setRequest(req);
        ClientSocket.getInstance().getOutStream().writeObject(request);

        try
        {
            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            sureDeleteButton.setVisible(false);
            Thread.sleep(100*showables.size());
            //Stage stage = (Stage) backButton.getScene().getWindow();
            //Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowSomethingList.fxml"));
            //Scene newScene = new Scene(root);
            //stage.setScene(newScene);
            drawTable();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
