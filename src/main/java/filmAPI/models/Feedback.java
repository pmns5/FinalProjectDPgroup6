package filmAPI.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public class Feedback {
    private final int id_user;
    private final String comment;
    private final int id_film;
    private final Float score;
    @SuppressWarnings("unused")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "it_IT", timezone = "Europe/Rome")
    private Date date = null;

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

    public int getId_film() {
        return id_film;
    }

    public String getComment() {
        return comment;
    }

    public Float getScore() {
        return score;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
