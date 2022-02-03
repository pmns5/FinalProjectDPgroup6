package APIGateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


import static APIGateway.Util.*;

@WebServlet(name = "APIGateway", value = {"/get-actors", "/get-actor"
        , "/add-actor", "/delete-actor", "/edit-actor",
        "/get-feedback", "/add-feedback", "/delete-feedback", "/edit-feedback",
        "/get-by-user", "/get-by-film",
        "/getAll", "/getPerGenre", "/getFilmDetails",
        "/get-film", "/add-film", "/delete-film", "/edit-film",
        "/add-user", "/edit-user", "/login-user", "/ban-user", "/remove-ban-user",
        "/delete-user", "/get-user", "/get-users", "/get-banned-users", "/get-no-banned-users"
})
@MultipartConfig(maxFileSize = 16177215)
public class NewAPIGateway extends HttpServlet {
    private final String actorMicroservice = "http://87.1.89.130:8081/api/actors";
    private final String filmDiscovery = "http://87.1.89.130:8081/api/query";
    private final String filmMicroservice = "http://87.1.89.130:8081/api/films";
    private final String feedbackMicroservice = "http://87.1.89.130:8081/api/feedbacks";

    private final String loginMicroservice = "http://87.1.89.130:8082/apiLogin/login";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = Util.getRequestPath(request);
        PrintWriter pw = response.getWriter();
        try {
            switch (path) {
                // Actor
                case "/add-actor" -> response.setStatus(post(actorMicroservice + path, new Actor(request, true)));
                case "/edit-actor" -> response.setStatus(post(actorMicroservice + path, new Actor(request, false)));

                // Feedback
                case "/add-feedback" -> response.setStatus(post(feedbackMicroservice + path, new Feedback(request)));
                case "/edit-feedback" -> response.setStatus(post(feedbackMicroservice + path, new Feedback(request)));

                // Film
                case "/add-film" -> response.setStatus(post(filmMicroservice + path, new FilmManagement(request, true)));
                case "/edit-film" -> response.setStatus(post(filmMicroservice + path, new FilmManagement(request, false)));

                // Login
                case "/add-user" -> response.setStatus(post(loginMicroservice + path, new User(request, true)));
                case "/edit-user" -> response.setStatus(post(loginMicroservice + path, new User(request, false)));

                case "/login-user" -> pw.write(toJSON(post(loginMicroservice + path, new UserLogin(request), UserCookie.class)));
                //case "/ban-user", "/remove-ban-user" -> post(response, loginMicroservice + path, request, new String[]{"id_user"});

            }
        }catch (Exception e){
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

            // Feedback
            case "/delete-feedback" -> resp.setStatus(delete(feedbackMicroservice + path + "/" +
                    req.getParameter("id_film") + "/" + req.getParameter("id_user")));
            case "/get-feedback" -> pw.write(toJSON(get(feedbackMicroservice + path + "/" +
                    req.getParameter("id_film") + "/" + req.getParameter("id_user"), Feedback.class)));
            case "/get-by-film" -> pw.write(toJSON(get(feedbackMicroservice + path + "/" + req.getParameter("id_film"),null)));
            case "/get-by-user" -> pw.write(toJSON(get(feedbackMicroservice + path + "/" + req.getParameter("id_user"),null)));

            // Film
            case "/delete-film"-> resp.setStatus(delete(filmMicroservice + path + "/" + req.getParameter("id")));
            case "/get-film" -> pw.write(toJSON(get(filmMicroservice + path + "/" + req.getParameter("id"), FilmManagement.class)));
            case "/get-films" -> pw.write(toJSON(get(filmMicroservice + path, null)));


            // Login
            case "/delete-user" -> resp.setStatus(delete(loginMicroservice + path + "/" + req.getParameter("id_user")));
            case "/get-user" -> pw.write(toJSON(get(loginMicroservice + path + "/" + req.getParameter("id_user"), User.class)));
            //case "/get-users", "/get-banned-users", "/get-no-banned-users" -> get(resp, loginMicroservice + path);


            // Film Query
            case "/getAll" -> pw.write(toJSON(get(filmDiscovery + path, null)));
            case "/getFilmDetails" -> pw.write(toJSON(get(filmDiscovery + path+ "/"+req.getParameter("id"), ReviewPageFilm.class)));
            //case "/getPerGenre" -> get(resp, filmDiscovery + path, req, new String[]{"genre"});
        }
        pw.flush();
    }




}

