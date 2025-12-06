package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class UserTest
{

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void testSetAndGetIDWhenValidIDThenIDSetAndReturned() {
        int expectedID = 123;
        user.setID(expectedID);
        int actualID = user.getID();
        Assertions.assertEquals(expectedID, actualID);
    }

    @Test
    public void testSetAndGetLoginWhenValidLoginThenLoginSetAndReturned() {
        String expectedLogin = "testUser";
        user.setLogin(expectedLogin);
        String actualLogin = user.getLogin();
        Assertions.assertEquals(expectedLogin, actualLogin);
    }

    @Test
    public void testSetAndGetPasswordHashWhenValidPasswordThenHashedPasswordSetAndReturned() {
        String password = "password123";
        user.setPasswordHash(password);
        String actualPasswordHash = user.getPassword();
        Assertions.assertNotEquals(password, actualPasswordHash);
    }

    @Test
    public void testSetAndGetPasswordWhenValidPasswordThenPasswordSetAndReturned() {
        String expectedPassword = "password123";
        user.setPassword(expectedPassword);
        String actualPassword = user.getPassword();
        Assertions.assertEquals(expectedPassword, actualPassword);
    }

    @Test
    public void testComparePasswordWhenCorrectPasswordThenTrueReturned() {
        String password = "password123";
        user.setPassword(password);
        Assertions.assertTrue(user.comparePassword(password));
    }

    @Test
    public void testComparePasswordWhenIncorrectPasswordThenFalseReturned() {
        String password = "password123";
        user.setPassword(password);
        Assertions.assertFalse(user.comparePassword("incorrectPassword"));
    }

    @Test
    public void testCompareLoginWhenCorrectLoginThenTrueReturned() {
        String login = "testUser";
        user.setLogin(login);
        Assertions.assertTrue(user.compareLogin(login));
    }

    @Test
    public void testCompareLoginWhenIncorrectLoginThenFalseReturned() {
        String login = "testUser";
        user.setLogin(login);
        Assertions.assertFalse(user.compareLogin("wrongUser"));
    }

    @Test
    public void testSetAndGetRoleWhenValidRoleThenRoleSetAndReturned() {
        int expectedRole = 1;
        user.setRole(expectedRole);
        int actualRole = user.getRole();
        Assertions.assertEquals(expectedRole, actualRole);
    }

    @Test
    public void testSetAndGetStatusWhenValidStatusThenStatusSetAndReturned() {
        int expectedStatus = 0;
        user.setStatus(expectedStatus);
        int actualStatus = user.getStatus();
        Assertions.assertEquals(expectedStatus, actualStatus);
    }
}