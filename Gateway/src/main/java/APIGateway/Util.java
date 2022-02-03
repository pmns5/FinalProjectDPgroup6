package APIGateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Actor;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.PrintWriter;

public class Util {
    /**
     * Get internal path (inner to the context path) of a request.
     *
     * @param request: the request you would get the path.
     * @return the inner path.
     */
    public static String getRequestPath(HttpServletRequest request) {
        return request.getRequestURI().substring(request.getContextPath().length());
    }

    public static String validate(String str) throws Exception {
        if (str.matches(".*<+.*>+.*")) throw new Exception();
        return str;
    }

    public static String toJSON(Object o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(o);
    }

    public static int post(String url, Object obj) {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);

        Invocation.Builder invocationBuilder
                = target.request(MediaType.APPLICATION_JSON);

        Response response
                = invocationBuilder
                .post(Entity.entity(obj, MediaType.APPLICATION_JSON));

        return response.getStatus();
    }
    public static Object post(String url, Object obj, Class<?> objClass ) {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);

        Invocation.Builder invocationBuilder
                = target.request(MediaType.APPLICATION_JSON);

        Response response
                = invocationBuilder
                .post(Entity.entity(obj, MediaType.APPLICATION_JSON));

        Object x = response.readEntity(objClass);
        return x;
    }

    public static Object get(String url, Class<?> objClass) throws JsonProcessingException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);

        Invocation.Builder invocationBuilder
                = target.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.get();

        if (objClass == null)
            return response.readEntity(new GenericType<>() {
            });
        else {
            Object x = response.readEntity(objClass);
            return x;
        }
    }


    public static int delete(String url) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);

        Invocation.Builder invocationBuilder
                = target.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.get();

        return response.getStatus();
    }

}



