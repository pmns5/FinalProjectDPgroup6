package filmAPI.microservices;

import com.mysql.cj.jdbc.Blob;
import filmAPI.interfaces.DBConnection;
import filmAPI.models.Actor;
import filmAPI.models.EnumGenre;
import filmAPI.models.Film;
import filmAPI.models.FilmManagement;

import javax.sql.rowset.serial.SerialBlob;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet keys;
        Film film_to_add = filmManagement.getFilm();
        int id_film;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("INSERT INTO film (title, genre, plot, trailer, poster) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, film_to_add.getTitle());
            statement.setString(2, film_to_add.getGenre().toString());
            statement.setString(3, film_to_add.getPlot());
            statement.setString(4, film_to_add.getTrailer());
            statement.setBlob(5, new SerialBlob(film_to_add.getPoster()));
            statement.execute();

            keys = statement.getGeneratedKeys();
            keys.next();
            id_film = keys.getInt(1);

            statement = connection.prepareStatement("INSERT INTO cast (id_film, id_actor) VALUES (?, ?)");
            statement.setInt(1, id_film);
            for (Actor actor : filmManagement.getActorList()) {
                statement.setInt(2, actor.getId());
                statement.execute();
            }

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return false;
        } finally {
            connection.commit();
            db.disconnect();
        }
        return true;
    }

    @POST
    @Path("/edit-film")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean editFilm(FilmManagement filmManagement) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        Film film_to_edit = filmManagement.getFilm();

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("UPDATE film SET title = ?, genre = ?, plot = ?, trailer = ?, poster = ? WHERE id_film = ?");
            statement.setString(1, film_to_edit.getTitle());
            statement.setString(2, film_to_edit.getGenre().toString());
            statement.setString(3, film_to_edit.getPlot());
            statement.setString(4, film_to_edit.getTrailer());
            statement.setBlob(5, new SerialBlob(film_to_edit.getPoster()));
            statement.setInt(6, film_to_edit.getId());
            statement.execute();

            statement = connection.prepareStatement("DELETE FROM cast WHERE id_film = ?");
            statement.setInt(1, film_to_edit.getId());
            statement.execute();

            statement = connection.prepareStatement("INSERT INTO cast (id_film, id_actor) VALUES (?, ?)");
            statement.setInt(1, film_to_edit.getId());
            for (Actor actor : filmManagement.getActorList()) {
                statement.setInt(2, actor.getId());
                statement.execute();
            }

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return false;
        } finally {
            connection.commit();
            db.disconnect();
        }
        return true;
    }

    @GET
    @Path("/delete-film/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean deleteFilm(@PathParam("id") int id_film) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;


        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("DELETE FROM film WHERE id_film = ?");
            statement.setInt(1, id_film);
            statement.execute();

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return false;
        } finally {
            connection.commit();
            db.disconnect();
        }
        return true;
    }

    @GET
    @Path("/get-film/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public FilmManagement getFilm(@PathParam("id") int idFilm) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet rs;
        Film film_to_get;
        List<Actor> actorList;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("SELECT title, genre, plot, trailer, poster FROM film where film.id_film = ?");
            statement.setInt(1, idFilm);
            rs = statement.executeQuery();
            if (rs.next()) {
                Blob poster = (Blob) rs.getBlob("poster");
                film_to_get = new Film(idFilm, rs.getString("title"), EnumGenre.valueOf(rs.getString("genre")), rs.getString("plot"), rs.getString("trailer"), poster.getBytes(1, (int) poster.length()));
                poster.free();

                statement = connection.prepareStatement("SELECT a.id_actor, a.name, a.surname " + "FROM cast c join actor a on a.id_actor = c.id_actor where c.id_film = ?");
                statement.setInt(1, idFilm);
                rs = statement.executeQuery();
                actorList = new ArrayList<>();
                while (rs.next()) {
                    actorList.add(new Actor(rs.getInt("id_actor"), rs.getString("name"), rs.getString("surname")));
                }
                return new FilmManagement(film_to_get, actorList);
            } else {
                return null;
            }

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return null;
        } finally {
            connection.commit();
            db.disconnect();
        }
    }

    @GET
    @Path("/get-films")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<FilmManagement> getFilms() throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet rs_first, rs_second;
        int id_film;
        Film film_to_get;
        List<Actor> actorList;
        List<FilmManagement> filmManagementList = new ArrayList<>();

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("SELECT id_film, title, genre, plot, trailer, poster FROM film ORDER BY id_film");
            rs_first = statement.executeQuery();
            while (rs_first.next()) {
                id_film = rs_first.getInt("id_film");
                Blob poster = (Blob) rs_first.getBlob("poster");
                film_to_get = new Film(id_film, rs_first.getString("title"), EnumGenre.valueOf(rs_first.getString("genre")), rs_first.getString("plot"), rs_first.getString("trailer"), poster.getBytes(1, (int) poster.length()));
                poster.free();

                statement = connection.prepareStatement("SELECT a.id_actor, a.name, a.surname FROM film JOIN cast c ON film.id_film = c.id_film JOIN actor a ON a.id_actor = c.id_actor AND film.id_film = " + id_film);
                rs_second = statement.executeQuery();
                actorList = new ArrayList<>();
                while (rs_second.next()) {
                    actorList.add(new Actor(rs_second.getInt("id_actor"), rs_second.getString("name"), rs_second.getString("surname")));
                }
                filmManagementList.add(new FilmManagement(film_to_get, actorList));
            }

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return null;
        } finally {
            connection.commit();
            db.disconnect();
        }
        return filmManagementList;
    }
}
