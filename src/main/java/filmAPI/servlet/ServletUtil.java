package filmAPI.servlet;

import javax.servlet.http.HttpServletRequest;


public class ServletUtil {


    /**
     * Transform an array of strings into an array of longs. If the array is null, it creates an empty long array.
     *
     * @param ids: array of strings
     * @return an array of long.
     */
    public static long[] getIdsArray(String[] ids) {
        if (ids == null) {
            return new long[0];
        }
        long[] longIds = new long[ids.length];
        for (int i = 0; i < ids.length; i++) {
            longIds[i] = Long.parseLong(ids[i]);
        }
        return longIds;
    }

    /**
     * Get internal path (inner to the context path) of a request.
     *
     * @param request: the request you would get the path.
     * @return the inner path.
     */
    public static String getRequestPath(HttpServletRequest request) {
        return request.getRequestURI().substring(request.getContextPath().length());
    }

}