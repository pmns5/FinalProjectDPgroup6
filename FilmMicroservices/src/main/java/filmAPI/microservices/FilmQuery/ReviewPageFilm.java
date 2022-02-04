package filmAPI.microservices.FilmQuery;

import filmAPI.EnumGenre;
import filmAPI.microservices.ActorManagement.Actor;
import filmAPI.microservices.FeedbackManagement.Feedback;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "ReviewPageFilm")
@XmlAccessorType(XmlAccessType.NONE)
public class ReviewPageFilm extends HomePageFilm{

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
