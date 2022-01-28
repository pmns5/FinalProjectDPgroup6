package filmAPI;

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

    public void main(String[] args) {
        junit.swingui.TestRunner.run(FeedbackImplementationTest.class);
    }

    @Order(1)
    protected void setUp() {
        //Service initialization
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
        List<Feedback> feedbackListExpected = new ArrayList<>();
        feedbackListExpected.add(feedback);
        String expected = Utils.toJSON(feedbackListExpected);
        List<Feedback> feedbackListResult = service.getByFilm(id_film);
        String result = Utils.toJSON(feedbackListResult);
        assertEquals(expected, result);
    }

    @Order(6)
    public final void testGetByUser() throws JsonProcessingException {
        int id_user = 1;
        int id_film = 1;
        String comment = "Bellissimo film";
        double score = 5.0;
        Feedback feedback = new Feedback(id_user, id_film, comment, (float) score);
        List<Feedback> feedbackListExpected = new ArrayList<>();
        feedbackListExpected.add(feedback);
        String expected = Utils.toJSON(feedbackListExpected);
        List<Feedback> feedbackListResult = service.getByUser(id_user);
        String result = Utils.toJSON(feedbackListResult);
        assertEquals(expected, result);
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
        Feedback feedbackExpected = new Feedback(id_user, id_film, comment, (float) score);
        String expected = Utils.toJSON(feedbackExpected);
        Feedback feedbackResult = service.getOneFeedback(id_film, id_user);
        String result = Utils.toJSON(feedbackResult);
        assertEquals(expected, result);
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
