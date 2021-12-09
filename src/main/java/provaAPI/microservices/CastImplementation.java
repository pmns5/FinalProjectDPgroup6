package provaAPI.microservices;

import provaAPI.interfaces.CastFilm;
import provaAPI.interfaces.DBConnection;
import provaAPI.models.Cast;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CastImplementation extends DBConnection implements CastFilm {

    public CastImplementation() {
        super();
        db.connect();
    }

    public boolean addCast(int id_film, String[] actors) {
        for (String id_actor : actors) {
            if (!addCast(new Cast(id_film, Integer.parseInt(id_actor))))
                return false;
        }
        return true;
    }

    public boolean editCast(int id_film, String[] actors) {
        if (deleteCast(id_film))
            return addCast(id_film, actors);
        return false;
    }

    public boolean deleteCast(int id_film) {
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "DELETE FROM cast WHERE  id_film = " + id_film
        )) {
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // Private Utilities
    private boolean addCast(Cast cast) {
        try (PreparedStatement stmt = db.getConn().prepareStatement(
                "INSERT INTO cast (id_film, id_actor) VALUES (?, ?);"
        )) {
            stmt.setInt(1, cast.getId_film());
            stmt.setInt(2, cast.getId_actor());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
