package filmAPI.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;

@XmlRootElement(name = "Feedback")
@XmlAccessorType(XmlAccessType.NONE)
public class Feedback {
    @XmlAttribute
    private int id_user;
    @XmlAttribute
    private int id_film;
    @XmlAttribute
    private String comment;
    @XmlAttribute
    private Float score;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "it_IT", timezone = "Europe/Rome")
    private Date date = null;

    public Feedback(int id_user, int id_film, String comment, Float score, Date date) {
        this.id_user = id_user;
        this.id_film = id_film;
        this.comment = comment;
        this.score = score;
        this.date = date;
    }

    public Feedback() {

    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_film() {
        return id_film;
    }

    public void setId_film(int id_film) {
        this.id_film = id_film;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
