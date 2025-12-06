package entities;

import enums.TicketStatuses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketTest {

    private Ticket ticket;

    @BeforeEach
    public void setUp() {
        ticket = new Ticket();
    }

    @Test
    public void testSetAndGetID() {
        int expectedID = 123;
        ticket.setID(expectedID);
        int actualID = ticket.getID();
        assertEquals(expectedID, actualID, "The ID returned should be the same as the one set");
    }

    @Test
    public void testSetAndGetSeanceID() {
        int expectedSeanceID = 456;
        ticket.setSeanceID(expectedSeanceID);
        int actualSeanceID = ticket.getSeanceID();
        assertEquals(expectedSeanceID, actualSeanceID, "The SeanceID returned should be the same as the one set");
    }

    @Test
    public void testSetAndGetSeatID() {
        int expectedSeatID = 789;
        ticket.setSeatID(expectedSeatID);
        int actualSeatID = ticket.getSeatID();
        assertEquals(expectedSeatID, actualSeatID, "The SeatID returned should be the same as the one set");
    }

    @Test
    public void testSetAndGetStatus() {
        int expectedStatus = 1;
        ticket.setStatus(expectedStatus);
        int actualStatus = ticket.getStatus();
        assertEquals(expectedStatus, actualStatus, "The Status returned should be the same as the one set");
    }

    @Test
    public void testSetAndGetCinemaID() {
        int expectedCinemaID = 101;
        ticket.setCinemaID(expectedCinemaID);
        int actualCinemaID = ticket.getCinemaID();
        assertEquals(expectedCinemaID, actualCinemaID, "The CinemaID returned should be the same as the one set");
    }

    @Test
    public void testSetAndGetMovieID() {
        int expectedMovieID = 202;
        ticket.setMovieID(expectedMovieID);
        int actualMovieID = ticket.getMovieID();
        assertEquals(expectedMovieID, actualMovieID, "The MovieID returned should be the same as the one set");
    }

    @Test
    public void testSetAndGetType() {
        int expectedType = 303;
        ticket.setType(expectedType);
        int actualType = ticket.getType();
        assertEquals(expectedType, actualType, "The Type returned should be the same as the one set");
    }

    @Test
    public void testSetStatusWithEnum() {
        ticket.setStatus(TicketStatuses.AVAILABLE);
        assertEquals(1, ticket.getStatus(), "The Status should be 1 when AVAILABLE is set");

        ticket.setStatus(TicketStatuses.UNAVAILABLE);
        assertEquals(0, ticket.getStatus(), "The Status should be 0 when UNAVAILABLE is set");
    }
}