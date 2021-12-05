package newBackEnd.models;

import com.mysql.cj.jdbc.Blob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class Film implements Model {
    private int id;
    private String title;
    private String plot;
    private int genre;
    private ArrayList<Actor> actors;
    private byte[] poster;

    public Film(String title, String plot, String genre, ArrayList<Actor> actors) {
        this.title = title;
        this.plot = plot;
        this.genre = Integer.parseInt(genre);
        this.actors = actors;
    }
    public Film(String id, String title, String plot, String genre, byte[] poster){
        this.id = Integer.parseInt(id);
        this.title = title;
        this.plot = plot;
        this.genre = Integer.parseInt(genre);
        this.poster = poster;
        this.actors = new ArrayList<>();
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

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public void setActors(ArrayList<Actor> actors) {
        this.actors = actors;
    }

    public byte[] getPoster() {
        return poster;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }

    public String toJSON() {
        return "{" + "\"id\":\"" + id + "\"," +
                "\"title\":\"" + title + "\"," +
                "\"plot\":\"" + plot + "\"," +
                "\"genre\":\"" + genre + "\"," +
                "\"poster\":\"" + new String(Base64.getEncoder().encode(poster)) + "\"," +
                "\"actors\":" + JsonUtil.toJson(actors) + "}";
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

}
