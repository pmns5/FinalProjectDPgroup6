package test;

import com.fasterxml.jackson.core.JsonProcessingException;
import filmAPI.microservices.CastImplementation;
import filmAPI.models.Cast;
import filmAPI.models.Utils;
import junit.framework.TestCase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CastImplementationTest extends TestCase {
    private CastImplementation service;

    @Order(1)
    protected void setUp() {
        service = new CastImplementation();
    }

    @Order(2)
    public final void testAddCast() {
        int id_film = 1;
        String[] actors = {"3", "2"};
        boolean result = service.addCast(id_film, actors);
        assertTrue(result);
    }

    @Order(3)
    public final void testEditCast() {
        int id_film = 1;
        String[] actors = {"1", "5"};
        boolean result = service.editCast(id_film, actors);
        assertTrue(result);
    }

    @Order(4)
    public final void testGetByFilm() throws JsonProcessingException {
        int id_film = 1;
        int id_actor1 = 1;
        int id_actor2 = 5;
        Cast cast = new Cast(id_film, id_actor1);
        Cast cast2 = new Cast(id_film, id_actor2);
        List<Cast> expected = new ArrayList<>();
        expected.add(cast);
        expected.add(cast2);
        List<Cast> result = service.getByFilm(id_film);
        assertEquals(Utils.toJSON(expected), Utils.toJSON(result));
    }

    @Order(5)
    public final void testDeleteCast() {
        int id_Cast = 1;
        boolean result = service.deleteCast(id_Cast);
        assertTrue(result);
    }

    @Order(6)
    protected void tearDown() {
        service = null;
    }
}
