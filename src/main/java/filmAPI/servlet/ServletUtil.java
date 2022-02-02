package filmAPI.servlet;

import javax.servlet.http.HttpServletRequest;

public class ServletUtil {

    /**
     * Method to get an HTTP request from other server
     *
     * @param request: received request
     * @return String content of request
     */
    public static String getRequestPath(HttpServletRequest request) {
        return request.getRequestURI().substring(request.getContextPath().length());
    }
}