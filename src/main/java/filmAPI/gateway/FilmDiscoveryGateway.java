package filmAPI.gateway;

import filmAPI.models.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class FilmDiscoveryGateway extends APIGateway {
    public FilmDiscoveryGateway() {
        super();
    }

    // Extract ALL Film Data, Cast and Score
    public void getAll(HttpServletResponse res) {
        try {
            // Aggregate Data From Different Microservices
            List<Film> films = filmInterface.getAllFilms();
            List<HomePageFilm> homePageFilms = extractAllData(films);
            // No error, send Json
            res.getWriter().print(Utils.toJSON(homePageFilms));
            res.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            // Request generated an error, send error
            res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }
    }

    // Extract Film Data, Cast and Score according to the Specific Genre
    public void getPerGenre(HttpServletRequest req, HttpServletResponse res) {
        String genre = req.getParameter("genre");
        try {
            // Aggregate Data From Different Microservices
            List<Film> films = filmInterface.getByGenre(EnumGenre.valueOf(genre));
            List<HomePageFilm> homePageFilms = extractAllData(films);
            // No error, send Json
            res.getWriter().print(Utils.toJSON(homePageFilms));
            res.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            // Request generated an error, send error
            res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }
    }

    //Extract one Film Data, Cast, Score and all feedbacks associated
    public void getFilmDataAndFeedbacks(HttpServletRequest req, HttpServletResponse res) {
        try {
            // Aggregate Data From Different Microservices
            int id_film = Integer.parseInt(req.getParameter("id"));
            Film film = filmInterface.getOneFilm(id_film);
            List<Actor> actorList = getActors(castFilm.getByFilm(id_film));
            List<Feedback> feedbacks = feedbackFilm.getByFilm(id_film);
            List<FeedbackUser> feedbackUsers = new ArrayList<>();
            for(Feedback f :feedbacks)
                feedbackUsers.add(new FeedbackUser(f, userInterface.getUser(f.getId_user())));

            // Construct Output
            ReviewPageFilm reviewPageFilm = new ReviewPageFilm(film, actorList, feedbackFilm.getAverageScore(id_film)
                    , feedbackUsers);
            // No error, send Json
            res.getWriter().print(Utils.toJSON(reviewPageFilm));
            res.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            // Request generated an error, send error
            res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }
    }
}
