package newBackEnd.models;

public class Feedback implements Model {
    private int id_film;
    private int id_user;
    private float score;
    private String comment;

    public Feedback(int id_film, int id_user, float score, String comment) {
        this.id_film = id_film;
        this.id_user = id_user;
        this.score = score;
        this.comment = comment;
    }

    public int getId_film() {
        return id_film;
    }

    public void setId_film(int id_film) {
        this.id_film = id_film;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String toJSON() {
        return "{" + "\"id_film\":\"" + id_film + "\"," +
                "\"id_user\":\"" + id_user + "\"," +
                "\"score\":\"" + score + "\"," +
                "\"comment\":\"" + comment + "\"}";
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }
}
