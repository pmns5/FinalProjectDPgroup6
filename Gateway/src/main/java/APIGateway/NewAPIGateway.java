package APIGateway;

import models.*;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static APIGateway.Util.*;

@WebServlet(name = "APIGateway", value = {"/add-actor", "/edit-actor", "/delete-actor", "/get-actor", "/get-actors",
                                          "/add-feedback", "/edit-feedback", "/delete-feedback", "/get-feedback", "/get-feedback-by-user", "/get-feedback-by-film",
                                          "/add-film", "/edit-film", "/delete-film", "/get-film",
                                          "/add-user", "/edit-user", "/login-user", "/delete-user", "/get-user", "/get-users",
                                          "/ban-user", "/remove-ban-user", "/get-banned-users", "/get-no-banned-users",
                                          "/get-films-home-page", "/get-films-by-genre", "/get-film-review-page"
})
@MultipartConfig(maxFileSize = 16177215)
public class NewAPIGateway extends HttpServlet {
    private final String actorMicroservice = "http://79.46.58.44:8082/api/actors";
    private final String filmMicroservice = "http://79.46.58.44:8082/api/films";
    private final String feedbackMicroservice = "http://79.46.58.44:8082/api/feedbacks";
    private final String filmDiscovery = "http://79.46.58.44:8082/api/query";

    private final String loginMicroservice = "http://79.46.58.44:8081/apiLogin/login";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = Util.getRequestPath(request);
        PrintWriter pw = response.getWriter();
        try {
            switch (path) {
                // Actor
                case "/add-actor" -> response.setStatus(post(actorMicroservice + path, new Actor(request, true)));
                case "/edit-actor" -> response.setStatus(post(actorMicroservice + path, new Actor(request, false)));
                // Film
                case "/add-film" -> response.setStatus(post(filmMicroservice + path, new FilmManagement(request, true)));
                case "/edit-film" -> response.setStatus(post(filmMicroservice + path, new FilmManagement(request, false)));
                // Feedback
                case "/add-feedback" -> response.setStatus(post(feedbackMicroservice + path, new Feedback(request)));
                case "/edit-feedback" -> response.setStatus(post(feedbackMicroservice + path, new Feedback(request)));
                // Login
                case "/add-user" -> response.setStatus(post(loginMicroservice + path, new User(request, true)));
                case "/edit-user" -> response.setStatus(post(loginMicroservice + path, new User(request, false)));
                case "/login-user" -> pw.write(toJSON(post(loginMicroservice + path, new UserLogin(request), UserCookie.class)));
                //case "/ban-user", "/remove-ban-user"
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        response.flushBuffer();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        String path = Util.getRequestPath(req);
        PrintWriter pw = resp.getWriter();
        switch (path) {
            // Actors
            case "/get-actor" -> pw.write(toJSON(get(actorMicroservice + path + "/" + req.getParameter("id"), Actor.class)));
            case "/get-actors" -> pw.write(toJSON(get(actorMicroservice + path, null)));
            case "/delete-actor" -> resp.setStatus(delete(actorMicroservice + path + "/" + req.getParameter("id")));
            // Film
            case "/delete-film" -> resp.setStatus(delete(filmMicroservice + path + "/" + req.getParameter("id")));
            case "/get-film" -> pw.write(toJSON(get(filmMicroservice + path + "/" + req.getParameter("id"), FilmManagement.class)));
            case "/get-films" -> pw.write(toJSON(get(filmMicroservice + path, null)));
            // Feedback
            case "/delete-feedback" -> resp.setStatus(delete(feedbackMicroservice + path + "/" + req.getParameter("id_film") + "/" + req.getParameter("id_user")));
            case "/get-feedback" -> pw.write(toJSON(get(feedbackMicroservice + path + "/" + req.getParameter("id_film") + "/" + req.getParameter("id_user"), Feedback.class)));
            case "/get-feedback-by-film" -> pw.write(toJSON(get(feedbackMicroservice + path + "/" + req.getParameter("id_film"), null)));
            case "/get-feedback-by-user" -> pw.write(toJSON(get(feedbackMicroservice + path + "/" + req.getParameter("id_user"), null)));
            // Film Query
            case "/get-films-home-page" -> pw.write(toJSON(get(filmDiscovery + path, null)));
            case "/get-film-review-page" -> pw.write(toJSON(get(filmDiscovery + path + "/" + req.getParameter("id"), ReviewPageFilm.class)));
            //case "/get-films-by-genre"
            // Login
            case "/delete-user" -> resp.setStatus(delete(loginMicroservice + path + "/" + req.getParameter("id_user")));
            case "/get-user" -> pw.write(toJSON(get(loginMicroservice + path + "/" + req.getParameter("id_user"), User.class)));
            //case "/get-users", "/get-banned-users", "/get-no-banned-users"

        }
        pw.flush();
    }
}
