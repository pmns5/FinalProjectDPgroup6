package provaAPI.interfaces;

import provaAPI.models.Film;

import java.util.ArrayList;

public interface FilmInterface {

    int addFilm(Film film);

    int editFilm(Film film);

    boolean deleteFilm(int id);

    Film getOneFilm(int id);

    ArrayList<Film> getAllFilms();
}
