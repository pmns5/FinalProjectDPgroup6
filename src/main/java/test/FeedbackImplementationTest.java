package test;

import com.fasterxml.jackson.core.JsonProcessingException;
import filmAPI.microservices.FeedbackImplementation;
import filmAPI.models.Feedback;
import filmAPI.models.Utils;
import junit.framework.TestCase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FeedbackImplementationTest extends TestCase {
    private FeedbackImplementation service;

    @Order(1)
    protected void setUp() {
        service = new FeedbackImplementation();
    }

    @Order(2)
    public final void testAddFeedback() {
        int id_user = 1;
        int id_film = 1;
        String comment = "Bel film";
        double score = 4.5;
        Feedback feedback = new Feedback(id_user, id_film, comment, (float) score);
        boolean result = service.addFeedback(feedback);
        assertTrue(result);
    }

    @Order(3)
    public final void testEditFeedback() {
        int id_user = 1;
        int id_film = 1;
        String comment = "Bellissimo film";
        double score = 5.0;
        Feedback feedback = new Feedback(id_user, id_film, comment, (float) score);
        boolean result = service.editFeedback(feedback);
        assertTrue(result);
    }

    @Order(5)
    public final void testGetByFilm() throws JsonProcessingException {
        int id_user = 1;
        int id_film = 1;
        String comment = "Bellissimo film";
        double score = 5.0;
        Feedback feedback = new Feedback(id_user, id_film, comment, (float) score);
        List<Feedback> expected = new ArrayList<>();
        expected.add(feedback);
        List<Feedback> result = service.getByFilm(id_film);
        result.get(0).setDate(null);
        assertEquals(Utils.toJSON(expected), Utils.toJSON(result));
    }

    @Order(6)
    public final void testGetByUser() throws JsonProcessingException {
        int id_user = 1;
        int id_film = 1;
        String comment = "Bellissimo film";
        double score = 5.0;
        Feedback feedback = new Feedback(id_user, id_film, comment, (float) score);
        List<Feedback> expected = new ArrayList<>();
        expected.add(feedback);
        List<Feedback> result = service.getByUser(id_user);
        result.get(0).setDate(null);
        assertEquals(Utils.toJSON(expected), Utils.toJSON(result));
    }

    @Order(7)
    public final void testGetAverageScore() {
        int id_film = 1;
        float result = service.getAverageScore(id_film);
        assertEquals((float) 5.0, result);
    }

    @Order(8)
    public final void testGetOneFeedback() throws JsonProcessingException {
        int id_user = 1;
        int id_film = 1;
        String comment = "Bellissimo film";
        double score = 5.0;
        Feedback expected = new Feedback(id_user, id_film, comment, (float) score);
        Feedback result = service.getFeedback(id_film, id_user);
        result.setDate(null);
        assertEquals(Utils.toJSON(expected), Utils.toJSON(result));
    }

    @Order(4)
    public final void testDeleteFeedback() {
        int id_user = 1;
        int id_film = 1;
        boolean result = service.deleteFeedback(id_film, id_user);
        assertTrue(result);
    }

    @Order(11)
    protected void tearDown() {
        service = null;
    }
}
