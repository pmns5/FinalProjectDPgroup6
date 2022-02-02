package filmAPI.gateway;

import filmAPI.models.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class FilmDiscoveryGateway extends APIGateway {
    public FilmDiscoveryGateway() {
        super();
    }

    public void getFilms(HttpServletResponse res) {
        // Init variables
        List<Film> films;
        List<HomePageFilm> homePageFilms;

        // Start Queries
        try {
            films = filmInterface.getFilms();
            homePageFilms = extractDataFromAll(films);
            res.getWriter().print(Utils.toJSON(homePageFilms));
            res.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public void getFilmsPerGenre(HttpServletRequest req, HttpServletResponse res) {
        // Init variables
        List<Film> films;
        List<HomePageFilm> homePageFilms;
        String genre = req.getParameter("genre");

        // Start Queries
        try {
            if (genre != null) {
                films = filmInterface.getFilmsPerGenre(EnumGenre.valueOf(genre));
                homePageFilms = extractDataFromAll(films);
                res.getWriter().print(Utils.toJSON(homePageFilms));
                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public void getFilmDetails(HttpServletRequest req, HttpServletResponse res) {
        // Init variables
        Film film;
        List<Actor> actorList;
        List<Feedback> feedbackList;
        ReviewPageFilm reviewPageFilm;
        int id_film = Integer.parseInt(req.getParameter("id"));

        // Start Queries
        try {
            if (id_film > 0) {
                film = filmInterface.getFilm(id_film);
                actorList = getActors(castFilm.getByFilm(id_film));
                feedbackList = feedbackFilm.getByFilm(id_film);
                reviewPageFilm = new ReviewPageFilm(film, actorList, feedbackFilm.getAverageScore(id_film), feedbackList);
                res.getWriter().print(Utils.toJSON(reviewPageFilm));
                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
