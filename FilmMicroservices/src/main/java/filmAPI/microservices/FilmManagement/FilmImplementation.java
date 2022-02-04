package filmAPI.microservices.FilmManagement;

import com.mysql.cj.jdbc.Blob;
import filmAPI.interfaces.DBConnection;
import filmAPI.microservices.ActorManagement.Actor;
import filmAPI.EnumGenre;

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

    @PUT
    @Path("/add-film")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean addFilm(Film film) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet keys;
        int id_film;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("INSERT INTO film (title, genre, plot, trailer, poster) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, film.getTitle());
            statement.setString(2, film.getGenre().toString());
            statement.setString(3, film.getPlot());
            statement.setString(4, film.getTrailer());
            statement.setBlob(5, new SerialBlob(film.getPoster()));
            statement.execute();

            keys = statement.getGeneratedKeys();
            keys.next();
            id_film = keys.getInt(1);

            statement = connection.prepareStatement("INSERT INTO cast (id_film, id_actor) VALUES (?, ?)");
            statement.setInt(1, id_film);
            for (Actor actor : film.getActorList()) {
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

    @PUT
    @Path("/edit-film")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean editFilm(Film film) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;


        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("UPDATE film SET title = ?, genre = ?, plot = ?, trailer = ?, poster = ? WHERE id_film = ?");
            statement.setString(1, film.getTitle());
            statement.setString(2, film.getGenre().toString());
            statement.setString(3, film.getPlot());
            statement.setString(4, film.getTrailer());
            statement.setBlob(5, new SerialBlob(film.getPoster()));
            statement.setInt(6, film.getId());
            statement.execute();

            statement = connection.prepareStatement("DELETE FROM cast WHERE id_film = ?");
            statement.setInt(1, film.getId());
            statement.execute();

            statement = connection.prepareStatement("INSERT INTO cast (id_film, id_actor) VALUES (?, ?)");
            statement.setInt(1, film.getId());
            for (Actor actor : film.getActorList()) {
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

    @DELETE
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
    public Film getFilm(@PathParam("id") int idFilm) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet rs_first, rs_second;
        List<Actor> actorList;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("SELECT title, genre, plot, trailer, poster FROM film where film.id_film = ?");
            statement.setInt(1, idFilm);
            rs_first = statement.executeQuery();
            if (rs_first.next()) {
                Blob poster = (Blob) rs_first.getBlob("poster");
                byte[] posterBytes = poster.getBytes(1, (int) poster.length());
                poster.free();

                statement = connection.prepareStatement("SELECT a.id_actor, a.name, a.surname " + "FROM cast c join actor a on a.id_actor = c.id_actor where c.id_film = ?");
                statement.setInt(1, idFilm);
                rs_second = statement.executeQuery();
                actorList = new ArrayList<>();
                while (rs_second.next()) {
                    actorList.add(new Actor(rs_second.getInt("id_actor"), rs_second.getString("name"),
                            rs_second.getString("surname")));
                }

                return new Film(idFilm, rs_first.getString("title"),
                        EnumGenre.valueOf(rs_first.getString("genre")), rs_first.getString("plot"),
                        rs_first.getString("trailer"), posterBytes, actorList);
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
    public List<Film> getFilms() throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet rs_first, rs_second;
        int id_film;
        List<Actor> actorList;
        List<Film> filmList = new ArrayList<>();

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("SELECT id_film, title, genre, plot, trailer, poster FROM film ORDER BY id_film");
            rs_first = statement.executeQuery();
            while (rs_first.next()) {
                id_film = rs_first.getInt("id_film");
                Blob poster = (Blob) rs_first.getBlob("poster");
                poster.free();

                statement = connection.prepareStatement("SELECT a.id_actor, a.name, a.surname FROM film JOIN cast c ON film.id_film = c.id_film JOIN actor a ON a.id_actor = c.id_actor AND film.id_film = " + id_film);
                rs_second = statement.executeQuery();
                actorList = new ArrayList<>();
                while (rs_second.next()) {
                    actorList.add(new Actor(rs_second.getInt("id_actor"), rs_second.getString("name"), rs_second.getString("surname")));
                }

                filmList.add(new Film(id_film, rs_first.getString("title"),
                        EnumGenre.valueOf(rs_first.getString("genre")), rs_first.getString("plot"),
                        rs_first.getString("trailer"), poster.getBytes(1, (int) poster.length()),
                        actorList));
            }

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return null;
        } finally {
            connection.commit();
            db.disconnect();
        }
        return filmList;
    }
}
