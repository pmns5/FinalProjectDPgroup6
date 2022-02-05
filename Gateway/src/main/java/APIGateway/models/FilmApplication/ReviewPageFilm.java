package APIGateway.models.FilmApplication;

import APIGateway.EnumGenre;

import java.util.List;

public class ReviewPageFilm extends HomePageFilm {

    private List<Feedback> feedbackList;

    public ReviewPageFilm() {

    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }
}
