package filmAPI.interfaces;

import filmAPI.models.Film;

import java.util.List;

public interface FilmInterface {
    int addFilm(Film film);

    int editFilm(Film film);

    boolean deleteFilm(int id);

    Film getOneFilm(int id);

    List<Film> getAllFilms();

    List<Film> getByGenre(String genre);
}
