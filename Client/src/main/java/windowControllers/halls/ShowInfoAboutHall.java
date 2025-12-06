package windowControllers.halls;

import com.example.client.HelloApplication;
import entities.Hall;
import entities.Seat;
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
import javafx.stage.Stage;
import upping.ClientRequest;
import upping.ClientSocket;
import upping.ServerResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowInfoAboutHall
{

    @FXML
    private Button backButton;
    @FXML
    private Label hallNumberLabel;
    @FXML
    private TextArea seatsArea;
    @FXML
    private Label XCountLabel;
    @FXML
    private Label YCountLabel;

    private Hall hall;
    private int x;
    private int y;

    private List<Seat> seats = new ArrayList<>();

    private void drawHallInTextArea()
    {
        String text = "";
        char type = 's';
        for(int i = 0; i < y; i++)
        {
            for(int j = 0; j < x; j++)
            {
                switch(seats.get(y * i + j).getType())
                {
                    case 0: type = 's'; break;
                    case 1: type = 'c'; break;
                    case 2: type = 'v'; break;
                }
                text += (type + " ");
            }
            text += '\n';
        }
        seatsArea.setText(text);
    }

    @FXML
    private void initialize()
    {
        hall = ClientSocket.getInstance().getHall();
        x = hall.getLength();
        y = hall.getWidth();
        XCountLabel.setText(Integer.toString(x));
        YCountLabel.setText(Integer.toString(y));
        hallNumberLabel.setText(Integer.toString(hall.getNumber()));

        try
        {
            ClientRequest request = new ClientRequest();
            request.setRequest(Requests.SEATS_FROM_HALL);
            request.setObject(hall.getID());
            ClientSocket.getInstance().getOutStream().writeObject(request);
            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            seats = (List<Seat>)response.getResponseData();
            drawHallInTextArea();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }


    public void onBackButtonClick() throws IOException
    {
        ClientSocket.getInstance().setOption(OptionsForWindows.HALLS);
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowSomethingList.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
}
