package filmAPI.gateway;

import filmAPI.models.Actor;
import filmAPI.models.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ActorGateway extends APIGateway {
    public ActorGateway() {
        super();
    }

    public void addActor(HttpServletRequest req, HttpServletResponse res) {
        // Init variables
        Actor actor_to_add;
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");

        // Start Queries
        if (name != null && surname != null) {
            actor_to_add = new Actor(-1, name, surname);
            if (actorFilm.addActor(actor_to_add)) {
                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        } else {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void editActor(HttpServletRequest req, HttpServletResponse res) {
        // Init variables
        Actor new_actor;
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");

        // Start Queries
        if (id > 0 && name != null && surname != null) {
            new_actor = new Actor(id, name, surname);
            if (actorFilm.editActor(new_actor)) {
                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        } else {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void deleteActor(HttpServletRequest req, HttpServletResponse res) {
        // Init variables
        int id = Integer.parseInt(req.getParameter("id"));

        // Start Queries
        if (id > 0) {
            if (actorFilm.deleteActor(id)) {
                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        } else {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void getActor(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // Init variables
        Actor actor_to_send;
        int id = Integer.parseInt(req.getParameter("id"));

        // Start Queries
        if (id > 0) {
            actor_to_send = actorFilm.getActor(id);
            res.getWriter().print(Utils.toJSON(actor_to_send));
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }
    }

    public void getActors(HttpServletResponse res) {
        // Init variables
        List<Actor> actors;

        // Start Queries
        try {
            actors = actorFilm.getActors();
            res.getWriter().print(Utils.toJSON(actors));
            res.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
