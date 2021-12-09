package provaAPI.servlet;


import provaAPI.gateway.HomeGateway;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "HomeServlet", value = {"/getAll", "/getPerGenre"})
public class HomeServlet extends HttpServlet {

    private HomeGateway gateway;

    @Override
    public void init() throws ServletException {
        gateway = new HomeGateway();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = ServletUtil.getRequestPath(request);

        if ("/getAll".equals(path)) {
            response.setContentType("application/json");
            gateway.getAll(request, response);

        } else if ("/getPerGenre".equals(path)) {
            gateway.getPerGenre(request, response);
        }
        response.flushBuffer();
    }


}


