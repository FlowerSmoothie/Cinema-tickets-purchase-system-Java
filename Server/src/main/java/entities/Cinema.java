package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "cinemas")
public class Cinema implements EntityDB, Serializable
{

    @Id
    @Column(name = "ID")
    private int ID;

    @Column(name = "Name")
    private String name;

    @Column(name = "adress")
    private String adress;

    @Column(name = "CountOfHalls")
    private int countOfHalls;

    @Column(name = "Visible")
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
