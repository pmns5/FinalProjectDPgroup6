package filmAPI.microservices.beans;

import filmAPI.EnumGenre;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Bean class representing the Film object containing data related to the film and the average score
 * given by feedbacks
 */
@XmlRootElement(name = "HomePageFilm")
@XmlAccessorType(XmlAccessType.NONE)
public class HomePageFilm extends Film {

    @XmlAttribute
    private float avgScore;

    public HomePageFilm() {

    }

    public HomePageFilm(int id, String title, EnumGenre genre, String plot, String trailer, byte[] poster,
                        List<Actor> actors, float avgScore) {
        super(id, title, genre, plot, trailer, poster, actors);
        this.avgScore = avgScore;
    }


    public float getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(float avgScore) {
        this.avgScore = avgScore;
    }

}
