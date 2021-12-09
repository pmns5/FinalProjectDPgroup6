package provaAPI.gateway;

import provaAPI.models.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeGateway extends APIGateway {

    public HomeGateway() {
        super();
    }

    // Extract ALL Film Data, Cast and Score
    public void getAll(HttpServletRequest req, HttpServletResponse res) throws IOException {

        try {
            // Aggregate Data From Different Microservices
            List<Film> films = filmInterface.getAllFilms();
            List<HomeFilm> homeFilms = extractAllData(films);

            // No error, send Json
            res.getWriter().print(Utils.toJSON(homeFilms));
            res.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            // Request generated an error, send error
            res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }

    }

    // Extract Film Data, Cast and Score according to the Specific Genre
    public void getPerGenre(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String genreStr = req.getParameter("genre");
        int genre = Integer.parseInt(genreStr);
        try {
            // Aggregate Data From Different Microservices
            List<Film> films = filmInterface.getByGenre(genre);
            List<HomeFilm> homeFilms = extractAllData(films);
            // No error, send Json
            res.getWriter().print(Utils.toJSON(homeFilms));
            res.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            // Request generated an error, send error
            res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }

    }

    public void getFilm(HttpServletRequest req, HttpServletResponse res) {
        String idFilmStr = req.getParameter("id");
        try {
            if (idFilmStr != null) {
                int idFilm = Integer.parseInt(idFilmStr);
                Film film = filmInterface.getOneFilm(idFilm);
                HomeFilm homeFilm = extractData(film);
                res.getWriter().print(Utils.toJSON(homeFilm));
                res.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }
    }

    // Private Utility for Requesting to the microservices the data associated to the current film list
    private List<HomeFilm> extractAllData(List<Film> films) {
        List<HomeFilm> homeFilms = new ArrayList<>();
        for (Film film : films) {
            homeFilms.add(extractData(film));
        }
        return homeFilms;
    }

    // Private Utility for Requesting to the microservices the data associated to the current film
    private HomeFilm extractData(Film film) {
        int id_film = film.getId();
        List<Cast> cast = castFilm.getByFilm(id_film);
        List<Actor> actorsCast = new ArrayList<>();
        for (Cast c : cast) {
            actorsCast.add(actorFilm.getOneActor(c.getId_actor()));
        }
        return new HomeFilm(film, actorsCast, feedbackFilm.getAverageScore(id_film));
    }
}
