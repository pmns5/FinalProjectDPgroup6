package provaAPI.gateway;

import provaAPI.interfaces.ActorFilm;
import provaAPI.interfaces.CastFilm;
import provaAPI.interfaces.FeedbackFilm;
import provaAPI.interfaces.FilmInterface;
import provaAPI.microservices.ActorImplementation;
import provaAPI.microservices.CastImplementation;
import provaAPI.microservices.FeedbackImplementation;
import provaAPI.microservices.FilmImplementation;
import provaAPI.models.Actor;
import provaAPI.models.Cast;
import provaAPI.models.Film;
import provaAPI.models.HomePageFilm;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@Path("/ciao")
public class APIGateway {
    @GET
    public String hello(){
        return "CIAOOOOOOO";
    }

    @Resource
    ActorFilm actorFilm;

    @Resource
    FilmInterface filmInterface;

    @Resource
    CastFilm castFilm;

    @Resource
    FeedbackFilm feedbackFilm;

    public APIGateway() {
        this.actorFilm = new ActorImplementation();
        this.filmInterface = new FilmImplementation();
        this.castFilm = new CastImplementation();
        this.feedbackFilm = new FeedbackImplementation();
    }


    // Utilities
    // Private Utility for Requesting to the microservices the data associated to the current film
    public HomePageFilm extractData(Film film) {
        int id_film = film.getId();
        List<Cast> cast = castFilm.getByFilm(id_film);
        List<Actor> actorsCast = new ArrayList<>();
        for (Cast c : cast) {
            actorsCast.add(actorFilm.getOneActor(c.getId_actor()));
        }
        return new HomePageFilm(film, actorsCast, feedbackFilm.getAverageScore(id_film));
    }

    // Private Utility for Requesting to the microservices the data associated to the current film list
    public List<HomePageFilm> extractAllData(List<Film> films) {
        List<HomePageFilm> homePageFilms = new ArrayList<>();
        for (Film film : films) {
            homePageFilms.add(extractData(film));
        }
        return homePageFilms;
    }

    // Utility that transforms a List of Cast in a list of Actors
    public List<Actor> getActors(List<Cast> castList) {
        List<Actor> actorList = new ArrayList<>();
        for (Cast c : castList) actorList.add(actorFilm.getOneActor(c.getId_actor()));
        return actorList;
    }

}
