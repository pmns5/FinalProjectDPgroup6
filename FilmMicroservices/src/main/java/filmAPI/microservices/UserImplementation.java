package filmAPI.microservices;

import filmAPI.interfaces.DBConnection;
import filmAPI.interfaces.UserInterface;
import filmAPI.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Microservice for Actor Management
 */
public class UserImplementation extends DBConnection implements UserInterface {
    public UserImplementation() {
        super();
        db.connect();
    }

    @Override
    public boolean addUser(User user) {
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "INSERT INTO user (id_user, username) VALUES (?, ?);"
        )) {
            stmt.setInt(1, user.getId());
            stmt.setString(2, user.getUsername());
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean editUser(User user) {
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "UPDATE user SET username = ? WHERE id_user = ?"
        )) {
            stmt.setString(1, user.getUsername());
            stmt.setInt(2, user.getId());
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteUser(int id_user) {
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "UPDATE user SET username = 'Canceled User' WHERE id_user = ?;"
        )) {
            stmt.setInt(1, id_user);
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public User getUser(int id_user){
        try (PreparedStatement stmt = db.getConn().prepareStatement("SELECT (username) FROM user WHERE id_user = ?;")) {
            stmt.setInt(1, id_user);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new User(id_user, rs.getString("username"));
            }
        } catch (SQLException e) {
            return null;
        }
    }
}
