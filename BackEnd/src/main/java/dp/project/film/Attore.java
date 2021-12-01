package dp.project.film;

import dp.project.MySqlDbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Attore {

    private final Connection con;
    public int dbOperationStatusCode;
    public String dbOperationMessage;

    private int id;
    private String nome;
    private String cognome;

    public Attore(MySqlDbConnection db) {
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
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
            String query = "INSERT INTO ATTORE (id, nome, cognome) VALUES (?,?,?)";
            PreparedStatement preparedStmt = this.con.prepareStatement(query);
            preparedStmt.setInt(1, this.id);
            preparedStmt.setString(2, this.nome);
            preparedStmt.setString(3, this.cognome);
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
            String query = "DELETE FROM ATTORE WHERE id = ?";
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
