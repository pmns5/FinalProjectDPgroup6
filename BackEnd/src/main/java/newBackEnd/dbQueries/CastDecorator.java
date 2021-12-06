package newBackEnd.dbQueries;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CastDecorator extends DbDecorator {
    /**
     * Base constructor, used to get an instance of the DbInterface interface used by the decorator classes.
     *
     * @param db : a concrete implementation of DbInterface.
     */
    public CastDecorator(DbInterface db) {
        super(db);
    }

    public boolean addCast(int id_film, String[] actors) {
        for (String curr_actor : actors) {
            try (PreparedStatement stmt = getConn().prepareStatement(
                    "INSERT INTO cast (id_film, id_actor) VALUES (?, ?);"
            )) {
                stmt.setString(1, String.valueOf(id_film));
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
