package provaAPI.servlet;

import provaAPI.gateway.FilmGateway;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FilmServlet", value = {"/view-film", "/add-film", "/delete-film", "/edit-film"})
public class FilmServlet extends HttpServlet {

    private FilmGateway gateway;

    @Override
    public void init() throws ServletException {
        gateway = new FilmGateway();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = ServletUtil.getRequestPath(request);
        // Add role
        if ("/add-film".equals(path)) {
            gateway.addFilm(request, response);
            // Delete role data
        } else if ("/edit-film".equals(path)) {
            gateway.editFilm(request, response);
        }
        response.flushBuffer();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = ServletUtil.getRequestPath(request);
        // Get role data
        if ("/view-film".equals(path)) {
            response.setContentType("application/json");
            // gateway.viewFilm(request, response);
            // Delete a role
        } else if ("/delete-film".equals(path)) {
            gateway.deleteFilm(request, response);
        }
        response.flushBuffer();
    }

}
