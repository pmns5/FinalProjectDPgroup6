package newBackEnd.servlets;

import newBackEnd.dbQueries.ActorsDecorator;
import newBackEnd.dbQueries.FilmDecorator;
import newBackEnd.dbQueries.MySQLDb;
import newBackEnd.models.Actor;
import newBackEnd.models.Film;
import newBackEnd.models.JsonUtil;
import newBackEnd.models.Model;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "TestFilmServlet", value = {"/add-film", "/edit-film", "/delete-film", "/films"})
@MultipartConfig(maxFileSize = 16177215)
public class TestFilmServlet extends HttpServlet {

    private FilmDecorator db;

    @Override
    public void init() {
        db = new FilmDecorator(new MySQLDb());
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
        if ("/add-film".equals(path)) {
            create(request, response);
            // Delete role data
        } else if ("/edit-film".equals(path)) {
            edit(request, response);
        }
        response.flushBuffer();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = ServletUtil.getRequestPath(request);
        // Get role data
        if ("/films".equals(path)) {
            view(request, response);
            // Delete a role
        } else if ("/delete-film".equals(path)) {
            delete(request, response);
        }
        response.flushBuffer();
    }

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

    private void renderAll(HttpServletResponse res) throws IOException {
        List<Film> films = db.getFilms();
        if (films != null) {
            // No error, send Json
            res.getWriter().print(JsonUtil.toJson(films));
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            // Request generated an error, send error
            res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }
    }

    private void renderOne(HttpServletResponse res, long id) throws IOException {
        Film film = db.getFilm(id);
        if (film != null) {
            // No error, send Json
            res.getWriter().print(film.toJSON());
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            // Request generated an error, send error
            res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) {
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) {
        String title = req.getParameter("title");
        String plot = req.getParameter("plot");
        String genre = req.getParameter("genre");
        String[] actors = req.getParameterValues("actors");


        byte[] image = new byte[0];
        try {
            Part filePart = req.getPart("poster");
            image = filePart.getInputStream().readAllBytes();
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }


        if (title != null && plot != null && actors != null && image != null) {
            if (db.addFilm(title, plot, genre, image, actors)) {
                // add success
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                // Request generated an error, send error
                resp.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        } else {
            // Error on parameters
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
