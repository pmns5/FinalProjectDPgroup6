package filmAPI.interfaces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDb implements DbInterface {
    private static final String url = "jdbc:mysql://localhost:3306";
    private static final String extra = "/film_db?useTimezone=true&serverTimezone=UTC";
    private static final String userDb = "root";
    private static final String password = "root";
    private Connection connection;
    private boolean connected;

    /**
     * Constructor to properly initialize the MySQL DataBase
     */
    public MySQLDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ignored) {
        }
        this.connected = false;
    }

    @Override
    public void connect() {
        if (!connected) {
            try {
                connection = DriverManager.getConnection(url + extra, userDb, password);
            } catch (SQLException e) {
                System.exit(99);
                return;
            }
            connected = true;
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
