package provaAPI.interfaces;

import provaAPI.models.Film;

import java.util.ArrayList;
import java.util.List;

public interface FilmInterface {

    int addFilm(Film film);

    int editFilm(Film film);

    boolean deleteFilm(int id);

    Film getOneFilm(int id);

    List<Film> getAllFilms();
    List<Film> getByGenre(int genre);

}
