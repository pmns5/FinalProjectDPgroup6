package provaAPI.servlet;

import provaAPI.gateway.FilmDetailsGateway;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FilmDetailsServlet", value = {"/getAllFeedbacks"})
public class FilmDetailsServlet extends HttpServlet {

    private FilmDetailsGateway gateway;

    @Override
    public void init() {
        gateway = new FilmDetailsGateway();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = ServletUtil.getRequestPath(request);
        response.setContentType("application/json");
        gateway.getAllFeedbackFromFilm(request, response);
        response.flushBuffer();
    }


}
