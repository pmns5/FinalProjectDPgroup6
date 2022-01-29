package filmAPI.gateway;

import filmAPI.models.Actor;
import filmAPI.models.User;
import filmAPI.models.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserGateway extends APIGateway {
    public UserGateway() {
        super();
    }

    public void addUser(HttpServletRequest req, HttpServletResponse res) {
        String idStr = req.getParameter("id_user");
        String username = req.getParameter("username");
        if (idStr != null && username != null) {
            int id = Integer.parseInt(idStr);
            User user = new User(id, username);
            if (userInterface.addUser(user)) {
                // add success
                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                // Request generated an error, send error
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        } else {
            // Error on parameters
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void editUser(HttpServletRequest req, HttpServletResponse res) {
        String idStr = req.getParameter("id_user");
        String username = req.getParameter("username");
        if (idStr != null && username != null) {
            User user = new User(Integer.parseInt(idStr), username);
            if (userInterface.editUser(user)) {
                // add success
                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                // Request generated an error, send error
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        } else {
            // Error on parameters
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    public void deleteUser(HttpServletRequest req, HttpServletResponse res) {
        String idStr = req.getParameter("id_user");
        if (idStr != null) {
            // Do delete
            if (userInterface.deleteUser(Integer.parseInt(idStr))) {
                // Send success
                res.setStatus(HttpServletResponse.SC_OK);
            } else {
                // Request generated an error, send error
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            }
        } else {
            // No id passed as parameter
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
