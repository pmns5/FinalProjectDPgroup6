package filmAPI.testFilmAPI;

import filmAPI.EnumGenre;
import filmAPI.microservices.ActorManagement.Actor;
import filmAPI.microservices.FilmManagement.Film;
import filmAPI.microservices.FilmManagement.FilmImplementation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmImplementationTest {
    private FilmImplementation service;

    @BeforeEach
    protected void setUp() {
        service = new FilmImplementation();
    }

    @Test
    public final void testAddFilm() throws SQLException {
        List<Actor> actorList = new ArrayList<>();
        actorList.add(new Actor(10, "Matt", "Damon"));
        actorList.add(new Actor(12, "Jennifer", "Lawrence"));
        Film Film = new Film(-1, "Prova", EnumGenre.Action, "Una prova avvincente", "www.youtube.com/prova", "image".getBytes(), actorList);
        Assertions.assertTrue(service.addFilm(Film));
    }

    @Test
    public final void testEditFilm() throws SQLException {
        List<Actor> actorList = new ArrayList<>();
        actorList.add(new Actor(18, "Johnny", "Deep"));
        actorList.add(new Actor(20, "Henry", "Ford"));
        Film Film = new Film(1, "Prova2", EnumGenre.Action, "Una2 prova2 avvincente2", "www.youtube.com/prova2", "image2".getBytes(), actorList);
        Assertions.assertTrue(service.editFilm(Film));
    }

    @Test
    public final void testDeleteFilm() throws SQLException {
        int id_film = 1;
        Assertions.assertTrue(service.deleteFilm(id_film));
    }

    @AfterEach
    protected void tearDown() {
        service = null;
    }
}
