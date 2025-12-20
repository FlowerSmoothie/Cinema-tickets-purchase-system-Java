package windowControllers.seances;

import com.example.client.HelloApplication;
import entities.*;
import enums.Requests;
import enums.Responses;
import enums.TicketStatuses;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class AddSeanceInfo {
    @FXML
    private Button backButton;
    @FXML
    private Button createButton;

    @FXML
    private ChoiceBox<String> movieCombo;
    @FXML
    private ChoiceBox<String> cinemaCombo;
    @FXML
    private ChoiceBox<Integer> hallCombo;

    @FXML
    private DatePicker date;

    @FXML
    private TextField timeField;
    @FXML
    private TextField standartField;
    @FXML
    private TextField comfortField;
    @FXML
    private TextField VIPField;

    @FXML
    private Label messageLabel;

    private List<Movie> movies;
    private List<Cinema> cinemas;
    private List<Hall> halls;

    String chosenCinema;
    String chosenMovie;

    private void setTextToMessageLabel(String text)
    {
        messageLabel.setVisible(true);
        messageLabel.setText(text);
    }

    private int getMovieIDByName(String movie)
    {
        for(int i = 0; i < movies.size(); i++)
            if(movies.get(i).getName().equals(movie))
                return movies.get(i).getID();
        return -1;
    }

    private int getCinemaIDByName(String cinema)
    {
        for(int i = 0; i < cinemas.size(); i++)
            if(cinemas.get(i).getName().equals(cinema))
                return cinemas.get(i).getID();
        return -1;
    }

    private void setHallsByCinemaID(int cinemaID)
    {
        for(Hall h: halls)
            if(h.getCinemaID() == cinemaID)
                hallCombo.getItems().add(h.getNumber());
        if(hallCombo.getItems().isEmpty()) createButton.setDisable(true);
    }

    private int getHallID(int cinemaID, int hallNumber)
    {
        for(Hall h: halls)
            if(h.getCinemaID() == cinemaID && h.getNumber() == hallNumber)
            {
                X = h.getLength();
                Y = h.getWidth();
                return h.getID();
            }
        return -1;
    }

    private LocalDate minDate;
    private LocalDate maxDate;

    private Seance s = new Seance();

    private int cinemaID;
    private int movieID;
    private int hallNumber;

    private int X;
    private int Y;

    @FXML
    private void initialize()
    {
        try
        {
            cinemaCombo.setOnAction(this::getCinema);
            movieCombo.setOnAction(this::getMovie);

            minDate = LocalDate.now().plusDays(1);
            maxDate = LocalDate.now().plusDays(28);
            date.setDayCellFactory(d ->
                    new DateCell()
                    {
                        @Override
                        public void updateItem(LocalDate item, boolean empty)
                        {
                            super.updateItem(item, empty);
                            setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
                        }
                    });

            ClientRequest request = new ClientRequest();
            request.setRequest(Requests.SHOW_MOVIES);
            ClientSocket.getInstance().getOutStream().writeObject(request);
            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            movies = (List<Movie>)response.getResponseData();
            for(Movie m: movies)
                movieCombo.getItems().add(m.getName());

            request = new ClientRequest();
            request.setRequest(Requests.SHOW_CINEMAS);
            ClientSocket.getInstance().getOutStream().writeObject(request);
            response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            cinemas = (List<Cinema>)response.getResponseData();
            for(Cinema c: cinemas)
                cinemaCombo.getItems().add(c.getName());

            request = new ClientRequest();
            request.setRequest(Requests.SHOW_HALLS);
            ClientSocket.getInstance().getOutStream().writeObject(request);
            response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            halls = (List<Hall>)response.getResponseData();
            System.out.println(halls.get(0).getCinemaID());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            setTextToMessageLabel(Constants.errorOnServer);
        }
    }

    public void getMovie(ActionEvent event)
    {
        chosenMovie = movieCombo.getValue();
        movieID = getMovieIDByName(chosenMovie);
        s.setMovieID(movieID);
    }

    public void getCinema(ActionEvent event)
    {
        chosenCinema = cinemaCombo.getValue();
        hallCombo.getItems().clear();
        cinemaID = getCinemaIDByName(chosenCinema);
        setHallsByCinemaID(cinemaID);
        s.setCinemaID(cinemaID);
    }


    public void onBackButtonClick() throws IOException
    {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowSeanceList.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onCreateButtonClick() throws IOException
    {
        try
        {
            s.setComfort(Float.parseFloat(comfortField.getText()));
            s.setNormal(Float.parseFloat(standartField.getText()));
            s.setVIP(Float.parseFloat(VIPField.getText()));

            LocalDate seanceDate = date.getValue();

            s.setDate(seanceDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

            int hallID = getHallID(cinemaID, hallCombo.getValue());
            s.setHallID(hallID);


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime t = LocalTime.parse(timeField.getText(), formatter);
            s.setTime(t.toString());
            ClientRequest request = new ClientRequest();
            request.setRequest(Requests.ADD_SEANCE);
            request.setObject(s);
            ClientSocket.getInstance().getOutStream().writeObject(request);
            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();

            int normal = 0, comfort = 0, VIP = 0;

            if(response.getResponseStatus() == Responses.OKAY)
            {
                int id = (Integer)response.getResponseData();
                List<Ticket> tickets = new ArrayList<Ticket>();
                int kol = X * Y;
                for(int i = 0; i < kol; i++)
                {
                    Ticket ti = new Ticket();
                    ti.setSeanceID(id);
                    ti.setCinemaID(s.getCinemaID());
                    ti.setMovieID(s.getMovieID());
                    ti.setStatus(TicketStatuses.AVAILABLE);
                    tickets.add(ti);
                }

                request = new ClientRequest();
                request.setRequest(Requests.SEATS_FROM_HALL);
                request.setObject(hallID);
                ClientSocket.getInstance().getOutStream().writeObject(request);
                response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
                List<Seat> seats = (List<Seat>)response.getResponseData();
                for(int i = 0; i < seats.size(); i++)
                {
                    tickets.get(i).setSeatID(seats.get(i).getID());
                    tickets.get(i).setType(seats.get(i).getType());
                }
                request = new ClientRequest();
                request.setRequest(Requests.ADD_TICKETS);
                request.setObject(tickets);
                ClientSocket.getInstance().getOutStream().writeObject(request);
                response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();

                Thread.sleep(500);
                onBackButtonClick();
            }
            else setTextToMessageLabel(response.getResponseMessage());
        }
        catch(NumberFormatException e) { setTextToMessageLabel(Constants.shouldBeNumbersInFields); }
        catch(DateTimeParseException e) { setTextToMessageLabel(Constants.checkEnteredTIme); }
        catch (Exception e) { setTextToMessageLabel(Constants.someError); }
    }
}
