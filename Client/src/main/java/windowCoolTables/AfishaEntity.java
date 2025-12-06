package windowCoolTables;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AfishaEntity
{

    private int column_id;
    private String column_movie;
    private String column_cinema;
    private LocalDate column_date;
    private LocalTime column_time;
    private float column_price;

    public AfishaEntity()
    {

    }

    public AfishaEntity(int seanceNumber, String movie, String cinema, LocalDate date, LocalTime time, float price)
    {
        this.column_id = seanceNumber;
        this.column_movie = movie;
        this.column_cinema = cinema;
        this.column_date = date;
        this.column_time = time;
        this.column_price = price;
    }

    public void setColumn_id(int seanceNumber) { this.column_id = seanceNumber; }
    public int getColumn_id() { return column_id; }

    public void setColumn_movie(String column_movie) { this.column_movie = column_movie; }
    public String getColumn_movie() { return column_movie; }

    public void setColumn_cinema(String cinema) { this.column_cinema = cinema; }
    public String getColumn_cinema() { return column_cinema; }

    public void setColumn_date(LocalDate column_date) { this.column_date = column_date; }
    public void setColumn_date(String date) { this.column_date = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy")); }
    public LocalDate getColumn_date() { return column_date; }

    public void setColumn_time(LocalTime time) { this.column_time = time; }
    public void setColumn_time(String time) { this.column_time = LocalTime.parse(time); }
    public LocalTime getColumn_time() { return column_time; }

    public void setColumn_price(float column_price) { this.column_price = column_price; }
    public float getColumn_price() { return column_price; }
}
