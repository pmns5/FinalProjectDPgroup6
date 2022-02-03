package userAPI.interfaces;

import userAPI.models.User;

import java.util.List;

public interface UserInterface {

    /**
     * Method to call the Query in order to add a user into the DataBase
     *
     * @param user: User object for adding
     * @return boolean of success
     */
    boolean addUser(User user);

    /**
     * Method to call the Query in order to edit a user from the DataBase
     *
     * @param user: edited User object for updating
     * @return boolean of success
     */
    boolean editUser(User user);

    /**
     * Method to call the Query in order to check the login from the DataBase
     *
     * @param user: User object for checking the login
     * @return if the user is found return its id, otherwise -1
     */
    int loginUser(User user);

    /**
     * Method to call the Query in order to ban a user into the DataBase
     *
     * @param id_user: user's id for banning
     * @return boolean of success
     */
    boolean banUser(int id_user);

    /**
     * Method to call the Query in order to remove the ban from user into the DataBase
     *
     * @param id_user: user's id for removing the ban
     * @return boolean of success
     */
    boolean removeBanUser(int id_user);

    /**
     * Method to call the Query in order to delete a user from the DataBase
     *
     * @param id_user: user's id for deleting
     * @return boolean of success
     */
    boolean deleteUser(int id_user);

    /**
     * Method to call the Query in order to get User object from the DataBase
     *
     * @param id_user: user's id for getting User object
     * @return if the user is found return its User object, otherwise null
     */
    User getUser(int id_user);

    /**
     * Method to call the Query in order to get user's id from the DataBase
     *
     * @param user: User object for getting its id
     * @return if the user is found return its id, otherwise -1
     */
    int getIdUser(User user);

    /**
     * Method to call the Query in order to get all users from the DataBase
     *
     * @return list of all users from the DataBase
     */
    List<User> getUsers();

    /**
     * Method to call the Query in order to get all banned users from the DataBase
     *
     * @return list of all banned users from the DataBase
     */
    List<User> getBannedUsers();

    /**
     * Method to call the Query in order to get all no-banned users from the DataBase
     *
     * @return list of all no-banned users from the DataBase
     */
    List<User> getNoBannedUsers();
}
