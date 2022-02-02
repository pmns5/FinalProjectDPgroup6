package filmAPI.gateway;

import filmAPI.interfaces.ActorFilm;
import filmAPI.interfaces.CastFilm;
import filmAPI.interfaces.FeedbackFilm;
import filmAPI.interfaces.FilmInterface;
import filmAPI.microservices.ActorImplementation;
import filmAPI.microservices.CastImplementation;
import filmAPI.microservices.FeedbackImplementation;
import filmAPI.microservices.FilmImplementation;
import filmAPI.models.Actor;
import filmAPI.models.Cast;
import filmAPI.models.Film;
import filmAPI.models.HomePageFilm;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class APIGateway {
    @Resource
    ActorFilm actorFilm;
    @Resource
    FilmInterface filmInterface;
    @Resource
    CastFilm castFilm;
    @Resource
    FeedbackFilm feedbackFilm;

    /**
     * Initialization gateway
     */
    public APIGateway() {
        this.actorFilm = new ActorImplementation();
        this.filmInterface = new FilmImplementation();
        this.castFilm = new CastImplementation();
        this.feedbackFilm = new FeedbackImplementation();
    }

    /**
     * Method to extract data from a Film object
     *
     * @param film: Film object for extracting data
     * @return HomePageFilm object
     */
    public HomePageFilm extractDataFrom(Film film) {
        int id_film = film.getId();
        List<Cast> cast = castFilm.getByFilm(id_film);
        List<Actor> actorsCast = new ArrayList<>();
        for (Cast c : cast) {
            actorsCast.add(actorFilm.getActor(c.getId_actor()));
        }
        return new HomePageFilm(film, actorsCast, feedbackFilm.getAverageScore(id_film));
    }

    /**
     * Method to extract data from a list of Film objects
     *
     * @param films: list of Film objects for extracting all data
     * @return list of HomePageFilm objects
     */
    public List<HomePageFilm> extractDataFromAll(List<Film> films) {
        List<HomePageFilm> homePageFilms = new ArrayList<>();
        for (Film film : films) {
            homePageFilms.add(extractDataFrom(film));
        }
        return homePageFilms;
    }

    /**
     * Method to extract a list of actors from a cast
     *
     * @param castList list of Cast objects for extracting all actors
     * @return list of Actor objects
     */
    public List<Actor> getActors(List<Cast> castList) {
        List<Actor> actorList = new ArrayList<>();
        for (Cast c : castList) actorList.add(actorFilm.getActor(c.getId_actor()));
        return actorList;
    }
}
