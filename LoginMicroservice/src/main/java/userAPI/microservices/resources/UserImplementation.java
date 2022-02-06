package userAPI.microservices.resources;

import userAPI.interfaces.DBConnection;
import userAPI.microservices.beans.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;

/**
 * Resource class for managing the users
 */
@Path("/users")
public class UserImplementation extends DBConnection {
    public UserImplementation() {
        super();
    }

    /**
     * Insert a new User inside the database
     * @param user - the User to be added
     * @return true if the insertion is completed correctly, false otherwise
     * @throws SQLException if the connection with the database fails
     */
    @PUT
    @Path("/add-user")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean addUser(User user) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("INSERT INTO user_db.user (username, email, password, role, ban) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            statement.setInt(5, user.getBan());
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
     * Edit the User data inside the database
     * @param user - the User to be edited
     * @return true if the edit is completed correctly, false otherwise
     * @throws SQLException if the connection with the database fails
     */
    @PUT
    @Path("/edit-user")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean editUser(User user) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("UPDATE user_db.user SET username = ?, email = ?, password = ?, role = ?, ban = ? WHERE id_user = ?");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            statement.setInt(5, user.getBan());
            statement.setInt(6, user.getId_user());
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
     * Delete the User associated to the given id
     * @param id_user - the id of the user to delete
     * @return true if the deletion is completed correctly, false otherwise
     * @throws SQLException if the connection with the database fails
     */
    @DELETE
    @Path("/delete-user/{id_user}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean deleteUser(@PathParam("id_user") int id_user) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("DELETE FROM user_db.user WHERE id_user = ?");
            statement.setInt(1, id_user);
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
     * Returns the User data associated to the passed id
     * @param id_user - the id of the user to retrieve
     * @return the User object containing all the data of the user
     * @throws SQLException if the connection with the database fails
     */
    @GET
    @Path("/get-user/{id_user}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User getUser(@PathParam("id_user") int id_user) throws SQLException {
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

            statement = connection.prepareStatement("SELECT * FROM user_db.user WHERE id_user = ?");
            statement.setInt(1, id_user);
            rs = statement.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id_user"), rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getString("role"), rs.getInt("ban"));
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
     * Changes the status of the user, from banned to unbanned and viceversa
     * @param id_user the id of the user target
     * @param currentStatus the current status
     * @return true if the toggle is completed correctly, false otherwise
     * @throws SQLException if the connection with the database fails
     */
    @PUT
    @Path("/toggle-ban-user/{currentStatus}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean toggleBan(int id_user, @PathParam("currentStatus") int currentStatus) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("UPDATE user_db.user SET ban = ? WHERE id_user = ?");
            statement.setInt(1, currentStatus == 1 ? 0 : 1);
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
     * Changes the role of the user, from client to manager and viceversa
     * @param id_user the id of the user target
     * @param currentRole the current role of the user
     * @return true if the toggle is completed correctly, false otherwise
     * @throws SQLException if the connection with the database fails
     */
    @PUT
    @Path("/toggle-role-user/{currentRole}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean toggleRole(int id_user, @PathParam("currentRole") String currentRole) throws SQLException {
        // Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("UPDATE user_db.user SET role = ? WHERE id_user = ?");
            statement.setString(1, currentRole.equals("client") ? "manager" : "client");
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


}
