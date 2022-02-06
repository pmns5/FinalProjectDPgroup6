package filmAPI.microservices.resources;

import filmAPI.interfaces.DBConnection;
import filmAPI.microservices.beans.Feedback;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Resource class for managing the feedbacks
 */
@Path("/feedbacks")
public class FeedbackImplementation extends DBConnection {
    public FeedbackImplementation() {
        super();
    }

    /**
     * Insert a new Feedback inside the database
     * @param feedback - the Feedback to be added
     * @return true if the insertion is completed correctly, false otherwise
     * @throws SQLException if the connection with the database fails
     */
    @POST
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

    /**
     * Edit the Feedback data inside the database
     * @param feedback - the Feedback to be edited
     * @return true if the edit is completed correctly, false otherwise
     * @throws SQLException if the connection with the database fails
     */
    @PUT
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

    /**
     * Delete the Feedback of the specified user regarding the specified film
     * @param id_film - the id of the film the feedback refers to
     * @param id_user - the id of the user who made the feedback
     * @return true if the deletion is completed correctly, false otherwise
     * @throws SQLException if the connection with the database fails
     */
    @DELETE
    @Path("/{id_film}/{id_user}")
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

    /**
     * Retrieves the Feedback of the specified user regarding the specified film
     * @param id_film - the id of the film the feedback refers to
     * @param id_user - the id of the user who made the feedback
     * @return the Feedback object
     * @throws SQLException if the connection with the database fails
     */
    @GET
    @Path("/{id_film}/{id_user}")
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

    /**
     * Retrieves all data associated to all Feedbacks of the database associated to the specified film
     * @param id_film - the id of the film the feedbacks refer to
     * @return a list of Feedback objects
     * @throws SQLException if the connection with the database fails
     */
    @GET
    @Path("/film/{id_film}")
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

    /**
     * Retrieves all data associated to all Feedbacks of the database associated to the specified user
     * @param id_user - the id of the user who made the feedback
     * @return a list of Feedback objects
     * @throws SQLException if the connection with the database fails
     */
    @GET
    @Path("/user/{id_user}")
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
}
