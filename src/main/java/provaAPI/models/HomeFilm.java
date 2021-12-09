package provaAPI.models;

import java.util.List;

public class HomeFilm extends Film {

    List<Actor> actors;
    Float avgScore;

    public HomeFilm(Film film, List<Actor> actors, Float avgScore) {
        super(film.getId(), film.getTitle(), film.getPlot(), film.getGenre(), film.getTrailer(), film.getPoster());
        this.actors = actors;
        this.avgScore = avgScore;
    }

}
