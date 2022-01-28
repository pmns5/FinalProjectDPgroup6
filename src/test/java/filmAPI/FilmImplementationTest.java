package filmAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import filmAPI.gateway.EnumGenre;
import filmAPI.microservices.FilmImplementation;
import filmAPI.models.Film;
import filmAPI.models.Utils;
import junit.framework.TestCase;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FilmImplementationTest extends TestCase {
    private FilmImplementation service;

    public void main(String[] args) {
        junit.swingui.TestRunner.run(FilmImplementationTest.class);
    }

    @Order(1)
    protected void setUp() {
        //Service initialization
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
        int result = service.editFilm(Film);
        assertNotSame(result, -1);
    }

    @Order(4)
    public final void testGetOneFilm() throws JsonProcessingException {
        int id_Film = 1;
        byte[] blobAsBytes = "string".getBytes();
        Film Film1 = new Film(1, "Pro", "Una pro avvincente", EnumGenre.Action, "www.youtube.com/pro", blobAsBytes);
        String expected = Utils.toJSON(Film1);
        Film Film2 = service.getOneFilm(id_Film);
        String result = Utils.toJSON(Film2);
        assertEquals(expected, result);
    }

    @Order(5)
    public final void testGetAllFilm() throws JsonProcessingException {
        List<Film> FilmList = new ArrayList<>();
        List<Film> allFilmList = service.getAllFilms();
        byte[] blobAsBytes = "string".getBytes();
        Film Film1 = new Film(1, "Pro", "Una pro avvincente", EnumGenre.Action, "www.youtube.com/pro", blobAsBytes);
        FilmList.add(Film1);
        String expected = Utils.toJSON(FilmList);
        String result = Utils.toJSON(allFilmList);
        assertEquals(expected, result);
    }

    @Order(6)
    public final void testGetByGenre() throws JsonProcessingException {
        EnumGenre genre = EnumGenre.Action;
        List<Film> listFilm = new ArrayList<>();
        byte[] blobAsBytes = "string".getBytes();
        Film Film1 = new Film(1, "Pro", "Una pro avvincente", EnumGenre.Action, "www.youtube.com/pro", blobAsBytes);
        listFilm.add(Film1);
        String expected = Utils.toJSON(listFilm);
        List<Film> listFilmresult = service.getByGenre(genre);
        String result = Utils.toJSON(listFilmresult);
        System.out.println(expected + " dsadas " + result);
        assertEquals(expected, result);
    }

    @Order(7)
    public final void testDeleteFilm() {
        int id_Film = 1;
        boolean result = service.deleteFilm(id_Film);
        assertTrue(result);
    }

    @Order(8)
    protected void tearDown() {
        service = null;
        ;
    }
}
