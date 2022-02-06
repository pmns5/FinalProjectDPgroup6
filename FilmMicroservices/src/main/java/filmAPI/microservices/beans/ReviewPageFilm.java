package filmAPI.microservices.beans;

import filmAPI.EnumGenre;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Bean class representing the Film object containing data related to the film and the average score
 * given by feedbacks together with all the related feedbacks
 */
@XmlRootElement(name = "ReviewPageFilm")
@XmlAccessorType(XmlAccessType.NONE)
public class ReviewPageFilm extends HomePageFilm {

    @XmlAttribute
    private List<Feedback> feedbackList;

    public ReviewPageFilm(int id, String title, EnumGenre genre, String plot, String trailer, byte[] poster,
                          List<Actor> actors, float avgScore, List<Feedback> feedbackList) {
        super(id, title, genre, plot, trailer, poster, actors, avgScore);
        this.feedbackList = feedbackList;
    }

    public ReviewPageFilm() {

    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }
}
