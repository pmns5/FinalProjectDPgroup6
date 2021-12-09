package provaAPI.gateway;


import provaAPI.interfaces.ActorFilm;
import provaAPI.interfaces.CastFilm;
import provaAPI.interfaces.FeedbackFilm;
import provaAPI.interfaces.FilmInterface;
import provaAPI.microservices.ActorImplementation;
import provaAPI.microservices.CastImplementation;
import provaAPI.microservices.FeedbackImplementation;
import provaAPI.microservices.FilmImplementation;

import javax.annotation.Resource;

public class APIGateway {

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
}
