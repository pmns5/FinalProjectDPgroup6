package filmAPI.microservices;

import filmAPI.interfaces.DBConnection;
import filmAPI.models.Feedback;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

@Path("/feedbacks")
public class FeedbackImplementation extends DBConnection {

    public FeedbackImplementation(){
        super();
    }

    @POST
    @Path("/add-feedback")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean addFeedback(Feedback feedback) {
        db.connect();
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "INSERT INTO feedback (id_film, id_user, score, comment, date) VALUES (?, ?, ?, ?, ?)"
        )) {
            stmt.setInt(1, feedback.getId_film());
            stmt.setInt(2, feedback.getId_user());
            stmt.setFloat(3, feedback.getScore());
            stmt.setString(4, feedback.getComment());
            stmt.setDate(5, Date.valueOf(LocalDate.now()));

            stmt.execute();
        } catch (SQLException e) {
            return false;
        }finally {
            db.disconnect();
        }
        return true;
    }

    @POST
    @Path("/edit-feedback")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean editFeedback(Feedback feedback) {
        db.connect();
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "UPDATE feedback SET comment = ?, score = ?, date = ? WHERE id_film = ? and id_user = ?"
        )) {
            stmt.setString(1, feedback.getComment());
            stmt.setFloat(2, feedback.getScore());
            stmt.setDate(3, Date.valueOf(LocalDate.now()));
            stmt.setInt(4, feedback.getId_film());
            stmt.setInt(5, feedback.getId_user());
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }finally {
            db.disconnect();
        }
        return true;
    }

    @GET
    @Path("/delete-feedback/{id_film}/{id_user}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public boolean deleteFeedback(@PathParam("id_film") int id_film, @PathParam("id_user") int id_user) {
        db.connect();
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "DELETE FROM feedback WHERE id_film = ? AND id_user = ?"
        )) {
            stmt.setInt(1, id_film);
            stmt.setInt(2, id_user);
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }finally {
            db.disconnect();
        }
        return true;
    }

    @GET
    @Path("/get-feedback/{id_film}/{id_user}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Feedback getOneFeedback(@PathParam("id_film") int id_film, @PathParam("id_user") int id_user) {
        db.connect();
        try (PreparedStatement stmt = db.getConn().prepareStatement("SELECT score, comment, date FROM feedback" +
                " WHERE id_film = ? AND id_user=?")) {
            stmt.setInt(1, id_film);
            stmt.setInt(2, id_user);
            ResultSet rs = stmt.executeQuery();
            if (rs != null) {
                rs.next();
                float score = rs.getFloat(1);
                String comment = rs.getString(2);
                Date date = rs.getDate(3);
                return new Feedback(id_user, id_film, comment, score, date);
            }
        } catch (SQLException ignored) {
        }finally {
            db.disconnect();
        }
        return null;
    }


    // TODO : Rendere Query
    @GET
    @Path("/get-by-film/{id_film}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Feedback> getByFilm(@PathParam("id_film") int id_film) {
        db.connect();
        ArrayList<Feedback> result = new ArrayList<>();
        try (PreparedStatement stmt = db.getConn().prepareStatement("SELECT * FROM feedback WHERE id_film = ?")) {
            stmt.setInt(1, id_film);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id_user = rs.getInt("id_user");
                    String comment = rs.getString("comment");
                    Float score = rs.getFloat("score");
                    Date date = rs.getDate("date");
                    result.add(new Feedback(id_user, id_film, comment, score, date));
                }
            }
        } catch (SQLException e) {
            return null;
        }finally {
            db.disconnect();
        }
        return result;
    }

    @GET
    @Path("/get-by-user/{id_user}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Feedback> getByUser(@PathParam("id_user") int id_user) {
        db.connect();

        ArrayList<Feedback> result = new ArrayList<>();
        try (PreparedStatement stmt = db.getConn().prepareStatement("SELECT * FROM feedback WHERE id_user = ?")) {
            stmt.setInt(1, id_user);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id_film = rs.getInt("id_film");
                    String comment = rs.getString("comment");
                    Float score = rs.getFloat("score");
                    Date date = rs.getDate("date");
                    result.add(new Feedback(id_user, id_film, comment, score, date));
                }
            }
        } catch (SQLException e) {
            return null;
        }finally {
            db.disconnect();
        }
        return result;
    }

    @GET
    @Path("/get-average-score/{id_film}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Float getAverageScore(@PathParam("id_film") int id_film) {
        db.connect();
        try (PreparedStatement stmt = db.getConn().prepareStatement("SELECT AVG(score) FROM feedback WHERE id_film = ?")) {
            stmt.setInt(1, id_film);
            ResultSet rs = stmt.executeQuery();
            if (rs != null) {
                rs.next();
                return rs.getFloat(1);
            }
        } catch (SQLException ignored) {
        }finally {
            db.disconnect();
        }
        return (float) 0;
    }


}
