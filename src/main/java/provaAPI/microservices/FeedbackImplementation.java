package provaAPI.microservices;

import provaAPI.interfaces.DBConnection;
import provaAPI.interfaces.FeedbackFilm;
import provaAPI.models.Actor;
import provaAPI.models.Feedback;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FeedbackImplementation extends DBConnection implements FeedbackFilm {


    @Override
    public boolean addFeedback(Feedback feedback) {
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "INSERT INTO feedback (id_film, id_user, score, comment) VALUES (?, ?, ?, ?)"
        )) {
            stmt.setInt(1, feedback.getId_film());
            stmt.setInt(2, feedback.getId_user());
            stmt.setFloat(3, feedback.getScore());
            stmt.setString(4, feedback.getComment());
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean editFeedback(Feedback feedback) {
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "UPDATE feedback SET comment = ?, score = ? WHERE id_film = ? and id_user = ?"
        )) {
            stmt.setString(1, feedback.getComment());
            stmt.setFloat(2, feedback.getScore());
            stmt.setInt(3, feedback.getId_film());
            stmt.setInt(3, feedback.getId_user());
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteFeedback(Feedback feedback) {
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "DELETE FROM feedback WHERE id_film = ? AND id_user = ?"
        )) {
            stmt.setInt(1, feedback.getId_film());
            stmt.setInt(1, feedback.getId_user());
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<Feedback> getByFilm(int id_film) {
        ArrayList<Feedback> result = new ArrayList<>();
        try (PreparedStatement stmt = db.getConn().prepareStatement("SELECT * FROM feedback WHERE id_film = ?")) {
            stmt.setInt(1, id_film);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id_user = rs.getInt("id_user");
                    String comment = rs.getString("comment");
                    Float score = rs.getFloat("score");
                    result.add(new Feedback(id_user, id_film, comment, score));
                }
            }
        } catch (SQLException e) {
            return null;
        }
        return result;
    }

    @Override
    public ArrayList<Feedback> getByUser(int id_user) {
        ArrayList<Feedback> result = new ArrayList<>();
        try (PreparedStatement stmt = db.getConn().prepareStatement("SELECT * FROM feedback WHERE id_user = ?")) {
            stmt.setInt(1, id_user);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id_film = rs.getInt("id_film");
                    String comment = rs.getString("comment");
                    Float score = rs.getFloat("score");
                    result.add(new Feedback(id_user, id_film, comment, score));
                }
            }
        } catch (SQLException e) {
            return null;
        }
        return result;
    }

    @Override
    public Float getAverageScore(int id_film) {
        try (PreparedStatement stmt = db.getConn().prepareStatement("SELECT AVG(score) FROM feedback WHERE id_film = ?")) {
            stmt.setInt(1, id_film);
            ResultSet rs = stmt.executeQuery();
            if (rs != null) {
                rs.next();
                return rs.getFloat(0);
            }

        } catch (SQLException ignored) {

        }
        return (float) -1;
    }
}
