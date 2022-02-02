package filmAPI.gateway;

import filmAPI.models.Feedback;
import filmAPI.models.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FeedbackGateway extends APIGateway {
    public FeedbackGateway() {
        super();
    }

    public void addFeedback(HttpServletRequest req, HttpServletResponse res) {
        // Init variables
        Feedback feedback_to_add;
        int id_film = Integer.parseInt(req.getParameter("id_film"));
        int id_user = Integer.parseInt(req.getParameter("id_user"));
        float score = Float.parseFloat(req.getParameter("score"));
        String comment = req.getParameter("comment");

        // Start Queries
        try {
            if (id_film > 0 && id_user > 0 && score > 0.0 && comment != null) {
                feedback_to_add = new Feedback(id_user, id_film, comment, score);
                if (feedbackFilm.addFeedback(feedback_to_add)) {
                    res.setStatus(HttpServletResponse.SC_OK);
                } else {
                    res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
                }
            } else {
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void editFeedback(HttpServletRequest req, HttpServletResponse res) {
        // Init variables
        Feedback new_feedback;
        int id_film = Integer.parseInt(req.getParameter("id_film"));
        int id_user = Integer.parseInt(req.getParameter("id_user"));
        float score = Float.parseFloat(req.getParameter("score"));
        String comment = req.getParameter("comment");

        // Start Queries
        try {
            if (id_film > 0 && id_user > 0 && score > 0.0 && comment != null) {
                new_feedback = new Feedback(id_user, id_film, comment, score);
                if (feedbackFilm.editFeedback(new_feedback)) {
                    res.setStatus(HttpServletResponse.SC_OK);
                } else {
                    res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
                }
            } else {
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void deleteFeedback(HttpServletRequest req, HttpServletResponse res) {
        // Init variables
        int id_film = Integer.parseInt(req.getParameter("id_film"));
        int id_user = Integer.parseInt(req.getParameter("id_user"));

        // Start Queries
        try {
            if (id_film > 0 && id_user > 0) {
                if (feedbackFilm.deleteFeedback(id_film, id_user)) {
                    res.setStatus(HttpServletResponse.SC_OK);
                } else {
                    res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
                }
            } else {
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void getFeedback(HttpServletRequest req, HttpServletResponse res) {
        // Init variables
        Feedback feedback_to_send;
        int id_film = Integer.parseInt(req.getParameter("id_film"));
        int id_user = Integer.parseInt(req.getParameter("id_user"));

        // Start Queries
        try {
            if (id_film > 0 && id_user > 0) {
                feedback_to_send = feedbackFilm.getFeedback(id_film, id_user);
                res.getWriter().print(Utils.toJSON(feedback_to_send));
                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
