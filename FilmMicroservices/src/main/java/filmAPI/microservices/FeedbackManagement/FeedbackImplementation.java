package filmAPI.microservices.FeedbackManagement;

import filmAPI.interfaces.DBConnection;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Path("/feedbacks")
public class FeedbackImplementation extends DBConnection {
    public FeedbackImplementation() {
        super();
    }

    @PUT
    @Path("/add-feedback")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean addFeedback(Feedback feedback) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("INSERT INTO feedback (id_film, id_user, score, comment, date) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, feedback.getId_film());
            statement.setInt(2, feedback.getId_user());
            statement.setFloat(3, feedback.getScore());
            statement.setString(4, feedback.getComment());
            statement.setDate(5, Date.valueOf(LocalDate.now()));
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

    @PUT
    @Path("/edit-feedback")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean editFeedback(Feedback feedback) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("UPDATE feedback SET comment = ?, score = ?, date = ? WHERE id_film = ? and id_user = ?");
            statement.setString(1, feedback.getComment());
            statement.setFloat(2, feedback.getScore());
            statement.setDate(3, Date.valueOf(LocalDate.now()));
            statement.setInt(4, feedback.getId_film());
            statement.setInt(5, feedback.getId_user());
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

    @DELETE
    @Path("/delete-feedback/{id_film}/{id_user}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public boolean deleteFeedback(@PathParam("id_film") int id_film, @PathParam("id_user") int id_user) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("DELETE FROM feedback WHERE id_film = ? AND id_user = ?");
            statement.setInt(1, id_film);
            statement.setInt(2, id_user);
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
    @Path("/get-feedback/{id_film}/{id_user}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Feedback getFeedback(@PathParam("id_film") int id_film, @PathParam("id_user") int id_user) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet rs;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("SELECT comment, score, date FROM feedback WHERE id_film = ? AND id_user = ?");
            statement.setInt(1, id_film);
            statement.setInt(2, id_user);
            rs = statement.executeQuery();
            if (rs.next()) {
                return new Feedback(id_user, id_film, rs.getString(1), rs.getFloat(2), rs.getDate(3));
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
    @Path("/get-feedback-by-film/{id_film}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Feedback> getFeedbackByFilm(@PathParam("id_film") int id_film) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet rs;
        ArrayList<Feedback> feedbackList = new ArrayList<>();

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("SELECT * FROM feedback WHERE id_film = ?");
            statement.setInt(1, id_film);
            rs = statement.executeQuery();
            while (rs.next()) {
                feedbackList.add(new Feedback(rs.getInt("id_user"), id_film, rs.getString("comment"), rs.getFloat("score"), rs.getDate("date")));
            }

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return null;
        } finally {
            connection.commit();
            db.disconnect();
        }
        return feedbackList;
    }

    @GET
    @Path("/get-feedback-by-user/{id_user}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Feedback> getFeedbackByUser(@PathParam("id_user") int id_user) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet rs;
        ArrayList<Feedback> feedbackList = new ArrayList<>();

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("SELECT * FROM feedback WHERE id_user = ?");
            statement.setInt(1, id_user);
            rs = statement.executeQuery();
            while (rs.next()) {
                feedbackList.add(new Feedback(id_user, rs.getInt("id_film"), rs.getString("comment"), rs.getFloat("score"), rs.getDate("date")));
            }

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return null;
        } finally {
            connection.commit();
            db.disconnect();
        }
        return feedbackList;
    }

    @GET
    @Path("/get-average-score/{id_film}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Float getAverageScore(@PathParam("id_film") int id_film) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet rs;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("SELECT AVG(score) FROM feedback WHERE id_film = ?");
            statement.setInt(1, id_film);
            rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getFloat(1);
            } else {
                return (float) 0;
            }

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return (float) 0;
        } finally {
            connection.commit();
            db.disconnect();
        }
    }
}
