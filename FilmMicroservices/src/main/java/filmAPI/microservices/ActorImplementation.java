package filmAPI.microservices;

import filmAPI.interfaces.ActorFilm;
import filmAPI.interfaces.DBConnection;
import filmAPI.models.Actor;

import javax.ws.rs.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Microservice for Actor Management
 */
@Path("/actors")
public class ActorImplementation extends DBConnection {
    public ActorImplementation() {
        super();
    }

    @POST
    @Path("/add-actor")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean addActor(Actor actor) {
        db.connect();
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "INSERT INTO actor (name, surname) VALUES (?, ?);"
        )) {
            stmt.setString(1, actor.getName());
            stmt.setString(2, actor.getSurname());
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }finally {
            db.disconnect();
        }
        return true;
    }


    @POST
    @Path("/edit-actor")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean editActor(Actor actor) {
        db.connect();
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "UPDATE actor SET name = ?, surname = ? WHERE id_actor = ?"
        )) {
            stmt.setString(1, actor.getName());
            stmt.setString(2, actor.getSurname());
            stmt.setInt(3, actor.getId());
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }finally {
            db.disconnect();
        }
        return true;
    }


    @GET
    @Path("/delete-actor/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean deleteActor(@PathParam("id") int id_actor) {
        db.connect();
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "DELETE FROM actor WHERE id_actor = ?;"
        )) {
            stmt.setInt(1, id_actor);
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }finally {
            db.disconnect();
        }
        return true;
    }


    @GET
    @Path("/get-actor/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Actor getOneActor(@PathParam("id") int id_actor) {
        db.connect();
        try (PreparedStatement stmt = db.getConn().prepareStatement("SELECT * FROM actor WHERE id_actor = ?;")) {
            stmt.setInt(1, id_actor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Actor a = new Actor(rs.getInt("id_actor"), rs.getString("name"), rs.getString("surname"));
                System.out.println(a);
                return a;
            }
        } catch (SQLException e) {
            return null;
        }finally {
            db.disconnect();
        }
    }


    @GET
    @Path("/get-actors")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Actor> getAllActors() {
        db.connect();
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
        }finally {
            db.disconnect();
        }
        return result;
    }


}
