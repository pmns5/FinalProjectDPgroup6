package newBackEnd.models;

public class User implements Model {
    private int id;
    private String username;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toJSON() {
        return "{" + "\"id\":\"" + id + "\"," +
                "\"title\":\"" + username + "\"}";
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }
}
