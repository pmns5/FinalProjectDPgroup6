package provaAPI.interfaces;

import provaAPI.gateway.APIGateway;
import provaAPI.models.Feedback;

import java.util.ArrayList;

public interface FeedbackFilm {

    boolean addFeedback(Feedback feedback);

    boolean editFeedback(Feedback feedback);

    boolean deleteFeedback(int id_film, int id_user);

    Feedback getOneFeedback(int id_film, int id_user);

    ArrayList<Feedback> getByFilm(int id_film);

    ArrayList<Feedback> getByUser(int id_user);

    Float getAverageScore(int id_film);

}
