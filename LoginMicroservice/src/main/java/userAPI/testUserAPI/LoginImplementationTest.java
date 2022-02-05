package userAPI.testUserAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import userAPI.Util;
import userAPI.microservices.Login.LoginImplementation;
import userAPI.microservices.Login.UserCookie;
import userAPI.microservices.Login.UserLogin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginImplementationTest {
    private LoginImplementation service;

    @BeforeEach
    protected void setUp() {
        service = new LoginImplementation();
    }

    @Test
    public final void testLoginUser() throws JsonProcessingException, SQLException {
        UserLogin userLogin = new UserLogin();
        userLogin.setUser("vincenzo");
        //userLogin.setUser("vincenzo@gmail.com");
        userLogin.setPassword("vincenzo");
        UserCookie result = service.loginUser(userLogin);
        UserCookie expected = new UserCookie(2, "vincenzo", "manager", 0);
        Assertions.assertEquals(Util.toJSON(expected), Util.toJSON(result));
    }

    @Test
    public final void testGetNoBannedUsers() throws JsonProcessingException, SQLException {
        List<UserCookie> expected = new ArrayList<>();
        expected.add(new UserCookie(2, "giuseppe", "manager", 0));
        expected.add(new UserCookie(3, "vincenzo", "manager", 0));
        expected.add(new UserCookie(4, "paolo", "manager", 0));
        expected.add(new UserCookie(5, "dario", "manager", 0));
        expected.add(new UserCookie(11, "rita", "client", 0));
        List<UserCookie> result = service.getNoBannedUsers();
        Assertions.assertEquals(Util.toJSON(expected), Util.toJSON(result));
    }

    @Test
    public final void testUsers() throws JsonProcessingException, SQLException {
        List<UserCookie> expected = new ArrayList<>();
        expected.add(new UserCookie(2, "giuseppe", "manager", 0));
        expected.add(new UserCookie(3, "vincenzo", "manager", 0));
        expected.add(new UserCookie(4, "paolo", "manager", 0));
        expected.add(new UserCookie(5, "dario", "manager", 0));
        expected.add(new UserCookie(6, "nicola", "client", 1));
        expected.add(new UserCookie(7, "saverio", "client", 1));
        expected.add(new UserCookie(8, "francesco", "client", 1));
        expected.add(new UserCookie(9, "raffaele", "client", 1));
        expected.add(new UserCookie(10, "michele", "client", 1));
        expected.add(new UserCookie(11, "rita", "client", 0));
        List<UserCookie> result = service.getUsers();
        Assertions.assertEquals(Util.toJSON(expected), Util.toJSON(result));
    }

    @AfterEach
    protected void tearDown() {
        service = null;
    }
}
