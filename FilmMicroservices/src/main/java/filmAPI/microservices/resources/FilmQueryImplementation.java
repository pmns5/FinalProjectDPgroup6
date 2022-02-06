package filmAPI.microservices.resources;

import com.mysql.cj.jdbc.Blob;
import filmAPI.EnumGenre;
import filmAPI.interfaces.DBConnection;
import filmAPI.microservices.beans.Actor;
import filmAPI.microservices.beans.Feedback;
import filmAPI.microservices.beans.HomePageFilm;
import filmAPI.microservices.beans.ReviewPageFilm;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Resource class for querying the database about aggregated film data
 */
@Path("/query")
public class FilmQueryImplementation extends DBConnection {
    public FilmQueryImplementation() {
        super();
    }

    /**
     * Retrieves a list of HomePageFilms
     * @return the list of HomePageFilms
     * @throws SQLException if the connection with the database fails
     */
    @GET
    @Path("/get-films-home-page")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<HomePageFilm> getFilmsHomePage() throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet rs_first, rs_second, rs_third;
        List<Actor> actorList;
        float average;
        List<HomePageFilm> listFilms = new ArrayList<>();

        //Execute transaction
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            // First query retrieves the list of films
            statement = connection.prepareStatement("SELECT id_film, title, genre, plot, trailer, poster FROM film ORDER BY id_film");
            rs_first = statement.executeQuery();
            while (rs_first.next()) {
                int id_film = rs_first.getInt(1);
                Blob poster = (Blob) rs_first.getBlob(6);
                byte[] posterBytes = poster.getBytes(1, (int) poster.length());
                poster.free();
                actorList = new ArrayList<>();

                // Second query retrieves all actors data associated to the particular film
                statement = connection.prepareStatement("SELECT a.id_actor, a.name, a.surname FROM film join cast c on film.id_film = c.id_film join actor a on a.id_actor = c.id_actor AND film.id_film = " + id_film);
                rs_second = statement.executeQuery();
                while (rs_second.next()) {
                    actorList.add(new Actor(rs_second.getInt("id_actor"), rs_second.getString("name"), rs_second.getString("surname")));
                }

                // Third query retrieves the average score associated to the given film
                statement = connection.prepareStatement("SELECT AVG(score) from feedback WHERE id_film = " + id_film);
                rs_third = statement.executeQuery();
                average = 0;
                if (rs_third.next()) {
                    average = rs_third.getFloat(1);
                }
                listFilms.add(new HomePageFilm(id_film, rs_first.getString("title"),
                        EnumGenre.valueOf(rs_first.getString("genre")), rs_first.getString("plot"),
                        rs_first.getString("trailer"), posterBytes, actorList, average));
            }

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return null;
        } finally {
            connection.commit();
            db.disconnect();
        }
        return listFilms;
    }

    /**
     * Retrieves a list of HomePageFilm associated to the specific genre
     * @param genre - the genre of the film to retrieve
     * @return the list of HomePageFilms
     * @throws SQLException if the connection with the database fails
     */
    @GET
    @Path("/get-films-home-page-per-genre/{genre}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<HomePageFilm> getFilmsHomePagePerGenre(@PathParam("genre") String genre) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet rs_first, rs_second, rs_third;
        List<Actor> actorList;
        float average;
        List<HomePageFilm> listFilms = new ArrayList<>();

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            // First query retrieves the target list of films
            statement = connection.prepareStatement("SELECT id_film, title, genre, plot, trailer, poster FROM film WHERE genre = ? ORDER BY id_film");
            statement.setString(1, genre);
            rs_first = statement.executeQuery();
            while (rs_first.next()) {
                int id_film = rs_first.getInt(1);
                Blob poster = (Blob) rs_first.getBlob(6);
                byte[] posterBytes = poster.getBytes(1, (int) poster.length());
                poster.free();

                // Second query retrieves all actors data associated to the particular film
                actorList = new ArrayList<>();
                statement = connection.prepareStatement("SELECT a.id_actor, a.name, a.surname FROM film join cast c on film.id_film = c.id_film join actor a on a.id_actor = c.id_actor AND film.id_film = " + id_film);
                rs_second = statement.executeQuery();
                while (rs_second.next()) {
                    actorList.add(new Actor(rs_second.getInt("id_actor"), rs_second.getString("name"), rs_second.getString("surname")));
                }

                // Third query retrieves the average score associated to the given film
                statement = connection.prepareStatement("SELECT AVG(score) from feedback WHERE id_film = " + id_film);
                rs_third = statement.executeQuery();
                average = 0;
                if (rs_third.next()) {
                    average = rs_third.getFloat(1);
                }
                listFilms.add(new HomePageFilm(id_film, rs_first.getString("title"),
                        EnumGenre.valueOf(rs_first.getString("genre")), rs_first.getString("plot"),
                        rs_first.getString("trailer"), posterBytes, actorList, average));
            }

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return null;
        } finally {
            connection.commit();
            db.disconnect();
        }
        return listFilms;
    }

    /**
     * Retrieves the ReviewPageFilm associated to the specified id
     * @param id_film - the id of the film to retrieve
     * @return a ReviewPageFilm object
     * @throws SQLException if the connection with the database fails
     */
    @GET
    @Path("/get-film-review-page/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ReviewPageFilm getFilmReviewPage(@PathParam("id") int id_film) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet rs_first, rs_second, rs_third, rs_fourth;
        List<Feedback> feedbackList;
        List<Actor> actorList;
        float average;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            // First query retrieves the film
            statement = connection.prepareStatement("SELECT title, plot, genre, trailer, poster FROM film WHERE film.id_film = ?");
            statement.setInt(1, id_film);
            rs_first = statement.executeQuery();
            if (rs_first.next()) {
                Blob poster = (Blob) rs_first.getBlob("poster");
                byte[] posterBytes = poster.getBytes(1, (int) poster.length());
                poster.free();

                // Second query retrieves all actors data associated to the particular film
                statement = connection.prepareStatement("SELECT a.id_actor, a.name, a.surname FROM cast c join actor a on a.id_actor = c.id_actor where c.id_film=?;");
                statement.setInt(1, id_film);
                rs_third = statement.executeQuery();
                actorList = new ArrayList<>();
                while (rs_third.next()) {
                    actorList.add(new Actor(rs_third.getInt("id_actor"), rs_third.getString("name"), rs_third.getString("surname")));
                }

                // Third query retrieves the average score associated to the given film
                statement = connection.prepareStatement("SELECT AVG(score) FROM feedback WHERE id_film = ?");
                statement.setInt(1, id_film);
                rs_fourth = statement.executeQuery();
                average = 0;
                if (rs_fourth.next()) {
                    average = rs_fourth.getFloat(1);
                }

                // Fourth query retrieves all feedbacks associated to the given film
                statement = connection.prepareStatement("SELECT * FROM feedback where id_film = ?");
                statement.setInt(1, id_film);
                rs_second = statement.executeQuery();
                feedbackList = new ArrayList<>();
                while (rs_second.next()) {
                    feedbackList.add(new Feedback(rs_second.getInt("id_user"), id_film, rs_second.getString("comment"), rs_second.getFloat("score"), rs_second.getDate("date")));
                }


                return new ReviewPageFilm(id_film, rs_first.getString("title"),
                        EnumGenre.valueOf(rs_first.getString("genre")), rs_first.getString("plot"),
                        rs_first.getString("trailer"), posterBytes, actorList, average, feedbackList);
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
}
