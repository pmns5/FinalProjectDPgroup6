package provaAPI.servlet;

import provaAPI.gateway.FilmManagementGateway;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FilmManagementServlet", value = {"/view-film", "/add-film", "/delete-film", "/edit-film"})
public class FilmManagementServlet extends HttpServlet {

    private FilmManagementGateway gateway;

    @Override
    public void init() throws ServletException {
        gateway = new FilmManagementGateway();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = ServletUtil.getRequestPath(request);

        switch (path) {
            case "/add-film" -> gateway.addFilm(request, response);
            case "/edit-film" -> gateway.editFilm(request, response);
        }
        response.flushBuffer();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = ServletUtil.getRequestPath(request);

        switch (path) {
            case "/delete-film" -> gateway.deleteFilm(request, response);
            case "/view-film" -> gateway.getFilm(request, response);
        }
        response.flushBuffer();
    }

}
