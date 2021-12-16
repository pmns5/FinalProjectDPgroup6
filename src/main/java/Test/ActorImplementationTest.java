package Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import filmAPI.microservices.ActorImplementation;
import filmAPI.models.Actor;
import filmAPI.models.Utils;
import junit.framework.TestCase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ActorImplementationTest extends TestCase {
    private ActorImplementation service;

    public void main(String[] args) {
        junit.swingui.TestRunner.run(ActorImplementationTest.class);
    }

    @Order(1)
    protected void setUp() {
        //Service initialization
        service = new ActorImplementation();
    }

    @Order(2)
    public final void testAddActor() {
        String name = "Vincenzo";
        String surname = "Salvati";
        Actor actor = new Actor(-1, name, surname);
        boolean result = service.addActor(actor);
        assertTrue(result);
    }

    @Order(3)
    public final void testEditActor() {
        String name = "Vinc";
        String surname = "Salv";
        Actor actor = new Actor(21, name, surname);
        boolean result = service.editActor(actor);
        assertTrue(result);
    }

    @Order(4)
    public final void testDeleteActor() {
        int id_actor = 21;
        boolean result = service.deleteActor(id_actor);
        assertTrue(result);
    }

    @Order(5)
    public final void testGetOneActor() throws JsonProcessingException {
        int id_Actor = 18;
        Actor Actor1 = new Actor(18, "Johnny", "Deep");
        String expected = Utils.toJSON(Actor1);
        Actor Actor2 = service.getOneActor(id_Actor);
        String result = Utils.toJSON(Actor2);
        assertEquals(expected, result);
    }

    @Order(6)
    public final void testGetAllActor() throws JsonProcessingException {
        List<Actor> ActorList = new ArrayList<>();
        List<Actor> allActorList = service.getAllActors();
        Actor Actor1 = new Actor(1, "Tom", "Cruise");
        Actor Actor2 = new Actor(2, "Angelina", "Jolie");
        Actor Actor3 = new Actor(3, "Brad", "Pitt");
        Actor Actor4 = new Actor(4, "George", "Clooney");
        Actor Actor5 = new Actor(5, "Dwayne", "Johnson");
        Actor Actor6 = new Actor(6, "Robert", "Downey");
        Actor Actor7 = new Actor(7, "Bradly", "Cooper");
        Actor Actor8 = new Actor(8, "Al", "Pacino");
        Actor Actor9 = new Actor(9, "Tom", "Holland");
        Actor Actor10 = new Actor(10, "Matt", "Damon");
        Actor Actor11 = new Actor(11, "Robert", "De Niro");
        Actor Actor12 = new Actor(12, "Jennifer", "Lawrence");
        Actor Actor13 = new Actor(13, "Jennifer", "Aniston");
        Actor Actor14 = new Actor(14, "Robert", "Williams");
        Actor Actor15 = new Actor(15, "Tom", "Hardy");
        Actor Actor16 = new Actor(16, "Scarlett", "Johansson");
        Actor Actor17 = new Actor(17, "Margot", "Robbie");
        Actor Actor18 = new Actor(18, "Johnny", "Deep");
        Actor Actor19 = new Actor(19, "Samuel", "Jackson");
        Actor Actor20 = new Actor(20, "Henry", "Ford");
        ActorList.add(Actor1);
        ActorList.add(Actor2);
        ActorList.add(Actor3);
        ActorList.add(Actor4);
        ActorList.add(Actor5);
        ActorList.add(Actor6);
        ActorList.add(Actor7);
        ActorList.add(Actor8);
        ActorList.add(Actor9);
        ActorList.add(Actor10);
        ActorList.add(Actor11);
        ActorList.add(Actor12);
        ActorList.add(Actor13);
        ActorList.add(Actor14);
        ActorList.add(Actor15);
        ActorList.add(Actor16);
        ActorList.add(Actor17);
        ActorList.add(Actor18);
        ActorList.add(Actor19);
        ActorList.add(Actor20);
        String expectedActorList = Utils.toJSON(ActorList);
        String resultActorList = Utils.toJSON(allActorList);
        assertEquals(expectedActorList, resultActorList);
    }

    @Order(7)
    protected void tearDown() {
        service = null;
    }
}
