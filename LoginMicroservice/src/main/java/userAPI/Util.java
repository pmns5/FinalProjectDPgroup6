package userAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Util {

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
}
