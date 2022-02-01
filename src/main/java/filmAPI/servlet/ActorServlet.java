package filmAPI.servlet;

import filmAPI.gateway.ActorGateway;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ActorServlet", value = {"/get-actor","/get-actors", "/add-actor", "/delete-actor", "/edit-actor"})
public class ActorServlet extends HttpServlet {
    private ActorGateway gateway;

    @Override
    public void init() {
        gateway = new ActorGateway();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = ServletUtil.getRequestPath(request);
        switch (path) {
            case "/add-actor" -> gateway.addActor(request, response);
            case "/edit-actor" -> gateway.editActor(request, response);
        }
        response.flushBuffer();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        String path = ServletUtil.getRequestPath(request);
        switch (path) {
            case "/get-actors" -> gateway.viewActors(request, response);
            case "/get-actor" -> gateway.viewOneActor(request, response);
            case "/delete-actor" -> gateway.deleteActor(request, response);
        }
        response.flushBuffer();
    }
}
