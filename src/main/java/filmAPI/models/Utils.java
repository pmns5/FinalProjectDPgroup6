package filmAPI.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
    public static String toJSON(Object o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(o);
    }
}
