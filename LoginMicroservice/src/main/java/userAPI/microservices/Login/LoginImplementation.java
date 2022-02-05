package userAPI.microservices.Login;

import userAPI.interfaces.DBConnection;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/login")
public class LoginImplementation extends DBConnection {
    public LoginImplementation() {
        super();
    }

    @PUT
    @Path("/login-user")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserCookie loginUser(UserLogin user) throws SQLException {
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

            statement = connection.prepareStatement("SELECT id_user, username, role, ban FROM user_db.user WHERE (username = ? OR email = ?) AND BINARY(password) = ?");
            statement.setString(1, user.getUser());
            statement.setString(2, user.getUser());
            statement.setString(3, user.getPassword());
            rs = statement.executeQuery();
            if (rs.next()) {
                return new UserCookie(rs.getInt("id_user"), rs.getString("username"), rs.getString("role"), rs.getInt("ban"));
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
    @Path("/get-no-banned-users")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<UserCookie> getNoBannedUsers() throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet rs;
        ArrayList<UserCookie> userList = new ArrayList<>();

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("SELECT id_user, username, role, ban FROM user_db.user WHERE ban=0 and role!='admin' ORDER BY id_user");
            rs = statement.executeQuery();
            while (rs.next()) {
                userList.add(new UserCookie(rs.getInt("id_user"), rs.getString("username"), rs.getString("role"), rs.getInt("ban")));
            }

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return null;
        } finally {
            connection.commit();
            db.disconnect();
        }
        return userList;
    }

    @GET
    @Path("/get-users")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<UserCookie> getUsers() throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet rs;
        ArrayList<UserCookie> userList = new ArrayList<>();

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("SELECT id_user, username, role, ban FROM user_db.user WHERE role!='admin' ORDER BY id_user");
            rs = statement.executeQuery();
            while (rs.next()) {
                userList.add(new UserCookie(rs.getInt("id_user"), rs.getString("username"), rs.getString("role"), rs.getInt("ban")));
            }

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return null;
        } finally {
            connection.commit();
            db.disconnect();
        }
        return userList;
    }

}
