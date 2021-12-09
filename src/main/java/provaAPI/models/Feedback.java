package provaAPI.models;

public class Feedback {
    private int id_user;
    private int id_film;
    private String comment;
    private Float score;

    public Feedback(int id_user, int id_film, String comment, Float score) {
        this.id_user = id_user;
        this.id_film = id_film;
        this.comment = comment;
        this.score = score;
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
}
