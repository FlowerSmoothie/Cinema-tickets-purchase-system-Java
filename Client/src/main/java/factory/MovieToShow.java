package factory;

public class MovieToShow extends Showable
{
    @Override
    public ShowableMovie createShowable()
    {
        return new ShowableMovie();
    }
}
