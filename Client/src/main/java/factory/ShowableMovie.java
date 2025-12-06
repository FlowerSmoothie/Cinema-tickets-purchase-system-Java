package factory;

import entities.Movie;

public class ShowableMovie implements ShowableOnViewList
{

    private int ID;

    private String name;

    private String studio;

    private String description;

    private int age;

    private float rating;

    private int visible;

    public ShowableMovie()
    {

    }

    public ShowableMovie(int ID, String name, String studio, String description, int age, float rating, int visible)
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

    public void convertFromEntity(Object entity)
    {
        Movie m = (Movie)entity;
        this.name = m.getName();
        this.ID = m.getID();
        this.age = m.getAge();
        this.description = m.getDescription();
        this.rating = m.getRating();
        this.studio = m.getStudio();
    }

    public Object convertToEntity()
    {
        return new Movie(ID, name, studio, description, age, rating, visible);
    }

}
