package windowControllers.tickets;

import com.example.client.HelloApplication;
import entities.*;
import enums.Requests;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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

public class ShowTickets
{

    @FXML
    private Button backButton;

    @FXML
    private Label hallNumberLabel;

    @FXML
    private TextArea ticketsArea;


    @FXML
    private Label normalPriceLabel;
    @FXML
    private Label comfortPriceLabel;
    @FXML
    private Label VIPpriceLabel;

    List<Ticket> tickets = new ArrayList<>();



    @FXML
    private void initialize()
    {
        try
        {
            Seance s = ClientSocket.getInstance().getSeance();

            ClientRequest request = new ClientRequest();
            request.setRequest(Requests.SHOW_TICKETS_FOR_SEANCE);
            request.setObject(s.getID());
            ClientSocket.getInstance().getOutStream().writeObject(request);

            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            tickets = (List<Ticket>)response.getResponseData();

            request = new ClientRequest();
            request.setRequest(Requests.SHOW_HALL);
            request.setObject(s.getHallID());
            ClientSocket.getInstance().getOutStream().writeObject(request);

            response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            Hall h = (Hall)response.getResponseData();

            int x = h.getLength();
            int y = h.getWidth();

            String str = "";
            Ticket t;

            for(int i = 0; i < y; i++)
            {
                for(int j = 0; j < x; j++)
                {
                    t = tickets.get(y * i + j);
                    if(t.getStatus() == 0)
                        str += "X";
                    else
                    {
                        switch (t.getType())
                        {
                            case 0 -> str += "S";
                            case 1 -> str += "C";
                            case 2 -> str += "V";
                        }
                    }
                    str += " ";
                }
                str += "\n";
            }

            ticketsArea.setText(str);
            hallNumberLabel.setText(Integer.toString(h.getNumber()));

            normalPriceLabel.setText(Float.toString(s.getNormal()));
            comfortPriceLabel.setText(Float.toString(s.getComfort()));
            VIPpriceLabel.setText(Float.toString(s.getVIP()));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public void onBackButtonClick() throws IOException
    {
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowSeanceList.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
}
