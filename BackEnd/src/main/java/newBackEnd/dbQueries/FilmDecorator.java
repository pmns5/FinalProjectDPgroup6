package newBackEnd.dbQueries;

import com.mysql.cj.jdbc.Blob;
import newBackEnd.models.Film;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FilmDecorator extends DbDecorator {
    /**
     * Base constructor, used to get an instance of the DbInterface interface used by the decorator classes.
     *
     * @param db : a concrete implementation of DbInterface.
     */
    DbInterface db;

    public FilmDecorator(DbInterface db) {
        super(db);
        this.db = db;
    }

    public boolean addFilm(String title, String genre, String plot, String trailer, byte[] poster, String[] actors) {
        try (PreparedStatement stmt = getConn().prepareStatement(
                "INSERT INTO film (title, genre, plot, trailer, poster) VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS
        )) {
            stmt.setString(1, title);
            stmt.setString(2, genre);
            stmt.setString(3, plot);
            stmt.setString(4, trailer);
            stmt.setBlob(5, new SerialBlob(poster));
            stmt.execute();
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            int id_film = keys.getInt(1);
            CastDecorator castDecorator = new CastDecorator(db);
            return castDecorator.addCast(id_film, actors);
        } catch (SQLException e) {
            return false;
        }
    }

    public Film getFilm(long idFilm) {
        try (PreparedStatement stmt = getConn().prepareStatement("SELECT id_film, title, genre, plot, trailer, poster FROM film WHERE id_film = ?;")) {
            stmt.setLong(1, idFilm);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                int id_film = rs.getInt(1);
                String title = rs.getString(2);
                String genre = rs.getString(3);
                String plot = rs.getString(4);
                String trailer = rs.getString(5);
                Blob poster = (Blob) rs.getBlob(6);
                int blobLength = (int) poster.length();
                byte[] blobAsBytes = poster.getBytes(1, blobLength);
                poster.free();
                return new Film(id_film, title, genre, plot, trailer, blobAsBytes);
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<Film> getFilms() {
        ArrayList<Film> listFilms = new ArrayList<>();
        try (Statement stmt = getConn().createStatement(); ResultSet rs = stmt.executeQuery("SELECT id_film, title, genre, plot, trailer, poster FROM film ORDER BY id_film")) {
            while (rs.next()) {
                int id_film = rs.getInt(1);
                String title = rs.getString(2);
                String genre = rs.getString(3);
                String plot = rs.getString(4);
                String trailer = rs.getString(5);
                Blob poster = (Blob) rs.getBlob(6);
                int blobLength = (int) poster.length();
                byte[] blobAsBytes = poster.getBytes(1, blobLength);
                poster.free();
                listFilms.add(new Film(id_film, title, genre, plot, trailer, blobAsBytes));
            }
        } catch (SQLException e) {
            return null;
        }
        return listFilms;
    }
}
