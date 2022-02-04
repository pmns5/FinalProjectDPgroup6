package APIGateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Util {

    /**
     * Method to get an HTTP request from other server
     *
     * @param request: received request
     * @return String content of request
     */
    public static String getRequestPath(HttpServletRequest request) {
        return request.getRequestURI().substring(request.getContextPath().length());
    }

    /**
     * Method to validate a string in order to avoid inadmissible symbols
     *
     * @param string: object to be converted
     * @return if the validation succeed return the string, otherwise throws the Exception
     * @throws Exception: any kind of Exception
     */
    public static String validate(String string) throws Exception {
        if (string.matches(".*<+.*>+.*")) throw new Exception();
        return string;
    }

    /**
     * Method to convert an object to a JSON String
     *
     * @param object: object to be converted
     * @return JSON String of the object
     * @throws JsonProcessingException: formatted JSON exception
     */
    public static String toJSON(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    public static int put(String url, Object obj) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.put(Entity.entity(obj, MediaType.APPLICATION_JSON));
        return response.getStatus();
    }

    public static Object put(String url, Object obj, Class<?> objClass) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.put(Entity.entity(obj, MediaType.APPLICATION_JSON));
        Object x = response.readEntity(objClass);
        return x;
    }

    public static Object get(String url, Class<?> objClass) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        if (objClass == null) return response.readEntity(new GenericType<>() {
        });
        else {
            Object x = response.readEntity(objClass);
            return x;
        }
    }

    public static int delete(String url) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.delete();
        return response.getStatus();
    }

    public static String extractTrailerString(String trailer) {
        String[] splitted = trailer.split("/");
        return splitted[splitted.length - 1];
    }
}
