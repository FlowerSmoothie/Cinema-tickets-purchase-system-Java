package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "movies")
public class Movie implements EntityDB, Serializable
{

    @Id
    @Column(name = "movieID")
    private int ID;

    @Column(name = "Name")
    private String name;

    @Column(name = "Studio")
    private String studio;

    @Column(name = "Description")
    private String description;

    @Column(name = "Age")
    private int age;

    @Column(name = "Rating")
    private float rating;

    @Column(name = "Visible")
    private int visible;

    public Movie()
    {

    }

    public Movie(int ID, String name, String studio, String description, int age, float rating, int visible)
    {
        this.ID = ID;
        this.name = name;
        this.studio = studio;
        this.description = description;
        this.age = age;
        this.rating = rating;
        this.visible = visible;
    }

    public void setID(int id) { this.ID = id; }
    public int getID() { return ID; }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public void setStudio(String studio) { this.studio = studio; }
    public String getStudio() { return studio; }

    public void setDescription(String description) { this.description = description; }
    public String getDescription() { return description; }

    public void setAge(int age) { this.age = age; }
    public int getAge() { return age; }

    public void setRating(float rating) { this.rating = rating; }
    public float getRating() { return rating; }

    public int getVisible() { return visible; }
    public void setVisible(int visible) { this.visible = visible; }


}
