package filmAPI.microservices;

import com.mysql.cj.jdbc.Blob;
import filmAPI.interfaces.DBConnection;
import filmAPI.models.EnumGenre;
import filmAPI.models.Film;

import javax.sql.rowset.serial.SerialBlob;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Microservice for management of Data associated of a film
 */
@Path("/films")
public class FilmImplementation extends DBConnection {

    public FilmImplementation() {
        super();
    }

    @POST
    @Path("/add-film")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean addFilm(Film film) throws SQLException {
        db.connect();
        Connection conn = db.getConn();
        Savepoint save1 = null;
        try {
            db.getConn().setAutoCommit(false);
            save1 = conn.setSavepoint();


            PreparedStatement stmt = conn.prepareStatement("INSERT INTO film (title, genre, plot, trailer, poster) VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, film.getTitle());
            stmt.setString(2, film.getGenre().toString());
            stmt.setString(3, film.getPlot());
            stmt.setString(4, film.getTrailer());
            stmt.setBlob(5, new SerialBlob(film.getPoster()));

            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();

            stmt.execute();
            PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO cast (id_film, id_actor) VALUES (?, ?)");
            stmt2.setInt(1, keys.getInt(1));
            for (String actorID : film.getActors()) {
                stmt2.setInt(2, Integer.parseInt(actorID));
                stmt2.execute();
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback(save1);
            return true;
        } finally {

            db.disconnect();
        }

        return false;
    }


    @POST
    @Path("/edit-film")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean editFilm(Film film) throws SQLException {
        db.connect();
        Connection conn = db.getConn();
        Savepoint save1 = null;
        try {
            db.getConn().setAutoCommit(false);
            save1 = conn.setSavepoint();


            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE film SET title = ?, genre = ?, plot = ?, trailer = ?," +
                            " poster = ? WHERE id_film = ?");
            stmt.setString(1, film.getTitle());
            stmt.setString(2, film.getGenre().toString());
            stmt.setString(3, film.getPlot());
            stmt.setString(4, film.getTrailer());
            stmt.setBlob(5, new SerialBlob(film.getPoster()));
            stmt.setInt(6, film.getId());
            stmt.execute();

            PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM cast WHERE id_film = (?);");
            stmt2.setInt(1, film.getId());
            stmt2.execute();

            PreparedStatement stmt3 = conn.prepareStatement(" INSERT INTO cast(id_film, id_actor) VALUES (?, ?);");
            stmt2.setInt(1, film.getId());
            for (String actorID : film.getActors()) {
                stmt3.setInt(2, Integer.parseInt(actorID));
                stmt3.execute();
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback(save1);
            return true;
        } finally {

            db.disconnect();
        }


        return false;
    }

    @GET
    @Path("/delete-film/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean deleteFilm(@PathParam("id") int idFilm) {
        db.connect();
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "DELETE FROM film WHERE id_film = ?;"
        )) {
            stmt.setInt(1, idFilm);
            stmt.execute();
        } catch (SQLException e) {
            return false;
        } finally {
            db.disconnect();
        }
        return true;
    }

    @GET
    @Path("/get-film/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Film getOneFilm(@PathParam("id") int idFilm) {
        db.connect();
        try (PreparedStatement stmt = db.getConn().prepareStatement("SELECT film.id_film, title, genre, plot, trailer, poster, c.id_actor\n" +
                "from film join cast c on film.id_film = c.id_film and film.id_film=?")) {
            stmt.setInt(1, idFilm);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                int id_film = rs.getInt(1);
                String title = rs.getString(2);
                String genre = rs.getString(3);
                String plot = rs.getString(4);
                String trailer = rs.getString(5);
                Blob poster = (Blob) rs.getBlob(6);
                int blobLength = (int) poster.length();
                byte[] blobAsBytes = poster.getBytes(1, blobLength);
                poster.free();
                List<String> actors = new ArrayList<>();
                actors.add(rs.getString(7));
                while (!rs.next()) {
                    actors.add(rs.getString(7));
                }
                return new Film(id_film, title, plot, EnumGenre.valueOf(genre), trailer, blobAsBytes, actors.toArray(new String[0]));
            } finally {
                db.disconnect();
            }
        } catch (SQLException e) {
            return null;
        }
    }

    @GET
    @Path("/get-films")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Film> getAllFilms() throws SQLException {
        db.connect();
        Connection conn = db.getConn();
        Savepoint save1 = null;
        ArrayList<Film> listFilms = new ArrayList<>();
        try {
            db.getConn().setAutoCommit(false);
            save1 = conn.setSavepoint();

            Statement stmt = db.getConn().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_film, title, genre, plot, trailer, poster FROM film ORDER BY id_film");
            while (rs.next()) {
                int id_film = rs.getInt(1);
                String title = rs.getString(2);
                String genre = rs.getString(3);
                String plot = rs.getString(4);
                String trailer = rs.getString(5);
                Blob poster = (Blob) rs.getBlob(6);
                int blobLength = (int) poster.length();
                byte[] blobAsBytes = poster.getBytes(1, blobLength);
                poster.free();
                Statement stmt2 = db.getConn().createStatement();
                ResultSet rs2 = stmt.executeQuery("SELECT id_actor FROM film join cast c on film.id_film = c.id_film AND film.id_film = " + id_film);
                List<String> actors = new ArrayList<>();
                while (rs2.next()) {
                    actors.add(String.valueOf(rs2.getInt(1)));
                }
                listFilms.add(new Film(id_film, title, plot, EnumGenre.valueOf(genre), trailer, blobAsBytes, actors.toArray(new String[0])));
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback(save1);
            return null;
        } finally {

            db.disconnect();
        }
        return listFilms;
    }

    /*
    @GET
    @Path("/get-by-genre/{genre}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Film> getByGenre(@PathParam("genre") String genreStr) {
        db.connect();
        EnumGenre genre = EnumGenre.valueOf(genreStr);
        ArrayList<Film> listFilms = new ArrayList<>();
        try (Statement stmt = db.getConn().createStatement(); ResultSet rs = stmt.executeQuery("SELECT id_film," +
                " title, plot, trailer, poster FROM film WHERE genre=" + genre + "  ORDER BY id_film")) {
            while (rs.next()) {
                int id_film = rs.getInt(1);
                String title = rs.getString(2);
                String plot = rs.getString(3);
                String trailer = rs.getString(4);
                Blob poster = (Blob) rs.getBlob(5);
                int blobLength = (int) poster.length();
                byte[] blobAsBytes = poster.getBytes(1, blobLength);
                poster.free();
                listFilms.add(new Film(id_film, title, plot, genre, trailer, blobAsBytes));
            }
        } catch (SQLException e) {
            return null;
        } finally {
            db.disconnect();
        }
        return listFilms;
    }
     */
}
