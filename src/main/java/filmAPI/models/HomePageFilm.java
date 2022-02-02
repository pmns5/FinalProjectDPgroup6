package filmAPI.models;

import java.util.List;

@SuppressWarnings("unused")
public record HomePageFilm(Film film, List<Actor> actors, Float avgScore) {
}
