package APIGateway.models.FilmApplication;

import APIGateway.EnumGenre;

import java.util.List;

public class ReviewPageFilm extends HomePageFilm {

    private List<Feedback> feedbackList;

    public ReviewPageFilm() {

    }

    public ReviewPageFilm(int id, String title, EnumGenre genre, String plot, String trailer, byte[] poster,
                          List<Actor> actors, float avgScore, List<Feedback> feedbackList) {
        super(id, title, genre, plot, trailer, poster, actors, avgScore);
        this.feedbackList = feedbackList;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }
}
