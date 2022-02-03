package userAPI.microservices;

import userAPI.interfaces.DBConnection;
import userAPI.interfaces.UserInterface;
import userAPI.models.User;
import userAPI.models.UserCookie;
import userAPI.models.UserLogin;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public boolean addUser(User user) {
        db.connect();
        try (PreparedStatement stmt = db.getConnection().prepareStatement(
                "INSERT INTO user_db.user (username, email, password, role, ban) VALUES (?, ?, ?, ?, ?);"
        )) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.setInt(5, user.getBan());
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }finally {
            db.disconnect();
        }
        return true;
    }

    @POST
    @Path("/edit-user")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean editUser(User user) {
        db.connect();
        try (PreparedStatement stmt = db.getConnection().prepareStatement(
                "UPDATE user_db.user SET username = ?, email = ?, password = ?, role = ?, ban = ? WHERE id_user = ?"
        )) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.setInt(5, user.getBan());
            stmt.setInt(6, user.getId_user());
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }finally {
            db.disconnect();
        }
        return true;
    }

    @GET
    @Path("/delete-user/{id_user}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean deleteUser(@PathParam("id_user") int idUser) {
        db.connect();
        try (PreparedStatement stmt = db.getConnection().prepareStatement(
                "DELETE FROM user_db.user WHERE id_user = ?;"
        )) {
            stmt.setInt(1, idUser);
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }finally {
            db.disconnect();
        }
        return true;
    }

    @GET
    @Path("/get-user/{id_user}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User getUser(@PathParam("id_user") int idUser) {
        db.connect();
        try (PreparedStatement stmt = db.getConnection().prepareStatement("SELECT * FROM user_db.user WHERE id_user = ?;")) {
            stmt.setInt(1, idUser);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new User(rs.getInt("id_user"), rs.getString("username"),
                        rs.getString("email"), rs.getString("password"),
                        rs.getString("role"), rs.getInt("ban"));
            }
        } catch (SQLException e) {
            return null;
        }finally {
            db.disconnect();
        }
    }

    @GET
    @Path("/get-users")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<User> getUsers() {
        db.connect();
        ArrayList<User> result = new ArrayList<>();
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id_user, username, email, role, ban FROM user_db.user ORDER BY id_user;")) {
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
        }finally {
            db.disconnect();
        }
        return result;
    }

    @POST
    @Path("/login-user")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserCookie loginUser(UserLogin user) {
        db.connect();
        try (PreparedStatement stmt = db.getConnection().prepareStatement(
                "SELECT id_user, username, role FROM user_db.user WHERE (username = ? OR email = ?) AND BINARY(password) = ?;"
        )) {
            stmt.setString(1, user.getUser());
            stmt.setString(2, user.getUser());
            stmt.setString(3, user.getPassword());
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new UserCookie(rs.getInt("id_user"), rs.getString("username"), rs.getString("role"));
            }
        } catch (SQLException e) {
            return null;
        }finally {
            db.disconnect();
        }
    }


    // TODO : Se non si usano, si possono togliere
    public int getIdUser(User user) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement("SELECT * FROM user_db.user WHERE username = ? or email = ?;")) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return -1;
                }
                return rs.getInt("id_user");
            }
        } catch (SQLException e) {
            return -1;
        }
    }

    public List<User> getBannedUsers() {
        ArrayList<User> result = new ArrayList<>();
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id_user, username, email, role, ban FROM user_db.user WHERE ban = 1 " +
                     " ORDER BY id_user;")) {
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
    public boolean banUser(int idUser) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement(
                "UPDATE user_db.user SET ban = ? WHERE id_user = ?"
        )) {
            stmt.setInt(1, 1);
            stmt.setInt(2, idUser);
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    public boolean removeBanUser(int idUser) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement(
                "UPDATE user_db.user SET ban = ? WHERE id_user = ?"
        )) {
            stmt.setInt(1, 0);
            stmt.setInt(2, idUser);
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    public List<User> getNoBannedUsers() {
        ArrayList<User> result = new ArrayList<>();
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id_user, username, email, role, ban FROM user_db.user WHERE ban = 0 " +
                     " ORDER BY id_user;")) {
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
