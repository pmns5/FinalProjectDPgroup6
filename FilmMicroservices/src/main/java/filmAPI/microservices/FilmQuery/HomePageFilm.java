package filmAPI.microservices.FilmQuery;

import filmAPI.EnumGenre;
import filmAPI.microservices.ActorManagement.Actor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "HomePageFilm")
@XmlAccessorType(XmlAccessType.NONE)
public class HomePageFilm extends BaseFilm {

    @XmlAttribute
    private List<Actor> actors;
    @XmlAttribute
    private float avgScore;

    public HomePageFilm() {

    }

    public HomePageFilm(int id, String title, EnumGenre genre, String plot, String trailer, byte[] poster,
                        List<Actor> actors, float avgScore) {
        super(id, title, genre, plot, trailer, poster);
        this.actors = actors;
        this.avgScore = avgScore;
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
