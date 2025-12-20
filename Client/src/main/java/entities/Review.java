package entities;

import java.io.Serializable;

public class Review implements EntityDB, Serializable
{

    private int ID;

    private int userID;

    private int movieID;

    private String text;

    private int stars;

    public Review()
    {

    }

    public Review(int ID, int userID, int movieID, String text, int stars)
    {
        this.ID = ID;
        this.userID = userID;
        this.movieID = movieID;
        this.text = text;
        this.stars = stars;
    }

    public void setID(int ID) { this.ID = ID; }
    public int getID() { return ID; }

    public void setUserID(int userID) { this.userID = userID; }
    public int getUserID() { return userID; }

    public void setMovieID(int movieID) { this.movieID = movieID; }
    public int getMovieID() { return movieID; }

    public void setText(String text) { this.text = text; }
    public String getText() { return text; }

    public void setStars(int stars) { this.stars = stars; }
    public int getStars() { return stars; }

}
