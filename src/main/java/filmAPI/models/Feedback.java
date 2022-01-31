package filmAPI.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import java.sql.Date;

public class Feedback {
    private int id_user;
    private int id_film;
    @Pattern(regexp = "<+.*>+", message = "Errore: Caratteri non permessi")
    private String comment;
    private Float score;

    //@Temporal(TemporalType.DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "it_IT", timezone = "Europe/Rome")
    private Date date=null;

    public Feedback(int id_user, int id_film, String comment, Float score) {
        this.id_user = id_user;
        this.id_film = id_film;
        this.comment = comment;
        this.score = score;
    }
    public Feedback(int id_user, int id_film, String comment, Float score, Date date) {
        this.id_user = id_user;
        this.id_film = id_film;
        this.comment = comment;
        this.score = score;
        this.date = date;
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
