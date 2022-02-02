package filmAPI.interfaces;

import filmAPI.models.Cast;

import java.util.List;

public interface CastFilm {

    /**
     * Method to call the Query in order to add a cast into the DataBase
     *
     * @param id_film: film's id for adding
     * @param actors:  list of actors for adding
     * @return boolean of success
     */
    boolean addCast(int id_film, String[] actors);

    /**
     * Method to call the Query in order to edit a cast into the DataBase
     *
     * @param id_film: film's id for updating
     * @param actors:  edited list of actors for adding
     * @return boolean of success
     */
    boolean editCast(int id_film, String[] actors);

    /**
     * Method to call the Query in order to delete a cast from the DataBase
     *
     * @param id_film: film's id for deleting
     * @return boolean of success
     */
    boolean deleteCast(int id_film);

    /**
     * Method to call the Query in order to get whole cast by a film from the DataBase
     *
     * @param id_film: film's id for getting a list of Cast objects
     * @return list of whole cast by a film from the DataBase
     */
    List<Cast> getByFilm(int id_film);
}
