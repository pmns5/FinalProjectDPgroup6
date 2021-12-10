package provaAPI.gateway;

import provaAPI.interfaces.FeedbackFilm;
import provaAPI.models.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FeedbackGateway extends APIGateway {

    public FeedbackGateway() {
        super();
    }

    public void addFeedback(HttpServletRequest req, HttpServletResponse res) {
        Integer id_film = Integer.parseInt(req.getParameter("id_film"));
        Integer id_user = Integer.parseInt(req.getParameter("id_user"));
        float score = Float.parseFloat(req.getParameter("score"));
        String comment = req.getParameter("comment");
        if (id_film != null && id_user != null) {
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
        Integer id_film = Integer.parseInt(req.getParameter("id_film"));
        Integer id_user = Integer.parseInt(req.getParameter("id_user"));
        Float score = Float.parseFloat(req.getParameter("score"));
        String comment = req.getParameter("comment");
        if (id_film != null && id_user != null && score != null && comment != null) {
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
        Integer id_film = Integer.parseInt(req.getParameter("id_film"));
        Integer id_user = Integer.parseInt(req.getParameter("id_user"));
        if (id_film != null && id_user != null) {
            // Return single role by id
            Feedback feedback = feedbackFilm.getOneFeedback(id_film, id_user);
            if (feedback != null) {
                res.getWriter().print(Utils.toJSON(feedback));
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
        Integer id_film = Integer.parseInt(req.getParameter("id_film"));
        Integer id_user = Integer.parseInt(req.getParameter("id_user"));
        Float score = Float.parseFloat(req.getParameter("score"));
        String comment = req.getParameter("comment");
        if (id_film != null && id_user != null && score != null && comment != null) {
            Feedback feedback = new Feedback(id_user, id_film, comment, score);
            // Do delete
            if (feedbackFilm.deleteFeedback(feedback)) {
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

    public void getOneFeedbackFromFilm(HttpServletRequest req, HttpServletResponse res) {

        try {
            // Aggregate Data From Different Microservices
            int id_film = Integer.parseInt(req.getParameter("id_film"));
            int id_user = Integer.parseInt(req.getParameter("id_user"));
            Feedback feedback = feedbackFilm.getOneFeedback(id_film, id_user);

            // No error, send Json
            res.getWriter().print(Utils.toJSON(feedback));
            res.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            // Request generated an error, send error
            res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }

    }
}
