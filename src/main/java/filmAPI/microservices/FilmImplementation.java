package filmAPI.microservices;

import com.mysql.cj.jdbc.Blob;
import filmAPI.gateway.EnumGenre;
import filmAPI.interfaces.DBConnection;
import filmAPI.interfaces.FilmInterface;
import filmAPI.models.Film;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FilmImplementation extends DBConnection implements FilmInterface {
    public FilmImplementation() {
        super();
        db.connect();
    }

    @Override
    public int addFilm(Film film) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement(
                "INSERT INTO film (title, genre, plot, trailer, poster) VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS
        )) {
            stmt.setString(1, film.getTitle());
            stmt.setString(2, film.getGenre().toString());
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
    public boolean editFilm(Film film) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement(
                "UPDATE film SET title = ?, genre = ?, plot = ?, trailer = ?," +
                        " poster = ? WHERE id_film = ?"
        )) {
            stmt.setString(1, film.getTitle());
            stmt.setString(2, film.getGenre().toString());
            stmt.setString(3, film.getPlot());
            stmt.setString(4, film.getTrailer());
            stmt.setBlob(5, new SerialBlob(film.getPoster()));
            stmt.setInt(6, film.getId());
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteFilm(int idFilm) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement(
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
    public Film getFilm(int idFilm) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement("SELECT id_film, title, genre, plot, trailer, poster FROM film WHERE id_film = ?")) {
            stmt.setInt(1, idFilm);
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
                return new Film(id_film, title, plot, EnumGenre.valueOf(genre), trailer, blobAsBytes);
            }
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Film> getFilms() {
        ArrayList<Film> listFilms = new ArrayList<>();
        try (Statement stmt = db.getConnection().createStatement(); ResultSet rs = stmt.executeQuery("SELECT id_film, title, genre, plot, trailer, poster FROM film ORDER BY id_film")) {
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
                listFilms.add(new Film(id_film, title, plot, EnumGenre.valueOf(genre), trailer, blobAsBytes));
            }
        } catch (SQLException e) {
            return null;
        }
        return listFilms;
    }

    @Override
    public List<Film> getFilmsPerGenre(EnumGenre genre) {
        ArrayList<Film> listFilms = new ArrayList<>();
        try (Statement stmt = db.getConnection().createStatement(); ResultSet rs = stmt.executeQuery("SELECT id_film," +
                "title, plot, trailer, poster FROM film WHERE genre=" + genre.toString() + " ORDER BY id_film")) {
            while (rs.next()) {
                int id_film = rs.getInt(1);
                String title = rs.getString(2);
                String plot = rs.getString(3);
                String trailer = rs.getString(4);
                Blob poster = (Blob) rs.getBlob(5);
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
