package createTables;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CreateTables
{

    public static void main(String[] args) {
        Statement stmt = null;
        try
        {

            System.out.println("This will DELETE all data, do you want to continue? (y/n) ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if(input.equals("y") || input.equals("Y"))
            {
                JDBC.connect();
                stmt = JDBC.connection.createStatement();

//                stmt.executeUpdate("DROP TABLE IF EXISTS statistics");
//                stmt.executeUpdate("DROP TABLE IF EXISTS reviews");
//                stmt.executeUpdate("DROP TABLE IF EXISTS orders");
//                stmt.executeUpdate("DROP TABLE IF EXISTS tickets");
//                stmt.executeUpdate("DROP TABLE IF EXISTS seats");
//                stmt.executeUpdate("DROP TABLE IF EXISTS seances");
//                stmt.executeUpdate("DROP TABLE IF EXISTS halls");
//                stmt.executeUpdate("DROP TABLE IF EXISTS movies");
//                stmt.executeUpdate("DROP TABLE IF EXISTS cinemas");
//                stmt.executeUpdate("DROP TABLE IF EXISTS users");


                String usersTable = "CREATE TABLE IF NOT EXISTS users (" +
                        "  ID int NOT NULL AUTO_INCREMENT," +
                        "  Login char(21) DEFAULT NULL," +
                        "  Password char(65) DEFAULT NULL," +
                        "  Role int DEFAULT NULL," +
                        "  Status int DEFAULT NULL," +
                        "  PRIMARY KEY (ID))";
                stmt.executeUpdate(usersTable);

                String addAdmin = "INSERT INTO users (Login, Password, Role, Status) VALUES ('admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '1', '0')";
                stmt.executeUpdate(addAdmin);

                System.out.println("Created Users table");




                String cinemasTable = "CREATE TABLE IF NOT EXISTS cinemas " +
                        "  (ID int NOT NULL AUTO_INCREMENT, " +
                        "  Name char(50) DEFAULT NULL, " +
                        "  Adress char(255) DEFAULT NULL, " +
                        "  CountOfHalls int DEFAULT NULL, " +
                        "  Visible int DEFAULT 1," +
                        "  PRIMARY KEY (ID))";
                stmt.executeUpdate(cinemasTable);
                System.out.println("Created Cinemas table");


                String hallsTable = "CREATE TABLE IF NOT EXISTS halls (" +
                        "  `ID` int NOT NULL AUTO_INCREMENT," +
                        "  `Number` int DEFAULT NULL," +
                        "  `CinemaID` int DEFAULT NULL," +
                        "  `Length` int NOT NULL DEFAULT '0'," +
                        "  `Width` int NOT NULL DEFAULT '0'," +
                        "  PRIMARY KEY (ID)," +
                        "  KEY Cinema (CinemaID)," +
                        "  Visible int DEFAULT 1," +
                        "  CONSTRAINT Cinema FOREIGN KEY (CinemaID) REFERENCES cinemas (ID))";
                stmt.executeUpdate(hallsTable);
                System.out.println("Created Halls table");


                String moviesTable = "CREATE TABLE IF NOT EXISTS movies (" +
                        "  MovieID int NOT NULL AUTO_INCREMENT," +
                        "  Name char(50) DEFAULT NULL," +
                        "  Studio char(50) DEFAULT NULL," +
                        "  Description char(255) DEFAULT NULL," +
                        "  Age int DEFAULT NULL," +
                        "  Rating float DEFAULT NULL," +
                        "  Visible int DEFAULT 1," +
                        "  PRIMARY KEY (`MovieID`))";
                stmt.executeUpdate(moviesTable);
                System.out.println("Created Movies table");


                String seancesTable = "CREATE TABLE IF NOT EXISTS seances (" +
                        "  ID int NOT NULL AUTO_INCREMENT," +
                        "  MovieID int NOT NULL DEFAULT '0'," +
                        "  HallId int NOT NULL DEFAULT '0'," +
                        "  Date char(10) NOT NULL DEFAULT '0'," +
                        "  Time char(5) NOT NULL DEFAULT '0'," +
                        "  NormalPrice float DEFAULT NULL," +
                        "  ComfortPrice float DEFAULT NULL," +
                        "  VIPPrice float DEFAULT NULL," +
                        "  CinemaID int DEFAULT NULL," +
                        "  PRIMARY KEY (ID)," +
                        "  KEY MovieID (MovieID)," +
                        "  KEY HallID (HallId)," +
                        "  KEY CinemaID (CinemaID)," +
                        "  CONSTRAINT CinemaID FOREIGN KEY (CinemaId) REFERENCES cinemas (ID)," +
                        "  CONSTRAINT HallID FOREIGN KEY (HallId) REFERENCES halls (ID)," +
                        "  CONSTRAINT MovieID FOREIGN KEY (MovieID) REFERENCES movies (MovieID))";
                stmt.executeUpdate(seancesTable);
                System.out.println("Created Seances table");


                String seatsTable = "CREATE TABLE IF NOT EXISTS seats (" +
                        "  SeatID int NOT NULL AUTO_INCREMENT," +
                        "  HallID int DEFAULT NULL," +
                        "  X int DEFAULT NULL," +
                        "  Y int DEFAULT NULL," +
                        "  Type int DEFAULT NULL," +
                        "  PRIMARY KEY (SeatID)," +
                        "  KEY Hall (HallID)," +
                        "  CONSTRAINT Hall FOREIGN KEY (HallID) REFERENCES halls (ID))";
                stmt.executeUpdate(seatsTable);
                System.out.println("Created Seats table");


                String ticketsTable = "CREATE TABLE IF NOT EXISTS tickets (" +
                        "  TicketID int NOT NULL AUTO_INCREMENT," +
                        "  SeanceID int NOT NULL," +
                        "  SeatID int NOT NULL," +
                        "  Status int NOT NULL," +
                        "  CinemaID int NOT NULL," +
                        "  MovieID int NOT NULL," +
                        "  TicketType int NOT NULL," +
                        "  PRIMARY KEY (TicketID)," +
                        "  KEY SeanceID (SeanceID)," +
                        "  KEY Seat (SeatID)," +
                        "  KEY CinemaIDnum (CinemaID)," +
                        "  KEY MovieIDnum (MovieID)," +
                        "  CONSTRAINT CinemaIDnum FOREIGN KEY (CinemaID) REFERENCES cinemas (ID)," +
                        "  CONSTRAINT MovieIDnum FOREIGN KEY (MovieID) REFERENCES movies (MovieID)," +
                        "  CONSTRAINT SeanceID FOREIGN KEY (SeanceID) REFERENCES seances (ID)," +
                        "  CONSTRAINT Seat FOREIGN KEY (SeatID) REFERENCES seats (SeatID))";
                stmt.executeUpdate(ticketsTable);
                System.out.println("Created Tickets table");


                String ordersTable = "CREATE TABLE IF NOT EXISTS orders (" +
                        "  RecordID int NOT NULL AUTO_INCREMENT," +
                        "  UserID int DEFAULT NULL," +
                        "  Ticket int DEFAULT NULL," +
                        "  Price float DEFAULT NULL," +
                        "  Status int DEFAULT NULL," +
                        "  OrderID int DEFAULT NULL," +
                        "  PRIMARY KEY (RecordID) USING BTREE," +
                        "  KEY User (UserID)," +
                        "  KEY Ticket (Ticket)," +
                        "  CONSTRAINT Ticket FOREIGN KEY (Ticket) REFERENCES tickets (TicketID)," +
                        "  CONSTRAINT User FOREIGN KEY (UserID) REFERENCES users (ID))";
                stmt.executeUpdate(ordersTable);
                System.out.println("Created Orders table");


                String reviewsTable = "CREATE TABLE IF NOT EXISTS reviews (" +
                        "  ID int NOT NULL AUTO_INCREMENT," +
                        "  UserID int DEFAULT NULL," +
                        "  MovieID int DEFAULT '0'," +
                        "  Text char(255) DEFAULT NULL," +
                        "  Stars int DEFAULT NULL," +
                        "  PRIMARY KEY (ID)," +
                        "  KEY UserID (UserID)," +
                        "  KEY Movie (MovieID)," +
                        "  CONSTRAINT Movie FOREIGN KEY (MovieID) REFERENCES movies (MovieID)," +
                        "  CONSTRAINT UserID FOREIGN KEY (UserID) REFERENCES users (ID))";
                stmt.executeUpdate(reviewsTable);
                System.out.println("Created Reviews table");


                String statisticsTable = "CREATE TABLE IF NOT EXISTS statistics (" +
                        "  ID int NOT NULL AUTO_INCREMENT," +
                        "  SeanceID int DEFAULT NULL," +
                        "  MovieID int DEFAULT NULL," +
                        "  standartTicketsTotal int DEFAULT NULL," +
                        "  standartTicketsSold int DEFAULT NULL," +
                        "  standartTicketsPrice float DEFAULT NULL," +
                        "  comfortTicketsTotal int DEFAULT NULL," +
                        "  comfortTicketsSold int DEFAULT NULL," +
                        "  comfortTicketsPrice float DEFAULT NULL," +
                        "  VIPTicketsTotal int DEFAULT NULL," +
                        "  VIPTicketsSold int DEFAULT NULL," +
                        "  VIPTicketsPrice float DEFAULT NULL," +
                        "  PRIMARY KEY (ID)," +
                        "  KEY Seance (SeanceID)," +
                        "  KEY MovieIDStat (MovieID)," +
                        "  CONSTRAINT MovieIDStat FOREIGN KEY (MovieID) REFERENCES movies (MovieID)," +
                        "  CONSTRAINT Seance FOREIGN KEY (SeanceID) REFERENCES seances (ID))";
                stmt.executeUpdate(statisticsTable);
                System.out.println("Created Statistics table");

                //InsertTestData.updateTables();
            }

        } catch(SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } finally {
            // Finally block, used to close resources
            if(stmt != null) {
                JDBC.close();
            }
        }
    }

}
