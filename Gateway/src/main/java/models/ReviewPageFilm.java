package models;

import java.util.List;

public record ReviewPageFilm(Film film, List<Actor> actors, Float avgScore, List<FeedbackUser> feedbackList) {
}
