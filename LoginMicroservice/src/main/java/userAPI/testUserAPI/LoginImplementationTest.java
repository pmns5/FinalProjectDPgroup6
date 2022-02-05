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
    public final void testGetClients() throws JsonProcessingException, SQLException {
        List<UserCookie> expected = new ArrayList<>();
        expected.add(new UserCookie(5, "nicola", "client", 0));
        expected.add(new UserCookie(6, "saverio", "client", 0));
        expected.add(new UserCookie(7, "francesco", "client", 0));
        expected.add(new UserCookie(8, "raffaele", "client", 0));
        expected.add(new UserCookie(9, "michele", "client", 0));
        expected.add(new UserCookie(10, "rita", "client", 0));
        List<UserCookie> result = service.getClients();
        Assertions.assertEquals(Util.toJSON(expected), Util.toJSON(result));
    }

    @AfterEach
    protected void tearDown() {
        service = null;
    }
}
