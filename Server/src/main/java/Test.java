import java.text.SimpleDateFormat;
import java.util.Date;

public class Test
{

    public static void main(String[] args)
    {
        String date = "29.09.2004 13:45";
        SimpleDateFormat formatter1=new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date1 = new Date();
        Date date2 = new Date();
        try
        {
            date1 = formatter1.parse(date);
        }
        catch(Exception e)
        {
            System.out.println("prikol");
        }
        System.out.println(date1.compareTo(date2));
    }

}
