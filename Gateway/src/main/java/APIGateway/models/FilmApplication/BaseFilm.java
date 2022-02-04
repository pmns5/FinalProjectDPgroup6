package APIGateway.models.FilmApplication;

import APIGateway.Util;
import APIGateway.EnumGenre;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;

public class BaseFilm {
    private int id;
    private String title;
    private String plot;
    private String genre;
    private String trailer;
    private String poster;

    public BaseFilm(HttpServletRequest req, boolean add) throws ServletException, IOException {
        if (!add) id = Integer.parseInt(req.getParameter("id"));
        title = req.getParameter("title");
        plot = req.getParameter("plot");
        genre = req.getParameter("genre");
        trailer = req.getParameter("trailer");
        poster = new String(Base64.getEncoder().encode(req.getPart("poster").getInputStream().readAllBytes()));
    }

    public BaseFilm(int id, String title, EnumGenre genre, String plot, String trailer, byte[] poster) {
        this.id = id;
        this.title = title;
        this.genre = genre.name();
        this.plot = plot;
        this.poster = new String(Base64.getEncoder().encode(poster));
        this.trailer = Util.extractTrailerString(trailer);
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

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public EnumGenre getGenre() {
        return EnumGenre.valueOf(genre);
    }

    public void setGenre(EnumGenre genre) {
        this.genre = genre.name();
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