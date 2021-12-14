package filmAPI.microservices;

import filmAPI.interfaces.ActorFilm;
import filmAPI.interfaces.DBConnection;
import filmAPI.models.Actor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Microservice for Actor Management
 */
public class ActorImplementation extends DBConnection implements ActorFilm {

    public ActorImplementation() {
        super();
        db.connect();
    }

    @Override
    public boolean addActor(Actor actor) {
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "INSERT INTO actor (name, surname) VALUES (?, ?);"
        )) {
            stmt.setString(1, actor.getName());
            stmt.setString(2, actor.getSurname());
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean editActor(Actor actor) {
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "UPDATE actor SET name = ?, surname = ? WHERE id_actor = ?"
        )) {
            stmt.setString(1, actor.getName());
            stmt.setString(2, actor.getSurname());
            stmt.setInt(3, actor.getId());
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteActor(int id_actor) {
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "DELETE FROM actor WHERE id_actor = ?;"
        )) {
            stmt.setInt(1, id_actor);
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public Actor getOneActor(int id_actor) {
        try (PreparedStatement stmt = db.getConn().prepareStatement("SELECT * FROM actor WHERE id_actor = ?;")) {
            stmt.setInt(1, id_actor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new Actor(rs.getInt("id_actor"), rs.getString("name"),
                        rs.getString("surname"));
            }
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public ArrayList<Actor> getAllActors() {
        ArrayList<Actor> result = new ArrayList<>();
        try (Statement stmt = db.getConn().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM actor ORDER BY id_actor;")) {
            while (rs.next()) {
                int id_actor = rs.getInt("id_actor");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                result.add(new Actor(id_actor, name, surname));
            }
        } catch (SQLException e) {
            return null;
        }
        return result;
    }

}
