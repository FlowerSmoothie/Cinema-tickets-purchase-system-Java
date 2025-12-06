package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class CinemaTest {

    private Cinema cinema;

    @BeforeEach
    public void setUp() {
        cinema = new Cinema();
    }

    @Test
    public void testSetAndGetIDWhenValidIDThenReturnID() {

        int expectedID = 10;
        cinema.setID(expectedID);

        int actualID = cinema.getID();

        Assertions.assertEquals(expectedID, actualID);
    }

    @Test
    public void testSetAndGetNameWhenValidNameThenReturnName() {

        String expectedName = "Cineplex";
        cinema.setName(expectedName);

        String actualName = cinema.getName();

        Assertions.assertEquals(expectedName, actualName);
    }

    @Test
    public void testSetAndGetLocationWhenValidLocationThenReturnLocation() {

        String expectedLocation = "1234 Movie St.";
        cinema.setAdress(expectedLocation);

        String actualLocation = cinema.getAdress();

        Assertions.assertEquals(expectedLocation, actualLocation);
    }
}