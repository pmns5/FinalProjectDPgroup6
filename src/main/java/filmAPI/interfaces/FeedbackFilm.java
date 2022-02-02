package filmAPI.interfaces;

import filmAPI.models.Feedback;

import java.util.ArrayList;

public interface FeedbackFilm {

    /**
     * Method to call the Query in order to add a feedback into the DataBase
     *
     * @param feedback: Feedback object for adding
     * @return boolean of success
     */
    boolean addFeedback(Feedback feedback);

    /**
     * Method to call the Query in order to edit a feedback from the DataBase
     *
     * @param feedback: edited Feedback object for updating
     * @return boolean of success
     */
    boolean editFeedback(Feedback feedback);

    /**
     * Method to call the Query in order to delete a feedback from the DataBase
     *
     * @param id_film: film's id for deleting
     * @param id_user: film's id for deleting
     * @return boolean of success
     */
    boolean deleteFeedback(int id_film, int id_user);

    /**
     * Method to call the Query in order to get Feedback object from the DataBase
     *
     * @param id_film: film's id for getting Feedback object
     * @param id_user: user's id for getting Feedback object
     * @return if the user is found return its User object, otherwise null
     */
    Feedback getFeedback(int id_film, int id_user);

    /**
     * Method to call the Query in order to get all feedback by a film from the DataBase
     *
     * @param id_film: film's id for getting a list of Feedback objects
     * @return list of all feedbacks by a film from the DataBase
     */
    ArrayList<Feedback> getByFilm(int id_film);

    /**
     * Method to call the Query in order to get all feedback by a user from the DataBase
     *
     * @param id_user: user's id for getting a list of Feedback objects
     * @return list of all feedbacks by a user from the DataBase
     */
    ArrayList<Feedback> getByUser(int id_user);

    /**
     * Method to call the Query in order to perform the average of film's scores from the DataBase
     *
     * @param id_film: film's id for performing the average of its scores
     * @return the average of film's scores
     */
    Float getAverageScore(int id_film);
}
