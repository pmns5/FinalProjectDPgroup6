package models;

import java.util.List;

public record HomePageFilm(Film film, List<Actor> actors, Float avgScore) {
}
