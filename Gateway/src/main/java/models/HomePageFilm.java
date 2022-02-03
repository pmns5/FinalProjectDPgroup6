package models;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.List;


@XmlRootElement(name = "HomePageFilm")
@XmlAccessorType(XmlAccessType.NONE)
public class HomePageFilm {
    @XmlAttribute
    private Film film;
    @XmlAttribute
    private List<Actor> actors;
    @XmlAttribute
    private float avgScore;

    public HomePageFilm() {

    }

    public HomePageFilm(Film film, List<Actor> actors, float avgScore) {
        this.film = film;
        this.actors = actors;
        this.avgScore = avgScore;
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

}
