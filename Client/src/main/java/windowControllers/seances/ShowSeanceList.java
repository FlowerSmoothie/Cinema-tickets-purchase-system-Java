package windowControllers.seances;

import com.example.client.HelloApplication;
import entities.Cinema;
import entities.Hall;
import entities.Movie;
import entities.Seance;
import enums.Requests;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import upping.ClientRequest;
import upping.ClientSocket;
import upping.ServerResponse;
import windowCoolTables.AfishaEntity;
import windowCoolTables.HallCinemaTableEntity;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShowSeanceList
{

    @FXML
    private TableView<AfishaEntity> table;

    @FXML
    private TableColumn<AfishaEntity, Integer> column_id;
    @FXML
    private TableColumn<AfishaEntity, String> column_movie;
    @FXML
    private TableColumn<AfishaEntity, String> column_cinema;
    @FXML
    private TableColumn<AfishaEntity, String> column_date;
    @FXML
    private TableColumn<AfishaEntity, String> column_time;
    @FXML
    private TableColumn<AfishaEntity, Float> column_price;


    @FXML
    private Button backButton;
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button showStatsButton;


    private List<Seance> seances;
    private List<Movie> movies;
    private List<Cinema> cinemas;

    private List<Hall> halls;


    private List<AfishaEntity> aes;

    private ObservableList<AfishaEntity> list;

    private List<HallCinemaTableEntity> hallCinema;

    private boolean asc;

    private String filterOption;


    @FXML
    private void initialize()
    {
        try
        {

            // setting up table columns
            {
                column_id.setCellValueFactory(new PropertyValueFactory<AfishaEntity, Integer>("column_id"));
                column_movie.setCellValueFactory(new PropertyValueFactory<AfishaEntity, String>("column_movie"));
                column_cinema.setCellValueFactory(new PropertyValueFactory<AfishaEntity, String>("column_cinema"));
                column_date.setCellValueFactory(new PropertyValueFactory<AfishaEntity, String>("column_date"));
                column_time.setCellValueFactory(new PropertyValueFactory<AfishaEntity, String>("column_time"));
                column_price.setCellValueFactory(new PropertyValueFactory<AfishaEntity, Float>("column_price"));
            }

            // getting arrays of needed items to work with afisha
            {
                ClientRequest request = new ClientRequest();
                request.setRequest(Requests.SHOW_SEANCES);
                request.setObject(LocalDateTime.now());
                ClientSocket.getInstance().getOutStream().writeObject(request);

                ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
                seances = (List<Seance>)response.getResponseData();


                request = new ClientRequest();
                request.setRequest(Requests.SHOW_MOVIES);
                ClientSocket.getInstance().getOutStream().writeObject(request);

                response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
                movies = (List<Movie>)response.getResponseData();


                request = new ClientRequest();
                request.setRequest(Requests.SHOW_CINEMAS);
                ClientSocket.getInstance().getOutStream().writeObject(request);

                response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
                cinemas = (List<Cinema>)response.getResponseData();

                request = new ClientRequest();
                request.setRequest(Requests.SHOW_HALLS);
                ClientSocket.getInstance().getOutStream().writeObject(request);

                response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
                halls = (List<Hall>)response.getResponseData();
            }

            // setting up table itself with our items
            {
                if(seances == null) return;
                aes = new ArrayList<>();
                AfishaEntity ae;

                for(Movie m: movies)
                    for(int i = 0; i < seances.size(); i++)
                        if(seances.get(i).getMovieID() == m.getID())
                        {
                            ae = new AfishaEntity();
                            ae.setColumn_movie(m.getName());
                            aes.add(ae);
                        }

                hallCinema = new ArrayList<>();
                HallCinemaTableEntity hcte;

                for(Cinema c: cinemas)
                    for(int i = 0; i < halls.size(); i++)
                        if(halls.get(i).getCinemaID() == c.getID())
                        {
                            hcte = new HallCinemaTableEntity();
                            hcte.setCinemaName(c.getName());
                            hcte.setHallID(halls.get(i).getID());
                            hallCinema.add(hcte);
                        }

                for(HallCinemaTableEntity h: hallCinema)
                    for(int i = 0; i < seances.size(); i++)
                        if(seances.get(i).getHallID() == h.getHallID())
                            aes.get(i).setColumn_cinema(h.getCinemaName());

                Seance s;
                AfishaEntity a;
                HallCinemaTableEntity hc;
                float smol;
                float comfort;
                float vip;

                for(int i = 0; i < seances.size(); i++)
                {
                    s = new Seance();
                    a = new AfishaEntity();
                    hc = new HallCinemaTableEntity();
                    s = seances.get(i);
                    a = aes.get(i);
                    a.setColumn_id(s.getID());
                    a.setColumn_date(s.getDate());
                    a.setColumn_time(s.getTime());

                    smol = s.getNormal();
                    comfort = s.getComfort();
                    vip = s.getVIP();

                    if(comfort < smol) smol = comfort;
                    if(vip < smol) smol = vip;

                    a.setColumn_price(smol);

                    aes.set(i, a);
                }

                showStatsButton.setDisable(false);
                list = FXCollections.observableArrayList(aes);
                table.setItems(list);
            }
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
        Stage stage = (Stage) addButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("AddSeanceInfo.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    private int getSelectedSeanceToMemory()
    {
        AfishaEntity a = table.getSelectionModel().getSelectedItem();
        if(a == null) return 0;
        for(Seance s: seances)
            if(a.getColumn_id() == s.getID())
            {
                ClientSocket.getInstance().setSeance(s);
                break;
            }
        return 1;
    }

    public void onShowStatsButtonClick() throws IOException
    {
        if (getSelectedSeanceToMemory() == 0) return;
        Stage stage = (Stage) showStatsButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowTickets.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
}
