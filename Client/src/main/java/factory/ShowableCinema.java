package factory;

import entities.Cinema;
import entities.Hall;
import entities.Movie;

public class ShowableCinema implements ShowableOnViewList
{

    private int ID;

    private String name;

    private String adress;

    private int countOfHalls;

    private int visible;

    public ShowableCinema()
    {

    }

    public ShowableCinema(int ID, String name, String adress, int countOfHalls, int visible)
    {
        this.ID = ID;
        this.name = name;
        this.adress = adress;
        this.countOfHalls = countOfHalls;
        this.visible = visible;
    }

    public int getID() { return ID; }
    public void setID(int id) { this.ID = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAdress() { return adress; }
    public void setAdress(String adress) { this.adress = adress; }

    public int getCountOfHalls() { return countOfHalls; }
    public void setCountOfHalls(int count) { countOfHalls = count; }

    public void convertFromEntity(Object entity)
    {
        Cinema m = (Cinema)entity;
        this.name = m.getName();
        this.ID = m.getID();
        this.countOfHalls = m.getCountOfHalls();
        this.adress = m.getAdress();
    }

    public Object convertToEntity()
    {
        return new Cinema(ID, name, adress, countOfHalls, visible);
    }

}
