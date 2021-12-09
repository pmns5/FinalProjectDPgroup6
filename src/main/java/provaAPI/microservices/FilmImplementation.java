package provaAPI.microservices;

import com.mysql.cj.jdbc.Blob;
import provaAPI.interfaces.DBConnection;
import provaAPI.interfaces.FilmInterface;
import provaAPI.models.Film;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Microservice for management of Data associated of a film
 */
public class FilmImplementation extends DBConnection implements FilmInterface {

    public FilmImplementation() {
        super();
        db.connect();
    }

    @Override
    public int addFilm(Film film) {
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "INSERT INTO film (title, genre, plot, trailer, poster) VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS
        )) {
            stmt.setString(1, film.getTitle());
            stmt.setInt(2, film.getGenre());
            stmt.setString(3, film.getPlot());
            stmt.setString(4, film.getTrailer());
            stmt.setBlob(5, new SerialBlob(film.getPoster()));
            stmt.execute();
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            return keys.getInt(1);
        } catch (SQLException e) {
            return -1;
        }
    }

    @Override
    public int editFilm(Film film) {
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "UPDATE film SET title = ?, genre = ?, plot = ?, trailer = ?," +
                        " poster = ? WHERE id_film = ?"
        )) {
            stmt.setString(1, film.getTitle());
            stmt.setInt(2, film.getGenre());
            stmt.setString(3, film.getPlot());
            stmt.setString(4, film.getTrailer());
            stmt.setBlob(5, new SerialBlob(film.getPoster()));
            stmt.setInt(6, film.getId());
            stmt.execute();
        } catch (SQLException e) {
            return -1;
        }
        return film.getId();
    }

    @Override
    public boolean deleteFilm(int idFilm) {
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "DELETE FROM film WHERE id_film = ?;"
        )) {
            stmt.setInt(1, idFilm);
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public Film getOneFilm(int idFilm) {
        try (PreparedStatement stmt = db.getConn().prepareStatement("SELECT id_film, title, genre, plot, trailer, poster FROM film WHERE id_film = ?")) {
            stmt.setInt(1, idFilm);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                int id_film = rs.getInt(1);
                String title = rs.getString(2);
                int genre = rs.getInt(3);
                String plot = rs.getString(4);
                String trailer = rs.getString(5);
                Blob poster = (Blob) rs.getBlob(6);
                int blobLength = (int) poster.length();
                byte[] blobAsBytes = poster.getBytes(1, blobLength);
                poster.free();
                return new Film(id_film, title, plot, genre, trailer, blobAsBytes);
            }
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public ArrayList<Film> getAllFilms() {
        ArrayList<Film> listFilms = new ArrayList<>();
        try (Statement stmt = db.getConn().createStatement(); ResultSet rs = stmt.executeQuery("SELECT id_film, title, genre, plot, trailer, poster FROM film ORDER BY id_film")) {
            while (rs.next()) {
                int id_film = rs.getInt(1);
                String title = rs.getString(2);
                int genre = rs.getInt(3);
                String plot = rs.getString(4);
                String trailer = rs.getString(5);
                Blob poster = (Blob) rs.getBlob(6);
                int blobLength = (int) poster.length();
                byte[] blobAsBytes = poster.getBytes(1, blobLength);
                poster.free();
                listFilms.add(new Film(id_film, title, plot, genre, trailer, blobAsBytes));
            }
        } catch (SQLException e) {
            return null;
        }
        return listFilms;
    }
}
