package filmAPI.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;


import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@JsonSerialize(using = Film.FilmSerializer.class)
@JsonDeserialize(using = Film.FilmDeserializer.class)
public class Film {
    private int id;
    private String title;
    private String plot;
    private EnumGenre genre;
    private String trailer;
    private byte[] poster;
    private String[] actors;

    public Film(int id, String title, String plot, EnumGenre genre, String trailer, byte[] poster, String[] actors) {
        this.id = id;
        this.title = title;
        this.plot = plot;
        this.genre = genre;
        this.poster = poster;
        this.trailer = extractTrailerString(trailer);
        this.actors = actors;
    }

    public Film (){

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
        return genre;
    }

    public void setGenre(EnumGenre genre) {
        this.genre = genre;
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

    public String[] getActors() {
        return actors;
    }

    public void setActors(String[] actors) {
        this.actors = actors;
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

        protected FilmSerializer() {
            this(null);
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
            jsonGenerator.writeStringField("actors", Arrays.toString(film.actors));
            jsonGenerator.writeEndObject();
        }
    }

    static class FilmDeserializer extends StdDeserializer<Film> {
        protected FilmDeserializer(Class<Film> t) {
            super(t);
        }

        protected FilmDeserializer() {
            this(Film.class);
        }

        @Override
        public Film deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            TreeNode tn = jsonParser.readValueAsTree();
            int id = Integer.parseInt(tn.get("id").toString());
            String title = tn.get("title").toString();
            String plot = tn.get("plot").toString();
            EnumGenre genre = EnumGenre.valueOf(tn.get("genre").toString());
            String trailer = tn.get("trailer").toString();
            String posterStr = tn.get("poster").toString();
            byte[] poster = Base64.getDecoder().decode(posterStr);
            String actors = tn.get("actors").toString();
            return new Film(id, title, plot, genre, trailer, poster, null);

        }


    }
}
