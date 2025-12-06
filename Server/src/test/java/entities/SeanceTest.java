package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeanceTest {

    private Seance seance;

    @BeforeEach
    public void setUp() {
        seance = new Seance();
    }

    @Test
    public void testSetAndGetID() {
        int expectedID = 123;
        seance.setID(expectedID);
        int actualID = seance.getID();
        assertEquals(expectedID, actualID);
    }

    @Test
    public void testSetAndGetMovieID() {
        int expectedMovieID = 456;
        seance.setMovieID(expectedMovieID);
        int actualMovieID = seance.getMovieID();
        assertEquals(expectedMovieID, actualMovieID);
    }

    @Test
    public void testSetAndGetCinemaID() {
        int expectedCinemaID = 789;
        seance.setCinemaID(expectedCinemaID);
        int actualCinemaID = seance.getCinemaID();
        assertEquals(expectedCinemaID, actualCinemaID);
    }

    @Test
    public void testSetAndGetPrice() {
        float expectedPrice = 10.5f;
        seance.setNormal(expectedPrice);
        float actualPrice = seance.getNormal();
        assertEquals(expectedPrice, actualPrice, 0.01);
    }
}