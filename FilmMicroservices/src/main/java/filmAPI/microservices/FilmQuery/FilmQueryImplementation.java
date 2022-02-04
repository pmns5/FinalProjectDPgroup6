package filmAPI.microservices.FilmQuery;

import com.mysql.cj.jdbc.Blob;
import filmAPI.EnumGenre;
import filmAPI.interfaces.DBConnection;
import filmAPI.microservices.ActorManagement.Actor;
import filmAPI.microservices.FeedbackManagement.Feedback;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/query")
public class FilmQueryImplementation extends DBConnection {
    public FilmQueryImplementation() {
        super();
    }

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

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("SELECT id_film, title, genre, plot, trailer, poster FROM film ORDER BY id_film");
            rs_first = statement.executeQuery();
            while (rs_first.next()) {
                int id_film = rs_first.getInt(1);
                Blob poster = (Blob) rs_first.getBlob(6);
                byte[] posterBytes = poster.getBytes(1, (int) poster.length());
                poster.free();
                actorList = new ArrayList<>();
                statement = connection.prepareStatement("SELECT a.id_actor, a.name, a.surname FROM film join cast c on film.id_film = c.id_film join actor a on a.id_actor = c.id_actor AND film.id_film = " + id_film);
                rs_second = statement.executeQuery();
                while (rs_second.next()) {
                    actorList.add(new Actor(rs_second.getInt("id_actor"), rs_second.getString("name"), rs_second.getString("surname")));
                }

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

    @GET
    @Path("/get-films-home-page-per-genre")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<HomePageFilm> getFilmsHomePagePerGenre(String genre) throws SQLException {
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

            statement = connection.prepareStatement("SELECT id_film, title, genre, plot, trailer, poster FROM film WHERE genre = ? ORDER BY id_film");
            statement.setString(1, genre);
            rs_first = statement.executeQuery();
            while (rs_first.next()) {
                int id_film = rs_first.getInt(1);
                Blob poster = (Blob) rs_first.getBlob(6);
                byte[] posterBytes = poster.getBytes(1, (int) poster.length());
                poster.free();
                actorList = new ArrayList<>();
                statement = connection.prepareStatement("SELECT a.id_actor, a.name, a.surname FROM film join cast c on film.id_film = c.id_film join actor a on a.id_actor = c.id_actor AND film.id_film = " + id_film);
                rs_second = statement.executeQuery();
                while (rs_second.next()) {
                    actorList.add(new Actor(rs_second.getInt("id_actor"), rs_second.getString("name"), rs_second.getString("surname")));
                }

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

            statement = connection.prepareStatement("SELECT title, plot, genre, trailer, poster FROM film WHERE film.id_film = ?");
            statement.setInt(1, id_film);
            rs_first = statement.executeQuery();
            if (rs_first.next()) {
                Blob poster = (Blob) rs_first.getBlob("poster");
                byte[] posterBytes = poster.getBytes(1, (int) poster.length());
                poster.free();

                statement = connection.prepareStatement("SELECT * FROM feedback where id_film = ?");
                statement.setInt(1, id_film);
                rs_second = statement.executeQuery();
                feedbackList = new ArrayList<>();
                while (rs_second.next()) {
                    feedbackList.add(new Feedback(rs_second.getInt("id_user"), id_film, rs_second.getString("comment"), rs_second.getFloat("score"), rs_second.getDate("date")));
                }

                statement = connection.prepareStatement("SELECT a.id_actor, a.name, a.surname FROM cast c join actor a on a.id_actor = c.id_actor where c.id_film=?;");
                statement.setInt(1, id_film);
                rs_third = statement.executeQuery();
                actorList = new ArrayList<>();
                while (rs_third.next()) {
                    actorList.add(new Actor(rs_third.getInt("id_actor"), rs_third.getString("name"), rs_third.getString("surname")));
                }

                statement = connection.prepareStatement("SELECT AVG(score) FROM feedback WHERE id_film = ?");
                statement.setInt(1, id_film);
                rs_fourth = statement.executeQuery();
                average = 0;
                if (rs_fourth.next()) {
                    average = rs_fourth.getFloat(1);
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
