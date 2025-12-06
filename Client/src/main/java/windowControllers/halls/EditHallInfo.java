package windowControllers.halls;

import com.example.client.HelloApplication;
import entities.Hall;
import entities.Seat;
import enums.OptionsForWindows;
import enums.Requests;
import enums.Responses;
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
import java.util.List;

public class EditHallInfo
{

    @FXML
    private Button backButton;
    @FXML
    private Button editHallButton;

    @FXML
    private Label numberLabel;
    @FXML
    private Label messageLabel;

    @FXML
    private TextArea seatsList;

    @FXML
    private TextField Ycoord;
    @FXML
    private TextField Xcoord;

    @FXML
    private RadioButton StandartRadio;
    @FXML
    private RadioButton ComfortRadio;
    @FXML
    private RadioButton VIPRadio;

    @FXML
    private ToggleGroup type;



    private Hall hall;
    private List<Seat> seats;

    private int xTotal;
    private int yTotal;

    private int x;
    private int y;

    private SeatTypes sType;

    private void setTextToMessageLabel(String text)
    {
        messageLabel.setVisible(true);
        messageLabel.setText(text);
    }

    private void redrawHallInTextArea()
    {
        String text = "";
        char type = 's';
        for(int i = 0; i < yTotal; i++)
        {
            for(int j = 0; j < xTotal; j++)
            {
                switch(seats.get(yTotal * i + j).getType())
                {
                    case 0: type = 's'; break;
                    case 1: type = 'c'; break;
                    case 2: type = 'v'; break;
                }
                text += (type + " ");
            }
            text += '\n';
        }
        seatsList.setText(text);
    }


    @FXML
    private void initialize()
    {
        try
        {
            hall = ClientSocket.getInstance().getHall();
            xTotal = hall.getLength();
            yTotal = hall.getWidth();
            numberLabel.setText(Integer.toString(hall.getNumber()));
            ClientRequest request = new ClientRequest();
            request.setRequest(Requests.SEATS_FROM_HALL);
            request.setObject(hall.getID());
            ClientSocket.getInstance().getOutStream().writeObject(request);

            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            seats = (List<Seat>)response.getResponseData();

            redrawHallInTextArea();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            setTextToMessageLabel(Constants.errorOnServer);
        }

    }

    @FXML
    private void getType(ActionEvent event)
    {
        if(StandartRadio.isSelected()) { sType = SeatTypes.STANDART; }
        else if(ComfortRadio.isSelected()) { sType = SeatTypes.COMFORT; }
        else if(VIPRadio.isSelected()) { sType = SeatTypes.VIP; }
    }


    public void onBackButtonClick() throws IOException
    {
        ClientSocket.getInstance().setOption(OptionsForWindows.HALLS);
        Stage stage = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("ShowSomethingList.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onEditHallButtonClick() throws IOException
    {
        ClientRequest request = new ClientRequest();
        request.setRequest(Requests.UPDATE_SEAT_LIST);
        request.setObject(seats);
        ClientSocket.getInstance().getOutStream().writeObject(request);
        try
        {
            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            if(response.getResponseStatus() == Responses.OKAY)
                onBackButtonClick();
            else setTextToMessageLabel(response.getResponseMessage());
        }
        catch(Exception e) { setTextToMessageLabel(Constants.errorOnServer); }
    }

    public void onEditSeatButtonClick() throws IOException
    {
        try
        {
            x = Integer.parseInt(Xcoord.getText() );
            y = Integer.parseInt(Ycoord.getText());
            if(x > hall.getLength() || y > hall.getWidth()) throw new IndexOutOfBoundsException();
            seats.get(hall.getWidth() * (y-1) + (x-1)).setType(sType);
            redrawHallInTextArea();
        }
        catch(NumberFormatException e)
        {
            setTextToMessageLabel(Constants.shouldBeNumbersInFields);
        }
        catch (IndexOutOfBoundsException e)
        {
            setTextToMessageLabel(Constants.checkEnteredData);
        }
    }
}
