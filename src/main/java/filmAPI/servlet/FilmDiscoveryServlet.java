package filmAPI.servlet;

import filmAPI.gateway.FilmDiscoveryGateway;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FilmDiscoveryServlet", value = {"/get-films", "/get-films-per-genre", "/get-film-details"})
public class FilmDiscoveryServlet extends HttpServlet {
    private FilmDiscoveryGateway gateway;

    @Override
    public void init() {
        gateway = new FilmDiscoveryGateway();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = ServletUtil.getRequestPath(request);
        response.setContentType("application/json");
        switch (path) {
            case "/get-films" -> gateway.getFilms(response);
            case "/get-films-per-genre" -> gateway.getFilmsPerGenre(request, response);
            case "/get-film-details" -> gateway.getFilmDetails(request, response);
        }
        response.flushBuffer();
    }
}
