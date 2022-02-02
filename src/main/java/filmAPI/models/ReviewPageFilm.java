package filmAPI.models;

import java.util.List;

@SuppressWarnings("unused")
public record ReviewPageFilm(Film film, List<Actor> actors, Float avgScore, List<Feedback> feedback) {
}
