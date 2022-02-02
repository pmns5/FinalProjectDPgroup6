package filmAPI.gateway;

import filmAPI.models.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

public class FilmManagementGateway extends APIGateway {
    public FilmManagementGateway() {
        super();
    }

    public void addFilm(HttpServletRequest req, HttpServletResponse res) {
        // Init variables
        int id_film;
        String title = req.getParameter("title");
        String plot = req.getParameter("plot");
        String genre = req.getParameter("genre");
        String trailer = req.getParameter("trailer");
        byte[] poster = null;
        String[] actors = req.getParameterValues("actors");
        try {
            Part part = req.getPart("poster");
            poster = part.getInputStream().readAllBytes();
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        // Start Queries
        if (title != null && plot != null && genre != null && trailer != null && poster != null) {
            Film film = new Film(-1, title, plot, EnumGenre.valueOf(genre), trailer, poster);
            id_film = filmInterface.addFilm(film);
            if (id_film > 0 && castFilm.addCast(id_film, actors)) {
                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        } else {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void editFilm(HttpServletRequest req, HttpServletResponse res) {
        // Init variables
        Film newFilm;
        int id_film = Integer.parseInt(req.getParameter("id"));
        String title = req.getParameter("title");
        String plot = req.getParameter("plot");
        String genre = req.getParameter("genre");
        String trailer = req.getParameter("trailer");
        byte[] poster = null;
        String[] actors = req.getParameterValues("actors");
        try {
            Part part = req.getPart("poster");
            poster = part.getInputStream().readAllBytes();
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        // Start Queries
        try {
            if (id_film > 0 && title != null && plot != null && genre != null && trailer != null && poster != null) {
                newFilm = new Film(id_film, title, plot, EnumGenre.valueOf(genre), trailer, poster);
                if (filmInterface.editFilm(newFilm) && castFilm.editCast(id_film, actors)) {
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

    public void deleteFilm(HttpServletRequest req, HttpServletResponse res) {
        // Init variables
        int id_film = Integer.parseInt(req.getParameter("id"));

        // Start Queries
        try {
            if (id_film > 0) {
                if (filmInterface.deleteFilm(id_film) && castFilm.deleteCast(id_film)) {
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

    public void getFilm(HttpServletRequest req, HttpServletResponse res) {
        // Init variables
        Film film;
        List<Cast> castList;
        List<Actor> actorList = new ArrayList<>();
        HomePageFilm homePageFilm;
        int idFilm = Integer.parseInt(req.getParameter("id"));

        // Start Queries
        try {
            if (idFilm > 0) {
                film = filmInterface.getFilm(idFilm);
                castList = castFilm.getByFilm(idFilm);
                for (Cast c : castList)
                    actorList.add(actorFilm.getActor(c.getId_actor()));
                homePageFilm = new HomePageFilm(film, actorList, feedbackFilm.getAverageScore(idFilm));
                res.getWriter().print(Utils.toJSON(homePageFilm));
                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
