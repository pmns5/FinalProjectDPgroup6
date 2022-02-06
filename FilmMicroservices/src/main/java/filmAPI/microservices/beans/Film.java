package filmAPI.microservices.beans;

import filmAPI.EnumGenre;
import filmAPI.Util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Base64;
import java.util.List;

/**
 * Bean class representing the Film
 */
@XmlRootElement(name = "Film")
@XmlAccessorType(XmlAccessType.NONE)
public class Film {
    @XmlAttribute
    private int id;
    @XmlAttribute
    private String title;
    @XmlAttribute
    private String genre;
    @XmlAttribute
    private String plot;
    @XmlAttribute
    private String trailer;
    @XmlAttribute
    private String poster;
    @XmlAttribute
    private List<Actor> actorList;

    public Film(int id, String title, EnumGenre genre, String plot, String trailer, byte[] poster,
                List<Actor> actorList) {
        this.id = id;
        this.title = title;
        this.genre = genre.name();
        this.plot = plot;
        this.poster = new String(Base64.getEncoder().encode(poster));
        this.trailer = Util.extractTrailerString(trailer);
        this.actorList = actorList;
    }


    public Film() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EnumGenre getGenre() {
        return EnumGenre.valueOf(genre);
    }

    public void setGenre(EnumGenre genre) {
        this.genre = genre.name();
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public byte[] getPoster() {
        return Base64.getDecoder().decode(poster);
    }

    public void setPoster(byte[] poster) {
        this.poster = new String(Base64.getEncoder().encode(poster));
    }


    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }
}
