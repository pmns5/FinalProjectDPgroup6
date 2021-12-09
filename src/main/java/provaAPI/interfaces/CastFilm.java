package provaAPI.interfaces;


import provaAPI.models.Cast;
import java.util.List;

public interface CastFilm {

    boolean addCast(int id_film, String[] actors);
    boolean editCast(int id_film, String[] actors);
    boolean deleteCast(int id_film);

    List<Cast> getByFilm(int id_film);

}
