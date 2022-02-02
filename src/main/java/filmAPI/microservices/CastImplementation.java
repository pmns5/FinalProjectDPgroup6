package filmAPI.microservices;

import filmAPI.interfaces.CastFilm;
import filmAPI.interfaces.DBConnection;
import filmAPI.models.Cast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CastImplementation extends DBConnection implements CastFilm {
    public CastImplementation() {
        super();
        db.connect();
    }

    @Override
    public boolean addCast(int id_film, String[] actors) {
        for (String id_actor : actors) {
            if (!addCast(new Cast(id_film, Integer.parseInt(id_actor))))
                return false;
        }
        return true;
    }

    @Override
    public boolean editCast(int id_film, String[] actors) {
        if (deleteCast(id_film))
            return addCast(id_film, actors);
        return false;
    }

    @Override
    public boolean deleteCast(int id_film) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement(
                "DELETE FROM cast WHERE id_film = " + id_film
        )) {
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Cast> getByFilm(int id_film) {
        ArrayList<Cast> cast = new ArrayList<>();
        try (PreparedStatement stmt = db.getConnection().prepareStatement("SELECT * FROM cast WHERE  id_film = " + id_film)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cast.add(new Cast(rs.getInt(1), rs.getInt(2)));
            }
            return cast;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean addCast(Cast cast) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement(
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
