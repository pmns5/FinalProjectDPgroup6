package dp.project;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlDbConnection {
    private String dbDriver = "jdbc:mysql";
    private String dbHost = "localhost";
    private String dbPort = "3306";
    private String dbUser;
    private String dbPassword;
    private String dbName;
    private String dbTimeZone = "Europe/Rome";

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public String getDbPort() {
        return dbPort;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbTimeZone() {
        return dbTimeZone;
    }

    public void setDbTimeZone(String dbTimeZone) {
        this.dbTimeZone = dbTimeZone;
    }

    public Connection connect() {
        String dbConnectionUrl = this.dbDriver + "://"
                + this.dbHost + ":"
                + this.dbPort + "/"
                + dbName
                + "?serverTimezone=" + dbTimeZone;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(dbConnectionUrl, dbUser, dbPassword);
            return con;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
