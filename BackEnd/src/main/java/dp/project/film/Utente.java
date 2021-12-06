package dp.project.film;

import dp.project.MySqlDbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Utente {
    private final Connection con;
    public int dbOperationStatusCode;
    public String dbOperationMessage;
    private String username;

    public Utente(MySqlDbConnection db) {
        this.con = db.connect();
    }

    public Connection getCon() {
        return con;
    }

    public int getDbOperationStatusCode() {
        return dbOperationStatusCode;
    }

    public void setDbOperationStatusCode(int dbOperationStatusCode) {
        this.dbOperationStatusCode = dbOperationStatusCode;
    }

    public String getDbOperationMessage() {
        return dbOperationMessage;
    }

    public void setDbOperationMessage(String dbOperationMessage) {
        this.dbOperationMessage = dbOperationMessage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void add() {
        try {
            String query = "INSERT INTO utente (username) VALUES (?)";
            PreparedStatement preparedStmt = this.con.prepareStatement(query);
            preparedStmt.setString(1, this.username);
            preparedStmt.execute();
            this.dbOperationStatusCode = 0;
            this.dbOperationMessage = "Record added";
        } catch (Exception e) {
            this.dbOperationStatusCode = 1;
            this.dbOperationMessage = "Record not added: " + e.getMessage();
        }
    }

    public void delete() {
        try {
            String query = "DELETE FROM utente WHERE username = ?";
            PreparedStatement preparedStmt = this.con.prepareStatement(query);
            preparedStmt.setString(1, this.username);
            preparedStmt.execute();
            this.dbOperationStatusCode = 0;
            this.dbOperationMessage = "Record deleted";
        } catch (Exception e) {
            this.dbOperationStatusCode = 1;
            this.dbOperationMessage = "Record not deleted: " + e.getMessage();
        }
    }
}
