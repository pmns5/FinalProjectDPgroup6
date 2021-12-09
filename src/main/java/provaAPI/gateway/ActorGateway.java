package provaAPI.gateway;


import provaAPI.models.Actor;
import provaAPI.models.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ActorGateway extends APIGateway {

    public ActorGateway() {
        super();
    }

    public void addActor(HttpServletRequest req, HttpServletResponse res) {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        if (name != null && surname != null) {
            Actor actor = new Actor(-1, name, surname);
            if (actorFilm.addActor(actor)) {
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

    public void editActor(HttpServletRequest req, HttpServletResponse res) {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        if (id != null && name != null && surname != null) {
            Actor actor = new Actor(Integer.parseInt(id), name, surname);
            if (actorFilm.editActor(actor)) {
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

    public void viewActor(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String id = req.getParameter("id");
        if (id != null) {
            // Return single role by id
            Actor actor = actorFilm.getOneActor(Integer.parseInt(id));
            if (actor != null) {
                res.getWriter().print(Utils.toJSON(actor));
                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        } else {
            // Return all role list
            List<Actor> actors = actorFilm.getAllActors();
            if (actors != null) {
                // No error, send Json
                res.getWriter().print(Utils.toJSON(actors));
                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                // Request generated an error, send error
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        }
    }

    public void deleteActor(HttpServletRequest req, HttpServletResponse res) {
        String id = req.getParameter("id");
        if (id != null) {
            // Do delete
            if (actorFilm.deleteActor(Integer.parseInt(id))) {
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
