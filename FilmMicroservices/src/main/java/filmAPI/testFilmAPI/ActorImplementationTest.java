package filmAPI.testFilmAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import filmAPI.Util;
import filmAPI.microservices.ActorManagement.Actor;
import filmAPI.microservices.ActorManagement.ActorImplementation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActorImplementationTest {
    private ActorImplementation service;

    @BeforeEach
    protected void setUp() {
        service = new ActorImplementation();
    }

    @Test
    public final void testAddActor() throws SQLException {
        String name = "Vincenzo";
        String surname = "Salvati";
        Actor actor = new Actor(-1, name, surname);
        Assertions.assertTrue(service.addActor(actor));
    }

    @Test
    public final void testEditActor() throws SQLException {
        Actor actor = new Actor(21, "Other", "Other");
        Assertions.assertTrue(service.editActor(actor));
    }

    @Test
    public final void testDeleteActor() throws SQLException {
        Assertions.assertTrue(service.deleteActor(21));
    }

    @Test
    public final void testGetActor() throws SQLException, JsonProcessingException {
        Actor expected = new Actor(2, "Angelina", "Jolie");
        Actor result = service.getActor(2);
        Assertions.assertEquals(Util.toJSON(expected), Util.toJSON(result));
    }

    @Test
    public final void testGetActors() throws SQLException, JsonProcessingException {
        List<Actor> expected = new ArrayList<>();
        expected.add(new Actor(1, "Tom", "Cruise"));
        expected.add(new Actor(2, "Angelina", "Jolie"));
        expected.add(new Actor(3, "Brad", "Pitt"));
        expected.add(new Actor(4, "George", "Clooney"));
        expected.add(new Actor(5, "Dwayne", "Johnson"));
        expected.add(new Actor(6, "Robert", "Downey"));
        expected.add(new Actor(7, "Bradly", "Cooper"));
        expected.add(new Actor(8, "Al", "Pacino"));
        expected.add(new Actor(9, "Tom", "Holland"));
        expected.add(new Actor(10, "Matt", "Damon"));
        expected.add(new Actor(11, "Robert", "De Niro"));
        expected.add(new Actor(12, "Jennifer", "Lawrence"));
        expected.add(new Actor(13, "Jennifer", "Aniston"));
        expected.add(new Actor(14, "Robert", "Williams"));
        expected.add(new Actor(15, "Tom", "Hardy"));
        expected.add(new Actor(16, "Scarlett", "Johansson"));
        expected.add(new Actor(17, "Margot", "Robbie"));
        expected.add(new Actor(18, "Johnny", "Deep"));
        expected.add(new Actor(19, "Samuel", "Jackson"));
        expected.add(new Actor(20, "Henry", "Ford"));
        List<Actor> result = service.getActors();
        Assertions.assertEquals(Util.toJSON(expected), Util.toJSON(result));
    }


    @AfterEach
    protected void tearDown() {
        service = null;
    }
}
