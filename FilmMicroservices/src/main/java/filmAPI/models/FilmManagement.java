package filmAPI.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "FilmManagement")
@XmlAccessorType(XmlAccessType.NONE)
public class FilmManagement{
    @XmlAttribute
    private Film film;
    @XmlAttribute
    private List<Actor> actorList;

    public FilmManagement(){

    }
    public FilmManagement(Film film, List<Actor> actorList) {
        this.film = film;
        this.actorList = actorList;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }
}
