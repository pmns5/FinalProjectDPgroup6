package filmAPI.microservices.resources;

import com.mysql.cj.jdbc.Blob;
import filmAPI.EnumGenre;
import filmAPI.interfaces.DBConnection;
import filmAPI.microservices.beans.Actor;
import filmAPI.microservices.beans.Film;

import javax.sql.rowset.serial.SerialBlob;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Resource class for managing the actors
 */
@Path("/films")
public class FilmResource extends DBConnection {
    public FilmResource() {
        super();
    }

    /**
     * Insert a new Film inside the database
     *
     * @param film - the Film to be added
     * @return true if the insertion is completed correctly, false otherwise
     * @throws SQLException if the connection with the database fails
     */
    @POST
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

        //Execute transaction
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            // First operation is inserting of the Film inside the film table
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

            // Second operation is inserting in the cast table a row for each actor associated to the given film
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

    /**
     * Edit the Film data inside the database
     *
     * @param film - the Film to be edited
     * @return true if the edit is completed correctly, false otherwise
     * @throws SQLException if the connection with the database fails
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean editFilm(Film film) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;


        //Execute transaction
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            // First operation is updating the data in the film table
            statement = connection.prepareStatement("UPDATE film SET title = ?, genre = ?, plot = ?, trailer = ?, poster = ? WHERE id_film = ?");
            statement.setString(1, film.getTitle());
            statement.setString(2, film.getGenre().toString());
            statement.setString(3, film.getPlot());
            statement.setString(4, film.getTrailer());
            statement.setBlob(5, new SerialBlob(film.getPoster()));
            statement.setInt(6, film.getId());
            statement.execute();

            // Second operation is deleting all the previous associations of actors to the given film
            statement = connection.prepareStatement("DELETE FROM cast WHERE id_film = ?");
            statement.setInt(1, film.getId());
            statement.execute();

            // Third operation is inserting in the cast table a row for each actor associated to the given film
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

    /**
     * Delete the Film associated to the given id
     *
     * @param id_film - the id of the film to delete
     * @return true if the deletion is completed correctly, false otherwise
     * @throws SQLException if the connection with the database fails
     */
    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean deleteFilm(@PathParam("id") int id_film) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;

        // Execute Transaction.
        // Only One operation is necessary since the underlying DBMS manages the consistency  of the data
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

    /**
     * Returns the Film data associated to the passed id
     *
     * @param id_film - the id of the film to retrieve
     * @return the Film object containing all the data of the Film
     * @throws SQLException if the connection with the database fails
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Film getFilm(@PathParam("id") int id_film) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet rs_first, rs_second;
        List<Actor> actorList;

        //Execute Transaction
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            // First Query retrieves all data from the film table associated to the id
            statement = connection.prepareStatement("SELECT title, genre, plot, trailer, poster FROM film where film.id_film = ?");
            statement.setInt(1, id_film);
            rs_first = statement.executeQuery();
            if (rs_first.next()) {
                Blob poster = (Blob) rs_first.getBlob("poster");
                byte[] posterBytes = poster.getBytes(1, (int) poster.length());
                poster.free();

                // Second query retrieves from the cast and actor tables the list of Actors associated to the film
                statement = connection.prepareStatement("SELECT a.id_actor, a.name, a.surname " + "FROM cast c join actor a on a.id_actor = c.id_actor where c.id_film = ?");
                statement.setInt(1, id_film);
                rs_second = statement.executeQuery();
                actorList = new ArrayList<>();
                while (rs_second.next()) {
                    actorList.add(new Actor(rs_second.getInt("id_actor"), rs_second.getString("name"),
                            rs_second.getString("surname")));
                }

                return new Film(id_film, rs_first.getString("title"),
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

    /**
     * Retrieves all data associated to all Films of the database
     *
     * @return a list of Film objects
     * @throws SQLException if the connection with the database fails
     */
    @GET
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

            // First Query retrieves all data from the film table associated to the id
            statement = connection.prepareStatement("SELECT id_film, title, genre, plot, trailer, poster FROM film ORDER BY id_film");
            rs_first = statement.executeQuery();
            while (rs_first.next()) {
                id_film = rs_first.getInt("id_film");
                Blob poster = (Blob) rs_first.getBlob("poster");
                poster.free();

                // Second query retrieves from the cast and actor tables the list of Actors associated to the film
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
