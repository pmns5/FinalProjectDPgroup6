package filmAPI.interfaces;

import filmAPI.gateway.EnumGenre;
import filmAPI.models.Film;

import java.util.List;

public interface FilmInterface {

    /**
     * Method to call the Query in order to add a film into the DataBase
     *
     * @param film: Film object for adding
     * @return boolean of success
     */
    int addFilm(Film film);

    /**
     * Method to call the Query in order to edit a film from the DataBase
     *
     * @param film: edited Film object for updating
     * @return boolean of success
     */
    boolean editFilm(Film film);

    /**
     * Method to call the Query in order to delete a film from the DataBase
     *
     * @param id_film: film's id for deleting
     * @return boolean of success
     */
    boolean deleteFilm(int id_film);

    /**
     * Method to call the Query in order to get Film object from the DataBase
     *
     * @param id_film: film's id for getting Film object
     * @return if the film is found return its Film object, otherwise null
     */
    Film getFilm(int id_film);

    /**
     * Method to call the Query in order to get all films from the DataBase
     *
     * @return list of all films from the DataBase
     */
    List<Film> getFilms();

    /**
     * Method to call the Query in order to get all films per genre from the DataBase
     *
     * @param genre: genre's enum for getting a list of Film objects
     * @return list of all films per genre from the DataBase
     */
    List<Film> getFilmsPerGenre(EnumGenre genre);
}
