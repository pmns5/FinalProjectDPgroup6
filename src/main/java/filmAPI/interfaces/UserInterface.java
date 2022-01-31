package filmAPI.interfaces;


import filmAPI.models.User;

public interface UserInterface {
    boolean addUser(User user);

    boolean editUser(User user);

    boolean deleteUser(int id_user);

    User getUser(int id_user);
}
