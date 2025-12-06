package createTables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {

    public static Connection connection = null;

    public static void connect() throws SQLException
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver found!");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Error finding driver!");
            e.printStackTrace();
            throw new SQLException();
        }
        connection = DriverManager.getConnection("jdbc:mysql://localhost/cinemacourseproject?useUnicode=true&serverTimezone=UTC", "root", "root");
        if(connection == null)
        {
            throw new SQLException();
        }
        else
        {
            System.out.println("Successfully connected");
        }
    }

    public static void close() {
        try {
            if(connection != null) {
                connection.close();
                System.out.println("Closing connection");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close connection!");
        }
    }

}
