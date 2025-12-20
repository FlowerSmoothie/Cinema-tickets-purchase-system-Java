package factory;

public class HallToShow extends Showable
{

    @Override
    public ShowableHall createShowable()
    {
        return new ShowableHall();
    }

}

