package entities;

import enums.OrderStatuses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    private Order order;

    @BeforeEach
    public void setUp() {
        order = new Order();
    }

    @Test
    public void testSetAndGetRecordIDWhenValidRecordIDThenRecordIDIsSetAndRetrieved() {

        int expectedRecordID = 123;
        order.setRecordID(expectedRecordID);

        int actualRecordID = order.getRecordID();

        assertEquals(expectedRecordID, actualRecordID);
    }

    @Test
    public void testSetAndGetUserIDWhenValidUserIDThenUserIDIsSetAndRetrieved() {

        int expectedUserID = 456;
        order.setUserID(expectedUserID);

        int actualUserID = order.getUserID();

        assertEquals(expectedUserID, actualUserID);
    }

    @Test
    public void testSetAndGetTicketWhenValidTicketIDThenTicketIDIsSetAndRetrieved() {

        int expectedTicketID = 789;
        order.setTicket(expectedTicketID);

        int actualTicketID = order.getTicket();

        assertEquals(expectedTicketID, actualTicketID);
    }

    @Test
    public void testSetAndGetPriceWhenValidPriceThenPriceIsSetAndRetrieved() {

        float expectedPrice = 99.99f;
        order.setPrice(expectedPrice);

        float actualPrice = order.getPrice();

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void testSetAndGetStatusWhenValidStatusThenStatusIsSetAndRetrieved() {

        OrderStatuses expectedStatus = OrderStatuses.PAYED;
        order.setStatus(expectedStatus);

        int actualStatus = order.getStatus();

        assertEquals(expectedStatus.ordinal(), actualStatus);
    }

    @Test
    public void testSetAndGetIDWhenValidIDThenIDIsSetAndRetrieved() {

        int expectedID = 1010;
        order.setID(expectedID);

        int actualID = order.getID();

        assertEquals(expectedID, actualID);
    }
}