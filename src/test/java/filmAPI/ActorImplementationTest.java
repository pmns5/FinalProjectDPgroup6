package filmAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import filmAPI.microservices.ActorImplementation;
import filmAPI.models.Actor;
import filmAPI.models.Utils;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActorImplementationTest{
    private ActorImplementation service;

    @BeforeEach
    protected void setUp() {
        //Service initialization
        service = new ActorImplementation();

        String populateQuery = "INSERT INTO test_film_db.actor(name, surname) VALUES ('Default', 'Default') ";
        try (Statement stmt = service.db.getConn().createStatement()) {
            stmt.execute(populateQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Test
    public final void testAddActor() {
        String name = "Vincenzo";
        String surname = "Salvati";
        Actor actor = new Actor(-1, name, surname);
        boolean result = service.addActor(actor);
        assertTrue(result);
    }

    @Test
    public final void testEditActor() {
        int id_Actor = 18;
        Actor expected = new Actor(18, "Johnny", "Deep");
        service.addActor(expected);

        Actor actor = new Actor(id_Actor, "Other", "Other");
        boolean result = service.editActor(actor);
        assertTrue(result);
    }

    @Test
    public final void testDeleteActor() {
        int id_Actor = 18;
        Actor expected = new Actor(18, "Johnny", "Deep");
        service.addActor(expected);

        boolean result = service.deleteActor(id_Actor);
        assertTrue(result);
    }

    @Test
    public final void testGetOneActor() throws JsonProcessingException {
        int id_Actor = 18;
        Actor expected = new Actor(18, "Johnny", "Deep");
        service.addActor(expected);

        Actor result = service.getOneActor(id_Actor);

        assertEquals(expected, result);
    }

    @Test
    public final void testGetAllActor() throws JsonProcessingException {
        // Set-up
        Actor actor0 = new Actor(1, "Default", "Default");
        Actor actor1 = new Actor(2, "Tom", "Cruise");
        Actor actor2 = new Actor(3, "Angelina", "Jolie");
        Actor actor3 = new Actor(4, "Brad", "Pitt");
        service.addActor(actor1);
        service.addActor(actor2);
        service.addActor(actor3);

        // Expected
        List<Actor> expected = new ArrayList<>();
        expected.add(actor0);
        expected.add(actor1);
        expected.add(actor2);
        expected.add(actor3);

        // Actual
        List<Actor> actual = service.getAllActors();
        assertEquals(Utils.toJSON(expected), Utils.toJSON(actual));
    }

    @AfterEach
    protected void tearDown() {

        String deleteQuery = "DELETE FROM test_film_db.actor;";
        String resetQuery =  "Alter table test_film_db.actor AUTO_INCREMENT = 1;";

        try (Statement stmt = service.db.getConn().createStatement()) {
            stmt.execute(deleteQuery);
            stmt.execute(resetQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        service.db.disconnect();
        service = null;
    }
}
