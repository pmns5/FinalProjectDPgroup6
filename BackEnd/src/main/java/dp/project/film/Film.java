package dp.project.film;

import dp.project.MySqlDbConnection;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Film {
    private final Connection con;
    public int dbOperationStatusCode;
    public String dbOperationMessage;

    private int id;
    private String name;
    private String genre;
    private String trama;
    private String trailer;
    private Blob blob;

    public Film(MySqlDbConnection db) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTrama() {
        return trama;
    }

    public void setTrama(String trama) {
        this.trama = trama;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
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
            String query = "INSERT INTO FILM (id,nome,genere,trama,trailer,copertina) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStmt = this.con.prepareStatement(query);
            preparedStmt.setInt(1, this.id);
            preparedStmt.setString(2, this.name);
            preparedStmt.setString(3, this.genre);
            preparedStmt.setString(4, this.trama);
            preparedStmt.setString(5, this.trailer);
            preparedStmt.setBlob(6, this.blob);
            preparedStmt.execute();
            this.dbOperationStatusCode = 0;
            this.dbOperationMessage = "Record added";
        } catch (Exception e) {
            this.dbOperationStatusCode = 1;
            this.dbOperationMessage = "Record not added: " + e.getMessage();
        }
    }

    public void update() {
        try {
            String query = "UPDATE FILM SET nome=?,genere=?,trama=?,trailer=?,copertina=? WHERE id = ?";
            PreparedStatement preparedStmt = this.con.prepareStatement(query);
            preparedStmt.setString(1, this.name);
            preparedStmt.setString(2, this.genre);
            preparedStmt.setString(3, this.trama);
            preparedStmt.setString(4, this.trailer);
            preparedStmt.setBlob(5, this.blob);
            preparedStmt.setInt(6, this.id);
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
            String query = "DELETE FROM FILM WHERE id = ?";
            PreparedStatement preparedStmt = this.con.prepareStatement(query);
            preparedStmt.setInt(1, this.id);
            preparedStmt.execute();
            this.dbOperationStatusCode = 0;
            this.dbOperationMessage = "Record deleted";
        } catch (Exception e) {
            this.dbOperationStatusCode = 1;
            this.dbOperationMessage = "Record not deleted: " + e.getMessage();
        }
    }

}
