package filmAPI.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import filmAPI.gateway.EnumGenre;

import java.io.IOException;
import java.util.Base64;

@JsonSerialize(using = Film.FilmSerializer.class)
public class Film {
    private final String plot;
    private final String trailer;
    private final byte[] poster;
    private final int id;
    private final String title;
    private final EnumGenre genre;

    public Film(int id, String title, String plot, EnumGenre genre, String trailer, byte[] poster) {
        this.id = id;
        this.title = title;
        this.plot = plot;
        this.genre = genre;
        this.poster = poster;
        this.trailer = extractTrailerString(trailer);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPlot() {
        return plot;
    }

    public EnumGenre getGenre() {
        return genre;
    }

    public String getTrailer() {
        return trailer;
    }

    public byte[] getPoster() {
        return poster;
    }

    private String extractTrailerString(String trailer) {
        String[] splitted = trailer.split("/");
        return splitted[splitted.length - 1];
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    static class FilmSerializer extends StdSerializer<Film> {
        protected FilmSerializer(Class<Film> t) {
            super(t);
        }

        @Override
        public void serialize(Film film, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", film.id);
            jsonGenerator.writeStringField("title", film.title);
            jsonGenerator.writeStringField("plot", film.plot);
            jsonGenerator.writeStringField("genre", film.genre.toString());
            jsonGenerator.writeStringField("trailer", film.trailer);
            jsonGenerator.writeStringField("poster", new String(Base64.getEncoder().encode(film.poster)));
            jsonGenerator.writeEndObject();
        }
    }
}
