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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddHallInfo {
    @FXML
    private Button backButton;
    @FXML
    private Label hallNumberLabel;
    @FXML
    private TextArea seatsList;
    @FXML
    private TextField YcountTextField;
    @FXML
    private TextField XcountTextField;
    @FXML
    private Button createHallButton;
    @FXML
    private TextField YcoordinateField;
    @FXML
    private TextField XcoordinateField;

    @FXML
    private ToggleGroup type;
    @FXML
    private RadioButton standartRadio;
    @FXML
    private RadioButton comfortRadio;
    @FXML
    private RadioButton VIPRadio;

    @FXML
    private Button UpdateSeatButton;
    @FXML
    private Button UpdateHallButton;

    @FXML
    private Label messageLabel;

    private int hallNumber;

    private Responses r;

    private int x;
    private int y;

    private int xPos;
    private int yPos;

    private Hall hall = new Hall();
    private List<Seat> seats = new ArrayList<>();

    private SeatTypes sType;

    @FXML
    private void getType(ActionEvent event)
    {
        if(standartRadio.isSelected()) { sType = SeatTypes.STANDART; }
        else if(comfortRadio.isSelected()) { sType = SeatTypes.COMFORT; }
        else if(VIPRadio.isSelected()) { sType = SeatTypes.VIP; }
    }

    private void redrawHallInTextArea()
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
        seatsList.setText(text);
    }

    private void setTextToMessageLabel(String text)
    {
        messageLabel.setVisible(true);
        messageLabel.setText(text);
    }

    @FXML
    private void initialize()
    {
        try
        {
            createHallButton.setDisable(true);
            standartRadio.setToggleGroup(type);
            comfortRadio.setToggleGroup(type);
            VIPRadio.setToggleGroup(type);
            UpdateSeatButton.setDisable(true);
            //hallNumber = ClientSocket.getInstance().getCinema().getCountOfHalls();
            ClientRequest request = new ClientRequest();
            request.setRequest(Requests.CINEMA_HALL_COUNT);
            request.setObject(ClientSocket.getInstance().getCinema().getID());
            ClientSocket.getInstance().getOutStream().writeObject(request);

            hallNumber = (Integer)((ServerResponse)ClientSocket.getInstance().getInStream().readObject()).getResponseData() + 1;
            hallNumberLabel.setText(Integer.toString(hallNumber));
            hall.setNumber(hallNumber);
            hall.setVisible(1);
            hall.setCinemaID(ClientSocket.getInstance().getCinema().getID());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            setTextToMessageLabel(Constants.errorOnServer);
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

    public void onCreateHallButtonClick() throws IOException
    {
        ClientRequest request = new ClientRequest();
        request.setRequest(Requests.ADD_HALL);
        request.setObject(hall);
        ClientSocket.getInstance().getOutStream().writeObject(request);
        try
        {
            ServerResponse response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            int id = (Integer)response.getResponseData();
            seats.forEach((s) -> s.setHallID(id));
            request = new ClientRequest();
            request.setRequest(Requests.ADD_SEATS);
            request.setObject(seats);
            ClientSocket.getInstance().getOutStream().writeObject(request);

            response = (ServerResponse)ClientSocket.getInstance().getInStream().readObject();
            if(response.getResponseStatus() == Responses.OKAY)
            {
                onBackButtonClick();
                Thread.sleep(50 * x * y);
            }
            else setTextToMessageLabel(response.getResponseMessage());
        }
        catch(Exception e) { setTextToMessageLabel(Constants.errorOnServer); }
    }

    public void onUpdateSeatButtonClick() throws IOException
    {
        try
        {
            xPos = Integer.parseInt(XcoordinateField.getText() );
            yPos = Integer.parseInt(YcoordinateField.getText());
            if(xPos > hall.getLength() || yPos > hall.getWidth()) throw new IndexOutOfBoundsException();
            seats.get(hall.getWidth() * (yPos-1) + (xPos-1)).setType(sType);
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

    public void onUpdateHallButtonClick() throws IOException
    {
        try
        {
            x = Integer.parseInt(XcountTextField.getText());
            y = Integer.parseInt(YcountTextField.getText());
            hall.setLength(x);
            hall.setWidth(y);
//            Seat[] seatsArr = new Seat[x * y];
//            seats = Arrays.asList(seatsArr);
//            seats.forEach((s) -> s.setType(SeatTypes.STANDART));
            int kol = x * y;
            for(int i = 0, tempX = 1, tempY = 1; i < kol; i++)
            {
                Seat s = new Seat();
                s.setType(SeatTypes.STANDART);
                s.setY(tempY);
                s.setX(tempX);
                seats.add(s);
                tempX++;
                if(tempX > x) { tempX = 1; tempY++; }
            }
            redrawHallInTextArea();
            hall.setWidth(y);
            hall.setLength(x);
            UpdateHallButton.setDisable(true);
            UpdateSeatButton.setDisable(false);
            createHallButton.setDisable(false);
        }
        catch(NumberFormatException e)
        {
            setTextToMessageLabel(Constants.shouldBeNumbersInFields);
        }
    }
}
