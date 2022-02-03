package filmAPI.models;

public class User {
    private String username;
    private int id;

    public User(int id, String username) {
        this.username = username;
        this.id = id;
    }
    public User(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
