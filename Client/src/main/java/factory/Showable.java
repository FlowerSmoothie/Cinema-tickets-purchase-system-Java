package factory;

public abstract class Showable 
{
    
    public void createObject()
    {
        ShowableOnViewList obj = createShowable();
    }
    
    public abstract ShowableOnViewList createShowable();
    
}
