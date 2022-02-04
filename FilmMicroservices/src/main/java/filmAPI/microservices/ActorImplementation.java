package filmAPI.microservices;

import filmAPI.interfaces.DBConnection;
import filmAPI.models.Actor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;

@Path("/actors")
public class ActorImplementation extends DBConnection {
    public ActorImplementation() {
        super();
    }

    @POST
    @Path("/add-actor")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean addActor(Actor actor) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("INSERT INTO actor (name, surname) VALUES (?, ?)");
            statement.setString(1, actor.getName());
            statement.setString(2, actor.getSurname());
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

    @POST
    @Path("/edit-actor")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean editActor(Actor actor) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("UPDATE actor SET name = ?, surname = ? WHERE id_actor = ?");
            statement.setString(1, actor.getName());
            statement.setString(2, actor.getSurname());
            statement.setInt(3, actor.getId());
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
    @Path("/delete-actor/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean deleteActor(@PathParam("id") int id_actor) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("DELETE FROM actor WHERE id_actor = ?");
            statement.setInt(1, id_actor);
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
    @Path("/get-actor/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Actor getOneActor(@PathParam("id") int id_actor) throws SQLException {
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

            statement = connection.prepareStatement("SELECT * FROM actor WHERE id_actor = ?");
            statement.setInt(1, id_actor);
            rs = statement.executeQuery();
            if (rs.next()) {
                return new Actor(rs.getInt("id_actor"), rs.getString("name"), rs.getString("surname"));
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
    @Path("/get-actors")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Actor> getAllActors() throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet rs;
        ArrayList<Actor> actorList = new ArrayList<>();

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("SELECT * FROM actor ORDER BY id_actor");
            rs = statement.executeQuery();
            while (rs.next()) {
                actorList.add(new Actor(rs.getInt("id_actor"), rs.getString("name"), rs.getString("surname")));
            }

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return null;
        } finally {
            connection.commit();
            db.disconnect();
        }
        return actorList;
    }
}
