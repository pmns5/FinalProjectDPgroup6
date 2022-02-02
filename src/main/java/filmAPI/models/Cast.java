package filmAPI.models;

public class Cast {
    private final int id_actor;
    private final int
            id_film;

    public Cast(int id_film, int id_actor) {
        this.id_film = id_film;
        this.id_actor = id_actor;
    }

    public int getId_film() {
        return id_film;
    }

    public int getId_actor() {
        return id_actor;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }
}
