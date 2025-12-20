package entities;

import java.io.Serializable;

public class Cinema implements EntityDB, Serializable
{
    private int ID;

    private String name;

    private String adress;

    private int countOfHalls;

    private int visible;

    public Cinema()
    {

    }

    public Cinema(int ID, String name, String adress, int countOfHalls, int visible)
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

    public int getVisible() { return visible; }
    public void setVisible(int visible) { this.visible = visible; }

}
