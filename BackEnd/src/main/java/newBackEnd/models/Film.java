package newBackEnd.models;

import java.util.Base64;

public class Film implements Model {
    private int id;
    private String title;
    private String genre;
    private String plot;
    private String trailer;
    private byte[] poster;

    public Film(int id, String title, String genre, String plot, String trailer, byte[] poster) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.plot = plot;
        this.trailer = trailer;
        this.poster = poster;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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
        return poster;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }

    public String toJSON() {
        return "{" + "\"id\":\"" + id + "\"," +
                "\"title\":\"" + title + "\"," +
                "\"genre\":\"" + genre + "\"," +
                "\"plot\":\"" + plot + "\"," +
                "\"trailer\":\"" + trailer + "\"," +
                "\"poster\":\"" + new String(Base64.getEncoder().encode(poster)) + "}";
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }
}
