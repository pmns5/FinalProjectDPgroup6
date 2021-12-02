package newBackEnd.servlets;

import newBackEnd.dbQueries.ActorsDecorator;
import newBackEnd.dbQueries.MySQLDb;
import newBackEnd.models.Actor;
import newBackEnd.models.JsonUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Servlet", value = {"/actors", "/add-actor", "/delete-actor", "/edit-actor"})
public class ActorsServlet extends HttpServlet {
    private ActorsDecorator db;

    @Override
    public void init() {
        db = new ActorsDecorator(new MySQLDb());
        db.connect();
    }

    @Override
    public void destroy() {
        db.disconnect();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = ServletUtil.getRequestPath(request);
        // Add role
        if ("/add-actor".equals(path)) {
            create(request, response);
            // Delete role data
        } else if ("/edit-actor".equals(path)) {
            edit(request, response);
        }
        response.flushBuffer();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = ServletUtil.getRequestPath(request);
        // Get role data
        if ("/actors".equals(path)) {
            view(request, response);
            // Delete a role
        } else if ("/delete-actor".equals(path)) {
            delete(request, response);
        }
        response.flushBuffer();
    }

    /**
     * Check the entered parameters and, if they are correct, add them. Otherwise it returns an error to the client.
     *
     * @param req: the HttpServletRequest from which to get the parameters;
     * @param res: the HttpServletResponse to return the response to.
     * @throws IOException if a communication error occurs with the client.
     */
    private void view(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // Set response type JSON
        res.setContentType("application/json");
        String id = req.getParameter("id");
        if (id != null) {
            // Return single role by id
            renderOne(res, Long.parseLong(id));
        } else {
            // Return all role list
            renderAll(res);
        }
    }

    /**
     * Returns the requested object in JSON format to the client.
     *
     * @param res: the HttpServletResponse to return the response to.
     * @param id:  the id of the requested object.
     * @throws IOException if a communication error occurs with the client.
     */
    private void renderOne(HttpServletResponse res, long id) throws IOException {
        Actor actor = db.getActor(id);
        if (actor != null) {
            // No error, send Json
            res.getWriter().print(actor.toJSON());
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            // Request generated an error, send error
            res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }
    }

    /**
     * Returns the list of requested objects in JSON format to the client.
     *
     * @param res: the HttpServletResponse to return the response to.
     * @throws IOException if a communication error occurs with the client.
     */
    private void renderAll(HttpServletResponse res) throws IOException {
        List<Actor> actors = db.getActors();
        if (actors != null) {
            // No error, send Json
            res.getWriter().print(JsonUtil.toJson(actors));
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            // Request generated an error, send error
            res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }
    }

    /**
     * Check the entered parameters and, if they are correct, edit them. Otherwise it returns an error to the client.
     *
     * @param req: the HttpServletRequest from which to get the parameters;
     * @param res: the HttpServletResponse to return the response to.
     */
    private void edit(HttpServletRequest req, HttpServletResponse res) {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        if (id != null && name != null && surname != null) {
            if (db.editActor(Long.parseLong(id), name, surname)) {
                // edit success
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

    /**
     * Check the entered parameters and, if they are correct, delete them. Otherwise it returns an error to the client.
     *
     * @param req: the HttpServletRequest from which to get the parameters;
     * @param res: the HttpServletResponse to return the response to.
     */
    private void delete(HttpServletRequest req, HttpServletResponse res) {
        String id = req.getParameter("id");
        if (id != null) {
            // Do delete
            if (db.deleteActor(Long.parseLong(id))) {
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

    /**
     * Check the entered parameters and, if they are correct, create them. Otherwise it returns an error to the client.
     *
     * @param req: the HttpServletRequest from which to get the parameters;
     * @param res: the HttpServletResponse to return the response to.
     */
    private void create(HttpServletRequest req, HttpServletResponse res) {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        if (name != null && surname != null) {
            if (db.addActor(name, surname)) {
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
}
