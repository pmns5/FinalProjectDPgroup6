package APIGateway.models.DataAggregation;

import APIGateway.models.FilmApplication.HomePageFilm;
import APIGateway.models.FilmApplication.ReviewPageFilm;

import java.util.List;

public class FilmDetailsRecord extends HomePageFilm {
    private List<FeedbackUsername> feedbacks;

    public FilmDetailsRecord(ReviewPageFilm reviewPageFilm, List<FeedbackUsername> feedbacks) {
        super(reviewPageFilm.getId(), reviewPageFilm.getTitle(), reviewPageFilm.getGenre(), reviewPageFilm.getPlot(),
                reviewPageFilm.getTrailer(), reviewPageFilm.getPoster(), reviewPageFilm.getActors(),
                reviewPageFilm.getAvgScore());
        this.feedbacks = feedbacks;
    }

    public List<FeedbackUsername> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbackUsername> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
