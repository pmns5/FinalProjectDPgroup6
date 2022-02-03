package models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "ReviewPageFilm")
@XmlAccessorType(XmlAccessType.NONE)
public class ReviewPageFilm {
    @XmlAttribute
    private Film film;
    @XmlAttribute
    private List<Actor> actors;
    @XmlAttribute
    private float avgScore;
    @XmlAttribute
    private List<Feedback> feedbackList;

    public ReviewPageFilm(){

    }

    public ReviewPageFilm(Film film, List<Actor> actors, float avgScore, List<Feedback> feedbackList) {
        this.film = film;
        this.actors = actors;
        this.avgScore = avgScore;
        this.feedbackList = feedbackList;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public float getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(float avgScore) {
        this.avgScore = avgScore;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }
}
