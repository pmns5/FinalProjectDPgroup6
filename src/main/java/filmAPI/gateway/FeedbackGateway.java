package filmAPI.gateway;

import filmAPI.models.Feedback;
import filmAPI.models.FeedbackUser;
import filmAPI.models.User;
import filmAPI.models.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FeedbackGateway extends APIGateway {
    public FeedbackGateway() {
        super();
    }

    public void addFeedback(HttpServletRequest req, HttpServletResponse res) {
        String id_filmStr = req.getParameter("id_film");
        String id_userStr = req.getParameter("id_user");
        String scoreStr = req.getParameter("score");
        String comment = req.getParameter("comment");
        if (id_filmStr != null && id_userStr != null && scoreStr != null && comment != null) {
            int id_film = Integer.parseInt(id_filmStr);
            int id_user = Integer.parseInt(id_userStr);
            float score = Float.parseFloat(scoreStr);
            Feedback feedback = new Feedback(id_user, id_film, comment, score);
            if (feedbackFilm.addFeedback(feedback)) {
                // add success
                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                // Request generated an error, send error
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        } else {
            // Error on parameters
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void editFeedback(HttpServletRequest req, HttpServletResponse res) {
        String id_filmStr = req.getParameter("id_film");
        String id_userStr = req.getParameter("id_user");
        String scoreStr = req.getParameter("score");
        String comment = req.getParameter("comment");
        if (id_filmStr != null && id_userStr != null && scoreStr != null && comment != null) {
            int id_film = Integer.parseInt(id_filmStr);
            int id_user = Integer.parseInt(id_userStr);
            float score = Float.parseFloat(scoreStr);
            Feedback feedback = new Feedback(id_user, id_film, comment, score);
            if (feedbackFilm.editFeedback(feedback)) {
                // add success
                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                // Request generated an error, send error
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        } else {
            // Error on parameters
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void viewFeedback(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String id_filmStr = req.getParameter("id_film");
        String id_userStr = req.getParameter("id_user");
        if (id_filmStr != null && id_userStr != null) {
            int id_film = Integer.parseInt(id_filmStr);
            int id_user = Integer.parseInt(id_userStr);
            Feedback feedback = feedbackFilm.getOneFeedback(id_film, id_user);
            User user = userInterface.getUser(id_user);
            if (feedback != null && user != null) {
                res.getWriter().print(Utils.toJSON(new FeedbackUser(feedback, user)));
                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        } else {
            // Request generated an error, send error
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void deleteFeedback(HttpServletRequest req, HttpServletResponse res) {
        String id_filmStr = req.getParameter("id_film");
        String id_userStr = req.getParameter("id_user");
        if (id_filmStr != null && id_userStr != null) {
            int id_film = Integer.parseInt(id_filmStr);
            int id_user = Integer.parseInt(id_userStr);
            // Do delete
            if (feedbackFilm.deleteFeedback(id_film, id_user)) {
                // Send success
                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                // Request generated an error, send error
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        } else {
            // No id passed as parameter
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
