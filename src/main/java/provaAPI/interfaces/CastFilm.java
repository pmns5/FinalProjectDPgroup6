package provaAPI.interfaces;

public interface CastFilm {

    boolean addCast(int id_film, String[] actors);

    boolean editCast(int id_film, String[] actors);

    boolean deleteCast(int id_film);

}
