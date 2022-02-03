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


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.Base64;

@XmlRootElement(name = "Film")
@XmlAccessorType(XmlAccessType.NONE)
//@JsonSerialize(using = Film.FilmSerializer.class)
//@JsonDeserialize(using = Film.FilmDeserializer.class)
public class Film {
    @XmlAttribute
    private int id;
    @XmlAttribute
    private String title;
    @XmlAttribute
    private String plot;
    @XmlAttribute
    private String genre;
    @XmlAttribute
    private String trailer;

    @XmlAttribute
    private String poster;

    public Film(int id, String title, String plot, EnumGenre genre, String trailer, byte[] poster){//, String[] actors) {
        this.id = id;
        this.title = title;
        this.plot = plot;
        this.genre = genre.name();
        this.poster = new String(Base64.getEncoder().encode(poster));
        this.trailer = extractTrailerString(trailer);

    }

    public Film() {

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
//            jsonGenerator.writeStringField("poster", new String(Base64.getEncoder().encode(film.poster)));
            jsonGenerator.writeStringField("poster", "CIAO");
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
            String poster = tn.get("poster").toString();
            //byte[] poster = Base64.getDecoder().decode(posterStr);

            return new Film(id, title, plot, genre, trailer, poster.getBytes());

        }


    }


//    static class PosterDeserializer extends StdDeserializer<byte[]> {
//
//        public PosterDeserializer() {
//            this(null);
//        }
//
//        public PosterDeserializer(Class<byte[]> t) {
//            super(t);
//        }
//
//        @Override
//        public byte[] deserialize(JsonParser jsonParser, DeserializationContext context)
//                throws IOException, JsonProcessingException {
//            TreeNode tn = jsonParser.readValueAsTree();
//            String posterStr = tn.get("poster").toString();
//            return Base64.getDecoder().decode(posterStr);
//
//        }
//    }
//    static class PosterSerializer extends StdSerializer<byte[]> {
//
//        public PosterSerializer() {
//            this(null);
//        }
//
//        public PosterSerializer(Class<byte[]> t) {
//            super(t);
//        }
//
//        @Override
//        public void serialize(byte[] bytes, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//            jsonGenerator.writeString(new String(Base64.getEncoder().encode(bytes)));
//        }
//
//    }

}
