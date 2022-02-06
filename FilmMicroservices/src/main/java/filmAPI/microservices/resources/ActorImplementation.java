package filmAPI.microservices.resources;

import filmAPI.interfaces.DBConnection;
import filmAPI.microservices.beans.Actor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;

/**
 * Resource class for managing the actors
 */
@Path("/actors")
public class ActorImplementation extends DBConnection {
    public ActorImplementation() {
        super();
    }

    /**
     * Insert a new Actor inside the database
     * @param actor - the Actor to be added
     * @return true if the insertion is completed correctly, false otherwise
     * @throws SQLException if the connection with the database fails
     */
    @PUT
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

    /**
     * Edit the Actor data inside the database
     * @param actor - the Actor to be edited
     * @return true if the edit is completed correctly, false otherwise
     * @throws SQLException if the connection with the database fails
     */
    @PUT
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

    /**
     * Delete the Actor associated to the given id
     * @param id_actor - the id of the actor to delete
     * @return true if the deletion is completed correctly, false otherwise
     * @throws SQLException if the connection with the database fails
     */
    @DELETE
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

    /**
     * Returns the Actor data associated to the passed id
     * @param id_actor - the id of the actor to retrieve
     * @return the Actor object containing all the data of the Actor
     * @throws SQLException if the connection with the database fails
     */
    @GET
    @Path("/get-actor/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Actor getActor(@PathParam("id") int id_actor) throws SQLException {
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

    /**
     * Retrieves all data associated to all Actors of the database
     * @return a list of Actor objects
     * @throws SQLException if the connection with the database fails
     */
    @GET
    @Path("/get-actors")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Actor> getActors() throws SQLException {
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
