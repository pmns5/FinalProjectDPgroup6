package userAPI.microservices.UserManagement;

import userAPI.interfaces.DBConnection;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;

@Path("/users")
public class UserImplementation extends DBConnection {
    public UserImplementation() {
        super();
    }

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

    @DELETE
    @Path("/delete-user/{id_user}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean deleteUser(@PathParam("id_user") int idUser) throws SQLException {
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
            statement.setInt(1, idUser);
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
    @Path("/get-user/{id_user}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User getUser(@PathParam("id_user") int idUser) throws SQLException {
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
            statement.setInt(1, idUser);
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


}
