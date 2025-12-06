package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class HallTest {

    private Hall hall;

    @BeforeEach
    void setUp() {
        hall = new Hall();
    }

    @Test
    void testSetAndGetIDWhenValidIDThenReturnID() {

        int expectedID = 123;
        hall.setID(expectedID);

        int actualID = hall.getID();

        Assertions.assertEquals(expectedID, actualID);
    }

    @Test
    void testSetAndGetNumberWhenValidNumberThenReturnNumber() {

        int expectedNumber = 5;
        hall.setNumber(expectedNumber);

        int actualNumber = hall.getNumber();

        Assertions.assertEquals(expectedNumber, actualNumber);
    }

    @Test
    void testSetAndGetCinemaIDWhenValidCinemaIDThenReturnCinemaID() {

        int expectedCinemaID = 10;
        hall.setCinemaID(expectedCinemaID);

        int actualCinemaID = hall.getCinemaID();

        Assertions.assertEquals(expectedCinemaID, actualCinemaID);
    }

    @Test
    void testSetAndGetLengthWhenValidLengthThenReturnLength() {

        int expectedLength = 20;
        hall.setLength(expectedLength);

        int actualLength = hall.getLength();

        Assertions.assertEquals(expectedLength, actualLength);
    }

    @Test
    void testSetAndGetWidthWhenValidWidthThenReturnWidth() {

        int expectedWidth = 15;
        hall.setWidth(expectedWidth);

        int actualWidth = hall.getWidth();

        Assertions.assertEquals(expectedWidth, actualWidth);
    }

    @Test
    void testSetAndGetVisibleWhenValidVisibleThenReturnVisible() {

        int expectedVisible = 1;
        hall.setVisible(expectedVisible);

        int actualVisible = hall.getVisible();

        Assertions.assertEquals(expectedVisible, actualVisible);
    }
}