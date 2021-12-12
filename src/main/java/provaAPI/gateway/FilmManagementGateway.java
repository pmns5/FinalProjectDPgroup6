package provaAPI.gateway;


import provaAPI.models.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilmManagementGateway extends APIGateway {

    public FilmManagementGateway() {
        super();
    }

    public void addFilm(HttpServletRequest req, HttpServletResponse res) {
        // Extract Data From Request
        String title = req.getParameter("title");
        String plot = req.getParameter("plot");
        String genreStr = req.getParameter("genre");
        String trailer = req.getParameter("trailer");
        byte[] poster = null;
        String[] actors = req.getParameterValues("actors");
        try {
            Part part = req.getPart("poster");
            poster = part.getInputStream().readAllBytes();
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }

        trailer ="";
        // Recall Microservices for Completion
        if (title != null && plot != null && genreStr != null && trailer != null && poster != null) {
            int genre = Integer.parseInt(genreStr);
            Film film = new Film(-1, title, plot, genre, trailer, poster);
            int id_film = filmInterface.addFilm(film);
            if (id_film != -1 && castFilm.addCast(id_film, actors)) {
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

    public void editFilm(HttpServletRequest req, HttpServletResponse res) {
        // Extract Data from Request
        int id = Integer.parseInt(req.getParameter("id"));
        String title = req.getParameter("title");
        String plot = req.getParameter("plot");
        int genre = Integer.parseInt(req.getParameter("genre"));
        String trailer = req.getParameter("trailer");
        byte[] poster = null;
        String[] actors = req.getParameterValues("actors");
        try {
            Part part = req.getPart("poster");
            poster = part.getInputStream().readAllBytes();
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }

        trailer ="";
        // Recall Microservices for Completion
        if (title != null && plot != null && genre != -1 && trailer != null && poster != null) {
            Film film = new Film(id, title, plot, genre, trailer, poster);
            int id_film = filmInterface.editFilm(film);
            if (id_film != -1 && castFilm.editCast(id_film, actors)) {
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

    public void deleteFilm(HttpServletRequest req, HttpServletResponse res) {
        String id = req.getParameter("id");
        if (id != null) {
            int id_film = Integer.parseInt(id);
            // Do delete
            if (filmInterface.deleteFilm(id_film) && castFilm.deleteCast(id_film)) {
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

    public void getFilm(HttpServletRequest req, HttpServletResponse res) {
        String idFilmStr = req.getParameter("id");
        try {
            if (idFilmStr != null) {
                int idFilm = Integer.parseInt(idFilmStr);
                Film film = filmInterface.getOneFilm(idFilm);
                List<Cast> castList = castFilm.getByFilm(idFilm);
                List<Actor> actorList = new ArrayList<>();
                for (Cast c : castList) actorList.add(actorFilm.getOneActor(c.getId_actor()));
                HomePageFilm homePageFilm = new HomePageFilm(film, actorList, feedbackFilm.getAverageScore(idFilm));
                res.getWriter().print(Utils.toJSON(homePageFilm));
                res.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }
    }

}
