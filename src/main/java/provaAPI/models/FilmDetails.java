package provaAPI.models;

import java.util.List;

public class FilmDetails extends HomeFilm {

    private final List<Feedback> feedbackList;

    public FilmDetails(Film film, List<Actor> actors, Float avgScore, List<Feedback> feedbackList) {
        super(film, actors, avgScore);
        this.feedbackList = feedbackList;
    }
}
