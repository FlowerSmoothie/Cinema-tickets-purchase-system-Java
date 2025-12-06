package windowCoolTables;

public class HallCinemaTableEntity
{

    private int hallID;
    private String cinemaName;

    public HallCinemaTableEntity() {}

    public HallCinemaTableEntity(int hallID, String cinemaName)
    {
        this.hallID = hallID;
        this.cinemaName = cinemaName;
    }

    public int getHallID() {
        return hallID;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setHallID(int hallID) {
        this.hallID = hallID;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }
}
