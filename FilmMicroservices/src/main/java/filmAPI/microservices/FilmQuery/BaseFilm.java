package filmAPI.microservices.FilmQuery;

import filmAPI.EnumGenre;
import filmAPI.Utils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Base64;

@XmlRootElement(name = "BaseFilm")
@XmlAccessorType(XmlAccessType.NONE)
public class BaseFilm {
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

    public BaseFilm(int id, String title, EnumGenre genre, String plot, String trailer, byte[] poster) {
        this.id = id;
        this.title = title;
        this.genre = genre.name();
        this.plot = plot;
        this.poster = new String(Base64.getEncoder().encode(poster));
        this.trailer = Utils.extractTrailerString(trailer);
    }

    public BaseFilm() {

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


    @Override
    public boolean equals(Object o) {
        return false;
    }

}
