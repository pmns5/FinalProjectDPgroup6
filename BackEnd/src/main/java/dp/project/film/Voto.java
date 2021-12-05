package dp.project.film;

import dp.project.MySqlDbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Voto {

    private final Connection con;
    public int dbOperationStatusCode;
    public String dbOperationMessage;

    private int film;
    private String username;
    private float voto;
    private String commento;


    public Voto(MySqlDbConnection db) {
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

    public int getFilm() {
        return film;
    }

    public void setFilm(int film) {
        this.film = film;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getVoto() {
        return voto;
    }

    public void setVoto(float voto) {
        this.voto = voto;
    }

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    public String getCRUDResultToJSON() {
        String CRUDJSONFormat = "{\"errno\":\"{CODE}\",\"msg\":\"{MESSAGE}\"}";
        String CRUDJSONResult;
        CRUDJSONResult = CRUDJSONFormat.replace("{CODE}", String.valueOf(this.dbOperationStatusCode));
        CRUDJSONResult = CRUDJSONResult.replace("{MESSAGE}", this.dbOperationMessage);
        return CRUDJSONResult;
    }

    public void add() {
        try {
            String query = "INSERT INTO voto (film, username, voto, commento) VALUES (?,?,?,?)";
            PreparedStatement preparedStmt = this.con.prepareStatement(query);
            preparedStmt.setInt(1, this.film);
            preparedStmt.setString(2, this.username);
            preparedStmt.setFloat(3, this.voto);
            preparedStmt.setString(3, this.commento);
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
            String query = "DELETE FROM voto WHERE film = ? AND username = ?";
            PreparedStatement preparedStmt = this.con.prepareStatement(query);
            preparedStmt.setInt(1, this.film);
            preparedStmt.setString(2, this.username);
            preparedStmt.execute();
            this.dbOperationStatusCode = 0;
            this.dbOperationMessage = "Record deleted";
        } catch (Exception e) {
            this.dbOperationStatusCode = 1;
            this.dbOperationMessage = "Record not deleted: " + e.getMessage();
        }
    }

    public void updateVoto() {
        try {
            String query = "UPDATE voto SET voto = ?" + " WHERE film = ? and username = ?";
            PreparedStatement preparedStmt = this.con.prepareStatement(query);
            preparedStmt.setFloat(1, this.voto);
            preparedStmt.setInt(2, this.film);
            preparedStmt.setString(3, this.username);
            preparedStmt.execute();
            this.dbOperationStatusCode = 0;
            this.dbOperationMessage = "Record updated";
        } catch (Exception e) {
            this.dbOperationStatusCode = 1;
            this.dbOperationMessage = "Record not updated: " + e.getMessage();
        }
    }

    public void updateCommento() {
        try {
            String query = "UPDATE voto SET commento = ?" + " WHERE film = ? and username = ?";
            PreparedStatement preparedStmt = this.con.prepareStatement(query);
            preparedStmt.setString(1, this.commento);
            preparedStmt.setInt(2, this.film);
            preparedStmt.setString(3, this.username);
            preparedStmt.execute();
            this.dbOperationStatusCode = 0;
            this.dbOperationMessage = "Record updated";
        } catch (Exception e) {
            this.dbOperationStatusCode = 1;
            this.dbOperationMessage = "Record not updated: " + e.getMessage();
        }
    }


}
