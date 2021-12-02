package newBackEnd.dbQueries;

import newBackEnd.models.Actor;

import javax.validation.constraints.NotNull;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ActorsDecorator extends DbDecorator{

    /**
     * Class used to decorate a Db Interface object and use the queries provided on the maintainer_role table.
     *
     * @param db: an object implementing DbInterface.
     */
    public ActorsDecorator(DbInterface db) {
        super(db);
    }

    /**
     * This method allows add a tuple to the maintainer_role table in the db. It takes the fields of the tuple as a parameter.
     *
     * @param name:        the name of the role.
     * @param surname: the description of the role.
     * @return true if no error occur, else false.
     */
    public boolean addActor(@NotNull String name, @NotNull String surname) {
        try (PreparedStatement stmt = getConn().prepareStatement(
                "INSERT INTO Actors ( name, surname) VALUES ( ?, ?);"
        )){
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * This method allows you to view the contents of the maintainer_role table, provided as a ModelInterface list.
     *
     * @return a list of MaintainerRole ModelInterface or null if an error occur.
     */
    public ArrayList<Actor> getActors() {
        ArrayList<Actor> result = new ArrayList<>();
        try (Statement stmt = getConn().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Actors ORDER BY id;")){
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                result.add(new Actor((int) id, name, surname));
            }
        } catch (SQLException e) {
            return null;
        }
        return result;
    }

    /**
     * This method displays an MaintainerRole elements with the selected id, which represent data from the
     * maintainer_role and related tables.
     *
     * @param id: the role id;
     * @return a MaintainerRole object or null if an error occur.
     */
    public Actor getActor(long id) {
        try (PreparedStatement stmt = getConn().prepareStatement("SELECT * FROM Actors WHERE id = ?;")){
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()){
                if (!rs.next()) {
                    return null;
                }
                return new Actor((int) rs.getLong("id"), rs.getString("name"),
                        rs.getString("surname"));
            }
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * This method delete a tuple from the maintainer_role table based on its id.
     *
     * @param id: id of the tuple to delete.
     * @return true if no error occur, else false.
     */
    public boolean deleteActor(long id) {
        try (PreparedStatement stmt = getConn().prepareStatement(
                "DELETE FROM actors WHERE id = ?;"
        )){
            stmt.setLong(1, id);
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * This method edit a tuple from the maintainer_role table based on its id. Need the parameter to edit.
     *
     * @param id:          the id of the tuple to edit.
     * @param name:        the new name to assign.
     * @param surname : the new description to assign.
     * @return true if no error occur, else false.
     */
    public boolean editActor(long id, @NotNull String name, @NotNull String surname) {
        try (PreparedStatement stmt = getConn().prepareStatement(
                "UPDATE actors SET name = ?, surname = ? WHERE id = ?"
        )){
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setLong(3, id);
            stmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }


}
