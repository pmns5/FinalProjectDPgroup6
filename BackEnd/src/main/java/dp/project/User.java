package dp.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {

    private final Connection con;
    public int dbOperationStatusCode;
    public String dbOperationMessage;

    private String username;
    private String email;
    private String password;

    public User(MySqlDbConnection db) {
        this.con = db.connect();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResults() {
        String inProgressBrowseJSONFormat = "{\"result_code\":\"{code}\"}";
        String JSONRow = inProgressBrowseJSONFormat.replace("{code}", this.getDbOperationMessage());
        return "[" + JSONRow + "]";
    }

    public void select() {
        try {
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS WHERE username=" + this.username);
            if (rs.next()) {
                this.username = rs.getString(1);
                this.email = rs.getString(2);
                this.password = rs.getString(3);
                this.dbOperationStatusCode = 0;
                this.dbOperationMessage = "Record selected";
            }
        } catch (Exception e) {
            this.dbOperationStatusCode = 1;
            this.dbOperationMessage = "Record not selected: " + e.getMessage();
        }

    }

    public void add() {
        try {
            String query = "INSERT INTO USERS (username, email, passw) VALUES (?,?,?)";
            PreparedStatement preparedStmt = this.con.prepareStatement(query);
            preparedStmt.setString(1, this.username);
            preparedStmt.setString(2, this.email);
            preparedStmt.setString(3, this.password);
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
            String query = "DELETE FROM USERS WHERE username = ?";
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
