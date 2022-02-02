package filmAPI.servlet;

import filmAPI.gateway.FeedbackGateway;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FeedbackServlet", value = {"/add-feedback", "/edit-feedback", "/delete-feedback", "/get-feedback"})
public class FeedbackServlet extends HttpServlet {
    private FeedbackGateway gateway;

    @Override
    public void init() {
        gateway = new FeedbackGateway();
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = ServletUtil.getRequestPath(request);
        switch (path) {
            case "/delete-feedback" -> gateway.deleteFeedback(request, response);
            case "/get-feedback" -> gateway.getFeedback(request, response);
        }
        response.flushBuffer();
    }


}
