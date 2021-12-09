package provaAPI.interfaces;

import provaAPI.models.Actor;

import java.util.ArrayList;

public interface ActorFilm {

    public boolean addActor(Actor actor);

    public boolean editActor(Actor actor);

    public boolean deleteActor(int idFilm);

    public Actor getOneActor(int idFilm);

    public ArrayList<Actor> getAllActors();

}
