package models;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "FilmManagement")
@XmlAccessorType(XmlAccessType.NONE)
public class FilmManagement {
    @XmlAttribute
    private Film film;
    @XmlAttribute
    private List<Actor> actorList;

    public FilmManagement(HttpServletRequest req, boolean add) throws ServletException, IOException {
        film = new Film(req, add);
        List<Actor> list = new ArrayList<>();
        String[] ids = req.getParameterValues("actors");
        for (String id : ids) {
            list.add(new Actor(id));
        }
        actorList = list;
    }

    public FilmManagement() {

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
