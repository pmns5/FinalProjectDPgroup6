package provaAPI.gateway;

import provaAPI.models.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilmDetailsGateway extends APIGateway {

    public FilmDetailsGateway() {
        super();
    }

    public void getAll(HttpServletRequest req, HttpServletResponse res) {

        try {
            // Aggregate Data From Different Microservices
            int id_film = Integer.parseInt(req.getParameter("id"));
            Film film = filmInterface.getOneFilm(id_film);
            FilmDetails filmDetails = extractData(film);

            // No error, send Json
            res.getWriter().print(Utils.toJSON(filmDetails));
            res.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            // Request generated an error, send error
            res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }

    }

    // Private Utility for Requesting to the microservices the data associated to the current film
    private FilmDetails extractData(Film film) {
        int id_film = film.getId();
        List<Cast> cast = castFilm.getByFilm(id_film);
        List<Actor> actorsCast = new ArrayList<>();
        for (Cast c : cast) {
            actorsCast.add(actorFilm.getOneActor(c.getId_actor()));
        }
        List<Feedback> feedbacks = feedbackFilm.getByFilm(id_film);
        return new FilmDetails(film, actorsCast, feedbackFilm.getAverageScore(id_film), feedbacks);
    }


}
