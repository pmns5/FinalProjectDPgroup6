package provaAPI.servlet;


import provaAPI.gateway.ActorGateway;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "ActorServlet", value = {"/actors", "/add-actor", "/delete-actor", "/edit-actor"})
public class ActorServlet extends HttpServlet {

    private ActorGateway gateway;

    @Override
    public void init() throws ServletException {
        gateway = new ActorGateway();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = ServletUtil.getRequestPath(request);

        if ("/add-actor".equals(path)) {
            gateway.addActor(request, response);

        } else if ("/edit-actor".equals(path)) {
            gateway.editActor(request, response);
        }
        response.flushBuffer();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = ServletUtil.getRequestPath(request);

        if ("/actors".equals(path)) {
            response.setContentType("application/json");
            gateway.viewActor(request, response);

        } else if ("/delete-actor".equals(path)) {
            gateway.deleteActor(request, response);
        }
        response.flushBuffer();
    }


}

