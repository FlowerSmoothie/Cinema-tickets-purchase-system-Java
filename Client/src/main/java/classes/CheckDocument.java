package classes;

public class CheckDocument
{

    public static String getCheck(int orderNumber, String cinemaName, String cinemaAdress, String movieName, String date, String time)
    {
        String str = new String ("--------------\n" +
                "Order paid: " + Integer.toString(orderNumber) + "\n" +
                "--------------\n" +
                "Waiting for you in the " + cinemaName + " cinema\n" +
                "Address: " + cinemaAdress + "\n" +
                date + " at " + time + "\n" +
                "--------------\n" +
                movieName + "\n" +
                "--------------\n" +
                "Give this receipt to the administrator at the cinema. Enjoy your leisure! :)\n");
        return str;
    }

}
