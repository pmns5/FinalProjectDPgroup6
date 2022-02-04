package userAPI.microservices;

import userAPI.interfaces.DBConnection;
import userAPI.models.User;
import userAPI.models.UserCookie;
import userAPI.models.UserLogin;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/login")
public class UserImplementation extends DBConnection {
    public UserImplementation() {
        super();
    }

    @POST
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

    @POST
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

    @GET
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

    @GET
    @Path("/get-users")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<User> getUsers() throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet rs;
        ArrayList<User> userList = new ArrayList<>();

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("SELECT id_user, username, email, role, ban FROM user_db.user ORDER BY id_user");
            rs = statement.executeQuery();
            while (rs.next()) {
                userList.add(new User(rs.getInt("id_user"), rs.getString("username"), rs.getString("email"), rs.getString("role"), rs.getInt("ban")));
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

    @POST
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

            statement = connection.prepareStatement("SELECT id_user, username, role FROM user_db.user WHERE (username = ? OR email = ?) AND BINARY(password) = ?");
            statement.setString(1, user.getUser());
            statement.setString(2, user.getUser());
            statement.setString(3, user.getPassword());
            rs = statement.executeQuery();
            if (rs.next()) {
                return new UserCookie(rs.getInt("id_user"), rs.getString("username"), rs.getString("role"));
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

    // TODO : Se non si usano, si possono togliere
    public boolean banUser(int idUser) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement("UPDATE user_db.user SET ban = ? WHERE id_user = ?")) {
            stmt.setInt(1, 1);
            stmt.setInt(2, idUser);
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean removeBanUser(int idUser) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement("UPDATE user_db.user SET ban = ? WHERE id_user = ?")) {
            stmt.setInt(1, 0);
            stmt.setInt(2, idUser);
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public List<User> getBannedUsers() {
        ArrayList<User> result = new ArrayList<>();
        try (Statement stmt = db.getConnection().createStatement(); ResultSet rs = stmt.executeQuery("SELECT id_user, username, email, role, ban FROM user_db.user WHERE ban = 1 " + " ORDER BY id_user;")) {
            while (rs.next()) {
                int id_user = rs.getInt("id_user");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String role = rs.getString("role");
                int ban = rs.getInt("ban");
                result.add(new User(id_user, username, email, role, ban));
            }
        } catch (SQLException e) {
            return null;
        }
        return result;
    }

    public List<User> getNoBannedUsers() {
        ArrayList<User> result = new ArrayList<>();
        try (Statement stmt = db.getConnection().createStatement(); ResultSet rs = stmt.executeQuery("SELECT id_user, username, email, role, ban FROM user_db.user WHERE ban = 0 " + " ORDER BY id_user;")) {
            while (rs.next()) {
                int id_user = rs.getInt("id_user");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String role = rs.getString("role");
                int ban = rs.getInt("ban");
                result.add(new User(id_user, username, email, role, ban));
            }
        } catch (SQLException e) {
            return null;
        }
        return result;
    }
}
