package userAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import userAPI.Util;
import userAPI.microservices.resources.LoginImplementation;
import userAPI.microservices.beans.UserCookie;
import userAPI.microservices.beans.UserLogin;

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



    @AfterEach
    protected void tearDown() {
        service = null;
    }
}
