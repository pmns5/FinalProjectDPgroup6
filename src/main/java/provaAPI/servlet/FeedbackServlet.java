package provaAPI.servlet;

import provaAPI.gateway.FeedbackGateway;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "FeedbackServlet", value = {"/feedback", "/add-feedback", "/delete-feedback", "/edit-feedback"})
public class FeedbackServlet extends HttpServlet {

    private FeedbackGateway gateway;

    @Override
    public void init() {
        gateway = new FeedbackGateway();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = ServletUtil.getRequestPath(request);
        switch (path) {
            case "/feedback" -> gateway.viewFeedback(request, response);
            case "/delete-feedback" -> gateway.deleteFeedback(request, response);
        }
        response.flushBuffer();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = ServletUtil.getRequestPath(request);
        switch (path) {
            case "/add-feedback" -> gateway.addFeedback(request, response);
            case "/edit-feedback" -> gateway.editFeedback(request, response);
        }
        response.flushBuffer();
    }

}
