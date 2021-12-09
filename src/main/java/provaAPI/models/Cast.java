package provaAPI.models;

public class Cast {
    private int id_film;
    private int id_actor;

    public Cast(int id_film, int id_actor) {
        this.id_film = id_film;
        this.id_actor = id_actor;
    }

    public int getId_film() {
        return id_film;
    }

    public void setId_film(int id_film) {
        this.id_film = id_film;
    }

    public int getId_actor() {
        return id_actor;
    }

    public void setId_actor(int id_actor) {
        this.id_actor = id_actor;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }
}
