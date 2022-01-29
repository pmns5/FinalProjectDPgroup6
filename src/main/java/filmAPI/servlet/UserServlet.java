package filmAPI.servlet;

import filmAPI.gateway.ActorGateway;
import filmAPI.gateway.UserGateway;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserServlet", value = {"/add-user", "/delete-user", "/edit-user"})
public class UserServlet extends HttpServlet {
    private UserGateway gateway;

    @Override
    public void init() {
        gateway = new UserGateway();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = ServletUtil.getRequestPath(request);
        switch (path) {
            case "/add-user" -> gateway.addUser(request, response);
            case "/edit-user" -> gateway.editUser(request, response);
        }
        response.flushBuffer();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        String path = ServletUtil.getRequestPath(request);
        if ("/delete-user".equals(path)) {
            gateway.deleteUser(request, response);
        }
        response.flushBuffer();
    }
}
