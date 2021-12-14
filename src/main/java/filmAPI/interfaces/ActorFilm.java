package filmAPI.interfaces;

import filmAPI.models.Actor;

import java.util.List;

public interface ActorFilm {

    boolean addActor(Actor actor);

    boolean editActor(Actor actor);

    boolean deleteActor(int idActor);

    Actor getOneActor(int idActor);

    List<Actor> getAllActors();

}
