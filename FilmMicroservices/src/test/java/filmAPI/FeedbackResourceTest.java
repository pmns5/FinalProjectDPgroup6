package filmAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import filmAPI.microservices.beans.Feedback;
import filmAPI.microservices.resources.FeedbackResource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackResourceTest {
    private FeedbackResource service;

    @BeforeEach
    protected void setUp() {
        service = new FeedbackResource();
    }

    @Test
    public final void testAddFeedback() throws SQLException {
        Feedback feedback = new Feedback(1, 1, "Bel film", (float) 4.5, null);
        Assertions.assertTrue(service.addFeedback(feedback));
    }

    @Test
    public final void testEditFeedback() throws SQLException {
        Feedback feedback = new Feedback(1, 1, "Bellissimo film", (float) 5.0, null);
        Assertions.assertTrue(service.editFeedback(feedback));
    }

    @Test
    public final void testGetFeedback() throws SQLException, JsonProcessingException {
        Feedback expected = new Feedback(1, 1, "Bellissimo film", (float) 5.0, null);
        Feedback result = service.getFeedback(1, 1);
        result.setDate(null);
        Assertions.assertEquals(Util.toJSON(expected), Util.toJSON(result));
    }

    @Test
    public final void testGetFeedbackByFilm() throws SQLException, JsonProcessingException {
        List<Feedback> expected = new ArrayList<>();
        expected.add(new Feedback(1, 1, "Bellissimo film", (float) 5.0, null));
        List<Feedback> result = service.getFeedbackByFilm(1);
        result.get(0).setDate(null);
        Assertions.assertEquals(Util.toJSON(expected), Util.toJSON(result));
    }

    @Test
    public final void testGetFeedbackByUser() throws SQLException, JsonProcessingException {
        List<Feedback> expected = new ArrayList<>();
        expected.add(new Feedback(1, 1, "Bellissimo film", (float) 5.0, null));
        List<Feedback> result = service.getFeedbackByUser(1);
        result.get(0).setDate(null);
        Assertions.assertEquals(Util.toJSON(expected), Util.toJSON(result));
    }

    @Test
    public final void testDeleteFeedback() throws SQLException {
        Assertions.assertTrue(service.deleteFeedback(1, 1));
    }

    @AfterEach
    protected void tearDown() {
        service = null;
    }
}
