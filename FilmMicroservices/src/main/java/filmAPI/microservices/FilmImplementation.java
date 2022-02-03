package filmAPI.microservices;

import com.mysql.cj.jdbc.Blob;
import filmAPI.interfaces.DBConnection;
import filmAPI.models.*;

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
    public boolean addFilm(FilmManagement filmManagement) throws SQLException {
        db.connect();
        Connection conn = db.getConn();
        Savepoint save1 = null;
        try {
            db.getConn().setAutoCommit(false);
            save1 = conn.setSavepoint();

            Film film = filmManagement.getFilm();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO film (title, genre, plot, trailer, poster) VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, film.getTitle());
            stmt.setString(2, film.getGenre().toString());
            stmt.setString(3, film.getPlot());
            stmt.setString(4, film.getTrailer());
            stmt.setBlob(5, new SerialBlob(film.getPoster()));
            stmt.execute();

            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO cast (id_film, id_actor) VALUES (?, ?)");
            stmt2.setInt(1, keys.getInt(1));
            for (Actor actor: filmManagement.getActorList()) {
                stmt2.setInt(2, actor.getId());
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
    public boolean editFilm(FilmManagement filmManagement) throws SQLException {
        db.connect();
        Connection conn = db.getConn();
        Savepoint save1 = null;
        try {
            db.getConn().setAutoCommit(false);
            save1 = conn.setSavepoint();

            Film film = filmManagement.getFilm();

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

            PreparedStatement stmt3 = conn.prepareStatement("insert into cast (id_film, id_actor) values (?, ?);");
            stmt3.setInt(1, film.getId());
            for (Actor actor : filmManagement.getActorList()) {
                stmt3.setInt(2, actor.getId());
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
    public FilmManagement getOneFilm(@PathParam("id") int idFilm) {
        db.connect();
        Connection conn = db.getConn();
        Savepoint save1;
        try {
            db.getConn().setAutoCommit(false);
            save1 = conn.setSavepoint();

            PreparedStatement stmt = conn.prepareStatement("SELECT title, genre, plot, trailer, poster FROM film where film.id_film=?;");
            stmt.setInt(1, idFilm);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                String title = rs.getString("title");
                String genre = rs.getString("genre");
                String plot = rs.getString("plot");
                String trailer = rs.getString("trailer");
                com.mysql.cj.jdbc.Blob poster = (Blob) rs.getBlob("poster");
                int blobLength = (int) poster.length();
                byte[] blobAsBytes = poster.getBytes(1, blobLength);
                poster.free();


                PreparedStatement stmt2 = conn.prepareStatement("SELECT a.id_actor, a.name, a.surname " +
                        "FROM cast c join actor a on a.id_actor = c.id_actor where c.id_film=?;");
                stmt2.setInt(1, idFilm);
                try (ResultSet rs2 = stmt2.executeQuery()) {
                    List<Actor> actors = new ArrayList<>();
                    while (rs2.next()) {
                        actors.add(new Actor(rs2.getInt("id_actor"), rs2.getString("name"), rs2.getString("surname")));
                    }
                    return new FilmManagement(new Film(idFilm, title, plot, EnumGenre.valueOf(genre), trailer, blobAsBytes), actors);

                }
            } catch (SQLException e) {
                conn.rollback(save1);
            } finally {
                conn.commit();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            db.disconnect();
        }
        return null;

    }

    @GET
    @Path("/get-films")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<FilmManagement> getAllFilms() throws SQLException {
        db.connect();
        Connection conn = db.getConn();
        Savepoint save1 = null;
        List<FilmManagement> listFilms = new ArrayList<>();
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
                Film film = new Film(id_film, title, plot, EnumGenre.valueOf(genre), trailer, blobAsBytes);


                Statement stmt2 = db.getConn().createStatement();
                ResultSet rs2 = stmt2.executeQuery("SELECT a.id_actor, a.name, a.surname FROM film join cast c on film.id_film = c.id_film join actor a on a.id_actor = c.id_actor AND film.id_film = " + id_film);
                List<Actor> actorList = new ArrayList<>();
                while (rs2.next()) {
                    actorList.add(new Actor(rs2.getInt("id_actor"), rs2.getString("name"), rs2.getString("surname")));
                }
                listFilms.add(new FilmManagement(film, actorList));
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
