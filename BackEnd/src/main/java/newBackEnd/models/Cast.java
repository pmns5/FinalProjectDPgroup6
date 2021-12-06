package newBackEnd.models;

public class Cast implements Model {
    private int id_film;
    private String id_actor;

    public Cast(int id_film, String id_actor) {
        this.id_film = id_film;
        this.id_actor = id_actor;
    }

    public int getId_film() {
        return id_film;
    }

    public void setId_film(int id_film) {
        this.id_film = id_film;
    }

    public String getId_actor() {
        return id_actor;
    }

    public void setId_actor(String id_actor) {
        this.id_actor = id_actor;
    }

    public String toJSON() {
        return "{" + "\"id_film\":\"" + id_film + "\"," +
                "\"id_actor\":\"" + id_actor + "}";
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }
}
