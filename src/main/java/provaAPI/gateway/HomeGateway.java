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
            List<HomeFilm> homeFilms = extractData(films);

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
            List<HomeFilm> homeFilms = extractData(films);
            // No error, send Json
            res.getWriter().print(Utils.toJSON(homeFilms));
            res.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            // Request generated an error, send error
            res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }

    }

    // Private Utility for Requesting to the microservices the data associated to the current film list
    private List<HomeFilm> extractData(List<Film> films) {
        List<HomeFilm> homeFilms = new ArrayList<>();
        for (Film film : films) {
            int id_film = film.getId();
            List<Cast> cast = castFilm.getByFilm(id_film);
            List<Actor> actorsCast = new ArrayList<>();
            for (Cast c : cast) {
                actorsCast.add(actorFilm.getOneActor(c.getId_actor()));
            }
            homeFilms.add(new HomeFilm(film, actorsCast, feedbackFilm.getAverageScore(id_film)));
        }
        return homeFilms;
    }
}
