package provaAPI.interfaces;

import provaAPI.models.Actor;

import java.util.ArrayList;
import java.util.List;

public interface ActorFilm {

    boolean addActor(Actor actor);

    boolean editActor(Actor actor);

    boolean deleteActor(int idActor);

    Actor getOneActor(int idActor);

    List<Actor> getAllActors();

}
