package factory;

public class CinemaToShow extends Showable
{
    
    @Override
    public ShowableCinema createShowable()
    {
        return new ShowableCinema();
    }
    
}
