package test;

import filmAPI.gateway.EnumGenre;
import filmAPI.microservices.FilmImplementation;
import filmAPI.models.Film;
import junit.framework.TestCase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FilmImplementationTest extends TestCase {
    private FilmImplementation service;

    @Order(1)
    protected void setUp() {
        service = new FilmImplementation();
    }

    @Order(2)
    public final void testAddFilm() {
        String title = "Prova";
        String plot = "Una prova avvincente";
        EnumGenre genre = EnumGenre.Action;
        String trailer = "www.youtube.com/prova";
        byte[] blobAsBytes = "string".getBytes();
        Film Film = new Film(-1, title, plot, genre, trailer, blobAsBytes);
        int result = service.addFilm(Film);
        assertEquals(result, 1);
    }

    @Order(3)
    public final void testEditFilm() {
        String title = "Pro";
        String plot = "Una pro avvincente";
        EnumGenre genre = EnumGenre.Action;
        String trailer = "www.youtube.com/pro";
        byte[] blobAsBytes = "string".getBytes();
        Film Film = new Film(1, title, plot, genre, trailer, blobAsBytes);
        boolean result = service.editFilm(Film);
        assert (result);
    }

    @Order(4)
    public final void testDeleteFilm() {
        int id_Film = 1;
        boolean result = service.deleteFilm(id_Film);
        assertTrue(result);
    }

    @Order(5)
    protected void tearDown() {
        service = null;
    }
}
