package newBackEnd.dbQueries;


import com.mysql.cj.jdbc.Blob;
import newBackEnd.models.Actor;
import newBackEnd.models.Film;

import javax.sql.rowset.serial.SerialBlob;
import javax.validation.constraints.NotNull;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FilmDecorator extends DbDecorator {
    private static int count = 0;

    /**
     * Base constructor, used to get an instance of the DbInterface interface used by the decorator classes.
     *
     * @param db : a concrete implementation of DbInterface.
     */
    public FilmDecorator(DbInterface db) {
        super(db);
    }

    public boolean addFilm(@NotNull String title, @NotNull String plot, String genre, byte[] poster, String[] actors) {
        try (PreparedStatement stmt = getConn().prepareStatement(
                "INSERT INTO film (title, plot, genre, poster) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS
        )) {
            stmt.setString(1, title);
            stmt.setString(2, plot);
            stmt.setString(3, genre);
            stmt.setBlob(4, new SerialBlob(poster));
            stmt.execute();
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();
            int key = keys.getInt(1);

            return addCast(key, actors);
        } catch (SQLException e) {
            return false;
        }
    }

    public Film getFilm(long id) {
        try (PreparedStatement stmt = getConn().prepareStatement("SELECT id, title, plot, genre, poster FROM film WHERE id = ?;")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                int idFilm = rs.getInt(1);
                String title = rs.getString(2);
                String plot = rs.getString(3);
                String genre = rs.getString(4);
                Blob blob = (Blob) rs.getBlob(5);
                int blobLength = (int) blob.length();
                byte[] blobAsBytes = blob.getBytes(1, blobLength);
                blob.free();

                ArrayList<Actor> actors = new ArrayList<>();
                ResultSet rs2 = stmt.executeQuery("SELECT actors.id, name, surname from actors join cast c on actors.id = c.id_actor join film f on f.id = c.id_film where f.id = " + id);
                while (rs2.next()) {
                    actors.add(new Actor(rs2.getInt("id"), rs2.getString("name"), rs2.getString("surname")));
                }
                return new Film(idFilm, title, plot, genre, actors, blobAsBytes);
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<Film> getFilms() {

        ArrayList<Film> result = new ArrayList<>();
        try (Statement stmt = getConn().createStatement(); ResultSet rs = stmt.executeQuery("SELECT id, title, plot, genre, poster FROM film ORDER BY id;")) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String plot = rs.getString("plot");
                String genre = rs.getString("genre");
                Blob blob = (Blob) rs.getBlob("poster");
                int blobLength = (int) blob.length();
                byte[] blobAsBytes = blob.getBytes(1, blobLength);
                blob.free();

                ArrayList<Actor> actors = new ArrayList<>();
                ResultSet rs2 = getConn().createStatement().executeQuery("SELECT actors.id, name, surname from actors join cast c on actors.id = c.id_actor join film f on f.id = c.id_film where f.id = " + id);
                while (rs2.next()) {
                    actors.add(new Actor(rs2.getInt("id"), rs2.getString("name"), rs2.getString("surname")));
                }
                result.add(new Film(id, title, plot, genre, actors, blobAsBytes));
            }
        } catch (SQLException e) {
            return null;
        }
        return result;
    }

    private boolean addCast(int id_film, String[] actors) {

//        ResultSet rs;
//        int id_film;
//        try {
//            rs = getConn().createStatement().executeQuery("SELECT AUTO_INCREMENT\n" +
//                    "FROM information_schema.TABLES\n" +
//                    "WHERE TABLE_SCHEMA = 'film_db'\n" +
//                    "  AND TABLE_NAME = 'film'");
//            rs.next();
//
//            id_film =  rs.getInt(0);
//            id_film = id_film - 1;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }


        for (String curr_actor : actors) {
            try (PreparedStatement stmt = getConn().prepareStatement(
                    "INSERT INTO cast (id_film, id_actor) VALUES (?, ?);"
            )) {
                stmt.setString(1, String.valueOf(id_film - 1));
                stmt.setString(2, curr_actor);
                stmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

        }

        return true;
    }

}