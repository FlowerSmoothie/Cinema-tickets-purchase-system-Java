package windowControllers.workWithProfile;

import com.example.client.HelloApplication;
import entities.*;
import enums.Requests;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import windowCoolTables.OrderTable;
import windowCoolTables.SeatsTableEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

public class ShowOrderList
{

    @FXML
    private TableView<OrderTable> table;

    @FXML
    private TableColumn<OrderTable, Integer> column_id;
    @FXML
    private TableColumn<OrderTable, String> column_status;
    @FXML
    private TableColumn<OrderTable, String> column_cinema;
    @FXML
    private TableColumn<OrderTable, String> column_movie;
    @FXML
    private TableColumn<OrderTable, String> column_date;
    @FXML
    private TableColumn<OrderTable, String> column_time;

    @FXML
    private Button backButton;
    @FXML
    private Button chooseOrder;

    private List<Order> orders;
    private List<OrderTable> ordersList;
    private ObservableList<OrderTable> orderObs;

    private List<Movie> movies;
    private List<Hall> halls;
    private List<Cinema> cinemas;


    @FXML
    private void initialize()
    {
        try
        {

            // setting up table columns
            {
                column_id.setCellValueFactory(new PropertyValueFactory<OrderTable, Integer>("orderID"));
                column_status.setCellValueFactory(new PropertyValueFactory<OrderTable, String>("statusStr"));
                column_cinema.setCellValueFactory(new PropertyValueFactory<OrderTable, String>("cinemaStr"));
                column_movie.setCellValueFactory(new PropertyValueFactory<OrderTable, String>("movieStr"));
                column_date.setCellValueFactory(new PropertyValueFactory<OrderTable, String>("date"));
                column_time.setCellValueFactory(new PropertyValueFactory<OrderTable, String>("time"));
            }

            // getting arrays of needed items to work with order
            {

                List<Integer> ticketIDs = new ArrayList<>();


                ClientRequest request = new ClientRequest();
                request.setRequest(Requests.VIEW_ORDER_LIST);
                request.setObject(ClientSocket.getInstance().getUser().getID());
                ClientSocket.getInstance().getOutStream().writeObject(request);

                ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
                orders = (List<Order>)response.getResponseData();



                for (Order o: orders)
                    ticketIDs.add(o.getTicket());


                orders = orders.stream()
                        .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingInt(Order::getID))),
                                ArrayList::new));


                ordersList = new ArrayList<>();
                for(int i = 0; i < orders.size(); i++)
                {
                    OrderTable o = new OrderTable();
                    o.setOrderID(orders.get(i).getID());
                    o.setStatus(orders.get(i).getStatus());
                    switch (orders.get(i).getStatus())
                    {
                        case 0 -> o.setStatusStr("not paid");
                        case 1 -> o.setStatusStr("paid");
                        case 2 -> o.setStatusStr("cancelled");
                    }
                    ordersList.add(o);
                }


                request = new ClientRequest();
                request.setRequest(Requests.GET_TICKETS_BY_IDS);
                request.setObject(ticketIDs);
                ClientSocket.getInstance().getOutStream().writeObject(request);

                response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
                List<Ticket> tickets = (List<Ticket>)response.getResponseData();

                tickets = tickets.stream()
                        .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingInt(Ticket::getSeanceID))),
                                ArrayList::new));

                request = new ClientRequest();
                request.setRequest(Requests.SHOW_MOVIES);
                ClientSocket.getInstance().getOutStream().writeObject(request);

                response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
                movies = (List<Movie>)response.getResponseData();

                movies = movies.stream()
                        .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingInt(Movie::getID))),
                                ArrayList::new));

                request = new ClientRequest();
                request.setRequest(Requests.SHOW_CINEMAS);
                ClientSocket.getInstance().getOutStream().writeObject(request);

                response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
                cinemas = (List<Cinema>)response.getResponseData();

                cinemas = cinemas.stream()
                        .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingInt(Cinema::getID))),
                                ArrayList::new));

                chooseOrder.setDisable(true);

                if(tickets == null) return;

                for(int i = 0; i < tickets.size(); i++)
                {
                    for(int j = 0; j < movies.size(); j++)
                        if(tickets.get(i).getMovieID()
                                == movies.get(j).getID())
                        {
                            ordersList.get(i).setMovieStr(movies.get(j).getName());
                            ordersList.get(i).setMovie(movies.get(j));
                        }
                    for(int j = 0; j < cinemas.size(); j++)
                        if(tickets.get(i).getCinemaID()
                                == cinemas.get(j).getID())
                        {
                            ordersList.get(i).setCinemaStr(cinemas.get(j).getName());
                            ordersList.get(i).setCinema(cinemas.get(j));
                        }
                }


                List<Integer> ints = new ArrayList<>();
                for(Ticket t: tickets)
                    ints.add(t.getSeanceID());

                request = new ClientRequest();
                request.setRequest(Requests.SHOW_SEANCES_BY_IDS);
                request.setObject(ints);

                ClientSocket.getInstance().getOutStream().writeObject(request);

                response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
                List<Seance> seances = (List<Seance>)response.getResponseData();

                for(int i = 0; i < seances.size(); i++)
                {
                    ordersList.get(i).setSeance(seances.get(i));
                    ordersList.get(i).setDate(seances.get(i).getDate());
                    ordersList.get(i).setTime(seances.get(i).getTime());
                }

//                ordersList = ordersList.stream()
//                        .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingInt(OrderTable::getOrderID))),
//                                ArrayList::new));

                orderObs = FXCollections.observableArrayList(ordersList);
                table.setItems(orderObs);

                chooseOrder.setDisable(false);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void onBackClick() throws IOException
    {
        ClientSocket.getInstance().setUserToChange(null);
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("AccountInfoMenu.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onChooseClick() throws IOException
    {
        if(table.getSelectionModel().getSelectedItem() == null) return;
        OrderTable oa = table.getSelectionModel().getSelectedItem();
        ClientSocket.getInstance().setCurrentID(oa.getOrderID());
        ClientSocket.getInstance().setCinema(oa.getCinema());
        ClientSocket.getInstance().setMovie(oa.getMovie());
        ClientSocket.getInstance().setSeance(oa.getSeance());

        Stage stage = (Stage) chooseOrder.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("OrderInfo.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
}
