package filmAPI.interfaces;

import filmAPI.models.Actor;

import java.util.List;

public interface ActorFilm {

    /**
     * Method to call the Query in order to add an actor into the DataBase
     *
     * @param actor: Actor object for adding
     * @return boolean of success
     */
    boolean addActor(Actor actor);

    /**
     * Method to call the Query in order to edit an actor from the DataBase
     *
     * @param actor: edited Actor object for updating
     * @return boolean of success
     */
    boolean editActor(Actor actor);

    /**
     * Method to call the Query in order to delete an actor from the DataBase
     *
     * @param id_actor: actor's id for deleting
     * @return boolean of success
     */
    boolean deleteActor(int id_actor);

    /**
     * Method to call the Query in order to get Actor object from the DataBase
     *
     * @param id_actor: actor's id for getting Actor object
     * @return if the actor is found return its Actor object, otherwise null
     */
    Actor getActor(int id_actor);

    /**
     * Method to call the Query in order to get all actors from the DataBase
     *
     * @return list of all actors from the DataBase
     */
    List<Actor> getActors();
}
